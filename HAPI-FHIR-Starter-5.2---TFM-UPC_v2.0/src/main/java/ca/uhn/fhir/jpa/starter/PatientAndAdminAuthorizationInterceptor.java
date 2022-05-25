package ca.uhn.fhir.jpa.starter;

import ca.uhn.fhir.rest.api.server.RequestDetails;
import ca.uhn.fhir.rest.server.interceptor.auth.AuthorizationInterceptor;
import ca.uhn.fhir.rest.server.interceptor.auth.IAuthRule;
import ca.uhn.fhir.rest.server.interceptor.auth.RuleBuilder;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import java.util.List;

@SuppressWarnings("ConstantConditions")
public class PatientAndAdminAuthorizationInterceptor extends AuthorizationInterceptor {

    //for resources of type patient: Self, PatientPractitioner, PatientOrganization, Other
    private String requestorTypeForPatient(String id, String authHeader) throws Exception{
        Client client = ClientBuilder.newClient();
        Response response = client.target("http://localhost:8080/fhir")
                .path(id).request(MediaType.APPLICATION_JSON).header("Authorization", "Bearer 39ff939jgg").get();
        ObjectMapper mapper = new ObjectMapper();
        JsonNode node = mapper.readTree(response.readEntity(String.class));
        String patientPractitioner = node.get("generalPractitioner").get(0).get("identifier").get("id").asText();
        if (("Bearer " + patientPractitioner).equals(authHeader)) {
            return "PatientPractitioner";
        }
        String managingOrganization = node.get("managingOrganization").get("identifier").get("id").asText();
        if (("Bearer " + managingOrganization).equals(authHeader)) {
            return "PatientOrganization";
        }
        return "Other";
    }

    private String requestorResourceType(String requestorId) {
        javax.ws.rs.client.Client client = ClientBuilder.newClient();
        String[] resourceType = {"Patient", "Practitioner", "Organization"};
        for (String s: resourceType) {
            Response response = client.target("http://localhost:8080/fhir")
                    .path(s).path(requestorId).request(MediaType.APPLICATION_JSON).header("Authorization", "Bearer 39ff939jgg").get();
            if (response.getStatus() == 200) {
                return s;
            }
        }
        return "Other";
    }
    //for resources of type Observation: Self, ObservationPatient, ObservationPractitioner, Other
    private String requestorTypeForObservation(String id, String authHeader) throws  Exception{
        Client client = ClientBuilder.newClient();
        String requestorId = authHeader.split(" ")[1];
        String requestorResourceType = requestorResourceType(requestorId);
        Response response = client.target("http://localhost:8080/fhir")
                .path(id).request(MediaType.APPLICATION_JSON)
                .header("Authorization", "Bearer 39ff939jgg").get();
        ObjectMapper mapper = new ObjectMapper();
        JsonNode node = mapper.readTree(response.readEntity(String.class));
        String patient = node.get("subject").get("reference").asText();
        if (requestorResourceType.equals("Patient")) {
            return patient.equals("Patient/" + requestorId) ? "ObservationPatient" : "Other";
        }
        response = client.target("http://localhost:8080/fhir")
                .path(patient).request(MediaType.APPLICATION_JSON)
                .header("Authorization", "Bearer 39ff939jgg").get();
        node = mapper.readTree(response.readEntity(String.class));
        String patientPractitioner = node.get("generalPractitioner").get(0).get("identifier").get("id").asText();
        if (patientPractitioner.equals(requestorId)) {
            return "ObservationPractitioner";
        }
        return "Other";
    }
    private String getRequestorType (RequestDetails theRequestDetails) throws Exception {
        String id = theRequestDetails.getId().toString();
        String resourceType = id.substring(0, id.indexOf('/'));// tipo de recurso por el que se consulta
        String resourceId = id.substring(id.indexOf('/') + 1);// Id del recurso por el que se consulta
        String authHeader = theRequestDetails.getHeader("Authorization");
        if (("Bearer " + resourceId).equals(authHeader)) {
            return "Self";
        }
        if (resourceType.equals("Patient")) {
            return requestorTypeForPatient(id, authHeader);
        }
        return requestorTypeForObservation(id, authHeader);
    }

    @Override
    public List<IAuthRule> buildRuleList(RequestDetails theRequestDetails) {
        // Process authorization header - The following is a fake
        // implementation. Obviously we'd want something more real
        // for a production scenario.
        //
        // In this basic example we have two hardcoded bearer tokens,
        // one which is for a user that has access to one patient, and
        // another that has full access.
        String authHeader = theRequestDetails.getHeader("Authorization");
        boolean userIsAdmin = false;

        String requestorType = null;

        if ("Bearer 39ff939jgg".equals(authHeader)) {
            // This user has access to everything
            userIsAdmin = true;
        }
        else {
            try {
                requestorType = getRequestorType(theRequestDetails);
            } catch (Exception e) {

            }
        }

        if(userIsAdmin || requestorType.equals("Other") == false){
            return new RuleBuilder()
                    .allowAll()
                    .build();
        }
        System.out.println("RequestorType Prueba");

        // By default, deny everything. This should never get hit, but it's
        // good to be defensive
        return new RuleBuilder()
                .denyAll()
                .build();
    }
}