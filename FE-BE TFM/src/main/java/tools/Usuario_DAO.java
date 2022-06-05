/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

 
package tools;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import model.Condition;
import model.Patient;
import model.User;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.ArrayList;
import java.util.List;
import model.Observation;

/**
 *
 * @author alumne
 */
public class Usuario_DAO implements Validar{
    Connection con;
    Conexion_DB cn = new Conexion_DB();
    PreparedStatement ps;
    ResultSet rs;
    int r,ID;
    static String url = "http://10.0.0.224:8080/fhir";
   
    //Login de Usuario
    @Override
    public User validar(User usu) {
        r = 0;
        String sql="Select * from TFM.USUARIOS where NOMBRE_DE_USUARIO=? and CONTRASENHA=?";

        try{
            con=cn.getConnection();
            ps=con.prepareStatement(sql);
            ps.setString(1,usu.getNombre_de_Usuario());
            ps.setString(2,usu.getContrasenha());
            rs=ps.executeQuery();
            
            while(rs.next()){
                r=r+1;
                usu.setNombre_de_Usuario(rs.getString("NOMBRE_DE_USUARIO"));
                usu.setContrasenha(rs.getString("CONTRASENHA"));
                usu.setId_FHIR(rs.getString("IDFHIR"));
            }    
            if(r==1){
                return usu;
            }else{
                return null;
            }
        
        }catch (Exception e){
            System.out.println(e.getStackTrace());
            return null;
        }
        
    }

    public static  List<Patient> getPatientFHIR(String idPatient, String idRequestor) throws JsonMappingException, JsonProcessingException{ 
        javax.ws.rs.client.Client client_1 = ClientBuilder.newClient();
        
       
        Response response_1 = client_1.target(url)
                .path("Patient/" + idPatient).request(MediaType.APPLICATION_JSON).header("Authorization", "Bearer " + idRequestor).get();
                
        Patient paciente = new Patient();

        if (response_1.getStatus() == 200){
            ObjectMapper mapper_1 = new ObjectMapper();
            JsonNode node_1 = mapper_1.readTree(response_1.readEntity(String.class));

            paciente.setNames(node_1.get("name").get(0).get("given").get(0).asText() + " " + node_1.get("name").get(0).get("family").asText());
            paciente.setId(idPatient);
            paciente.setGender(node_1.get("gender").asText());
            paciente.setBirthday(node_1.get("birthDate").asText());
            paciente.setPractitioner(node_1.get("generalPractitioner").get(0).get("identifier").get("id").asText());
            paciente.setOrganization(node_1.get("managingOrganization").get("identifier").get("id").asText()); 


        } else{
            paciente.setNames("No Permitido");
            paciente.setId(idPatient);
            paciente.setGender("No Permitido");
            paciente.setBirthday("No Permitido");
            paciente.setPractitioner("No Permitido");
            paciente.setOrganization("No Permitido");
            
        }

        javax.ws.rs.client.Client client_2 = ClientBuilder.newClient();
        Response response_2 = client_2.target(url + "/Observation?subject=Patient/" + idPatient)
                .request(MediaType.APPLICATION_JSON)
                .header("Authorization", "Bearer 39ff939jgg").get();

        ObjectMapper mapper_2 = new ObjectMapper();
        JsonNode node_2 = mapper_2.readTree(response_2.readEntity(String.class));
        //paciente.setObservation(node_2.get("code").get("coding").get(0).get("Display").asText());
        
        System.out.println(node_2.get("entry").asText());
        
        String idObservation = node_2.get("entry").get(0).get("resource").get("id").asText();

        javax.ws.rs.client.Client client_3 = ClientBuilder.newClient();
        Response response_3 = client_3.target(url)
                .path("Observation/" + idObservation).request(MediaType.APPLICATION_JSON).header("Authorization", "Bearer " + idRequestor).get();

        if (response_3.getStatus() == 200){
            ObjectMapper mapper_3 = new ObjectMapper();
            JsonNode node_3 = mapper_3.readTree(response_3.readEntity(String.class));
            paciente.setObservation(node_3.get("code").get("coding").get(0).get("display").asText());
        }else paciente.setObservation("No Permitido");

        ArrayList<Patient> patients = new ArrayList<Patient>();
        patients.add(paciente);
        return  patients;
    }

    public static String requestorType(String requestorId){
        javax.ws.rs.client.Client client = ClientBuilder.newClient();
        String[] resourceType = {"Patient", "Practitioner", "Organization"};
        for (String s: resourceType) {
            Response response = client.target(url)
                    .path(s).path(requestorId).request(MediaType.APPLICATION_JSON).header("Authorization", "Bearer 39ff939jgg").get();
            if (response.getStatus() == 200) {
                return s;
            }
        }
        return "Other";

    }

public static Patient[] getPatientsData() throws JsonMappingException, JsonProcessingException{
        javax.ws.rs.client.Client client = ClientBuilder.newClient();
        Response response = client.target(url)
                .path("Patient").request(MediaType.APPLICATION_JSON).header("Authorization", "Bearer 39ff939jgg").get();

        ObjectMapper mapper = new ObjectMapper();
        JsonNode node = mapper.readTree(response.readEntity(String.class));
        
        int total = 500;
        //int total = Integer.parseInt(node.get("total").asText());
        Patient[] patients = new Patient[total];
        for (int i = 0; i < total; ++i) {
            
            
            Patient patient = new Patient();
            String id = node.get("entry").get(i).get("resource").get("id").asText();     
            String birthDate = node.get("entry").get(i).get("resource").get("birthDate").asText();
            String practitioner = node.get("entry").get(i).get("resource").get("generalPractitioner").get(0).get("identifier").get("id").asText();
            String organization = node.get("entry").get(i).get("resource").get("managingOrganization").get("identifier").get("id").asText();
            String gender = node.get("entry").get(i).get("resource").get("gender").asText();
            
            System.out.println("El i es: " + i);
            System.out.println("El id es: " + id);
            
            
            patient.setId(id);
            patient.setBirthday(birthDate);
            patient.setPractitioner(practitioner);
            patient.setOrganization(organization);
            patient.setGender(gender);
            patients[i] = patient;
        }
        return patients;
    }

public static Observation[] getObservationsData() throws JsonMappingException, JsonProcessingException{
        javax.ws.rs.client.Client client = ClientBuilder.newClient();
        Response response = client.target(url)
                .path("Observation").request(MediaType.APPLICATION_JSON).header("Authorization", "Bearer 39ff939jgg").get();

        ObjectMapper mapper = new ObjectMapper();
        JsonNode node = mapper.readTree(response.readEntity(String.class));
        
        int total = 500;
        //int total = Integer.parseInt(node.get("total").asText());
        Observation[] observations = new Observation[total];
        for (int i = 0; i < total; ++i) {
            Observation observation = new Observation();
            String id = node.get("entry").get(i).get("resource").get("id").asText();    
            String display = node.get("entry").get(i).get("resource").get("code").get("coding").get(0).get("display").asText();
            String patient = node.get("entry").get(i).get("resource").get("subject").get("reference").asText().substring(8);

            observation.setId(id);
            observation.setDisplay(display);
            observation.setIdPatient(patient);
            observations[i] = observation;
        }
        return observations;
    }
    
    private static int patientHasKeyWord(Observation[] observations, String idPatient, String keyWord ) {
        
        for(Observation observation: observations){
            if(observation.getIdPatient().equals(idPatient) && observation.getDisplay().contains(keyWord)){
                return 1;
            }
        }
        return 0;   
    }

    public static boolean isValid(Patient patient, List<Condition> conditions) throws JsonProcessingException{
        for (Condition condition: conditions) {
            if (!condition.validate(patient)) {
                return false;
            }
        }
        return true;
    }

    public static int getStatistics(List<Condition> conditions, String requestor) throws JsonProcessingException{
        Patient[] patients =  getPatientsData();
        Observation[] observations = getObservationsData();
        int quantity = 0;
        for(Patient patient:patients) {
            if ((requestor.equals("39ff939jgg") || requestor.equals(patient.getPractitioner())
                    || requestor.equals(patient.getOrganization())) && isValid(patient, conditions)) {
                ++quantity;
            }
        }
        return quantity;
    }
}

