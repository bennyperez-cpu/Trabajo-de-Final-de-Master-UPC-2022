import com.fasterxml.jackson.databind.ObjectMapper;
import model.*;
import model.Address;
import okhttp3.*;

import java.util.Random;

public class Main {

    static String[] maleNames = {"Roberto", "Luis", "Daniel", "Julio", "Joan", "Cesar", "Pedro", "Alexis", "Fernando", "Roger" };
    static String[] femaleNames = {"Maria", "Laura", "Gabriela", "Julia", "Veronika", "Monica", "Daniela", "Astrid", "Maritza", "Paola" };
    static String[] lastNames = {"Cardenitash", "Berrocal", "Cardinale", "Pérez", "Zavala", "Ayala", "Bertrán", "Guardiola", "Morais", "Beramendi" };

    public static void main(String[] args) throws Exception{
        for (int i=1; i<=500; ++i ) {
            System.out.println(i);
            createPatient();
        }
    }

    public static void createPatient() throws Exception{
        OkHttpClient client = new OkHttpClient().newBuilder()
                .build();
        MediaType mediaType = MediaType.parse("application/json");
        ObjectMapper objectMapper = new ObjectMapper();
        Patient patient = new Patient();
        Random random = new Random();
        int gender = random.nextInt(1);


        Name name = new Name();

        String[] names = {gender==1? maleNames[random.nextInt(10)] : femaleNames[random.nextInt(10)] };
        name.setGiven(names);
        name.setFamily(lastNames[random.nextInt(10)]);

        Address address = new Address();
        address.setCity("Barcelona");
        address.setState("Cataluña");
        int zipCode = random.nextInt(3);
        address.setPostalCode(zipCode==0? "08904": (zipCode==1? "08905" : "08906"));

        Practitioner practitioner = new Practitioner();
        Identifier identifier1 = new Identifier();


        int id = random.nextInt(10 - 4) + 4;
        identifier1.setId("" + id);
        practitioner.setIdentifier(identifier1);
        Practitioner[] practitioners = {practitioner};
        Organization organization = new Organization();
        id = random.nextInt(4 - 1) + 1;
        Identifier identifier2 = new Identifier();
        identifier2.setId("" + id);
        organization.setIdentifier(identifier2);
        patient.setName(name);

        patient.setGender(gender==1? "male":"female");
        int day = random.nextInt(28 - 1) + 1;
        int month = random.nextInt(12 - 1) + 1;
        int year = random.nextInt(2021 - 1940) + 1940;
        patient.setBirthDate(String.format("%d-%02d-%02d",year,month,day));
        patient.setAddress(address);
        patient.setGeneralPractitioner(practitioners);
        patient.setManagingOrganization(organization);


        String jsonBody = objectMapper.writeValueAsString(patient);

        System.out.println(jsonBody);
        RequestBody body = RequestBody.create(mediaType, jsonBody);


        Request request = new Request.Builder()
                .url("http://localhost:8080/fhir/Patient/")
                .method("POST", body)
                .addHeader("Authorization", "Bearer 39ff939jgg")
                .addHeader("Content-Type", "application/json")
                .build();
        Response response = client.newCall(request).execute();


    }

}
