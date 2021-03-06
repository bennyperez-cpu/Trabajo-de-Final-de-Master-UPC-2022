import com.fasterxml.jackson.databind.ObjectMapper;
import model.*;
import model.Address;
import okhttp3.*;

import java.util.Random;

public class Main {

    static String[] maleNames = {"Roberto", "Luis", "Daniel", "Julio", "Joan", "Cesar", "Pedro", "Alexis", "Fernando", "Roger" };
    static String[] femaleNames = {"Maria", "Laura", "Gabriela", "Julia", "Veronika", "Monica", "Daniela", "Astrid", "Maritza", "Paola" };
    static String[] lastNames = {"Cardenitash", "Berrocal", "Cardinale", "Pérez", "Zavala", "Ayala", "Bertrán", "Guardiola", "Morais", "Beramendi" };

    static String[] observations = {"Glucosa [Moles/Volumen] in Blood + Hearth Disease + Normal Pressure(Blood Pressure 80 )",
            "Glucosa [Moles/Volumen] in Blood + Hearth Disease + High Pressure(Blood Pressure 125 )",
            "Glucosa [Moles/Volumen] in Blood + Hearth Disease + Low Pressure(Blood Pressure 75 )",
            "Glucosa [Moles/Volumen] in Blood + Hearth Disease + Normal Pressure(Blood Pressure 80 )",
            "Glucosa [Moles/Volumen] in Blood + Hearth Disease + Normal Pressure(Blood Pressure 80 ) + High Weight",
            "Hearth Disease + Normal Pressure(Blood Pressure 80 ) + High Weight + Diabetes",
            "High Pressure(Blood Pressure 130 ) + Normal Weight",
            "Low Pressure(Blood Pressure 75 ) + High Weight + Hearth Disease",
            "Low Pressure(Blood Pressure 75 ) + High Weight + Hearth Disease + Diabetes",
            "High Pressure(Blood Pressure 135 ) + High Weight + Hearth Disease + Diabetes + Stress" };
    static String[] observationCode = {"15074-1", "15074-2", "15074-3", "15074-4", "15074-5", "15074-6", "15074-7", "15074-8", "15074-9", "15074-10" };

    static String[] organizationName = {"Clínica Corachan", "Clínica Japonesa","Clínica Internacional", "Clínica Barcelona"};

    public static void main(String[] args) throws Exception{

        for(int i=0; i<=3; ++i ) {
            createOrganization(i);
        }

        for(int i=0; i<=5; ++i ) {
            createPractitioner(i);
        }

        for (int i=1; i<=500; ++i ) {
            System.out.println(i);
            createPatient();
        }

        for (int i=11; i<=500 + 11; ++i ) {
            System.out.println(i);
            createObservation(i);
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
        Name[] names_1 = {name};


        Address address = new Address();
        address.setCity("Barcelona");
        address.setState("Cataluña");
        int zipCode = random.nextInt(3);
        address.setPostalCode(zipCode==0? "08904": (zipCode==1? "08905" : "08906"));

        Address[] address_1 = {address};

        generalPractitioner generalPractitioner = new generalPractitioner();
        Identifier identifier1 = new Identifier();


        int id = random.nextInt(10 - 4) + 4;
        identifier1.setId("" + id);
        generalPractitioner.setIdentifier(identifier1);
        generalPractitioner[] generalPractitioners = {generalPractitioner};
        managingOrganization managingOrganization = new managingOrganization();
        id = random.nextInt(4 - 1) + 1;
        Identifier identifier2 = new Identifier();
        identifier2.setId("" + id);
        managingOrganization.setIdentifier(identifier2);
        patient.setName(names_1);

        patient.setGender(gender==1? "male":"female");
        int day = random.nextInt(28 - 1) + 1;
        int month = random.nextInt(12 - 1) + 1;
        int year = random.nextInt(2021 - 1940) + 1940;
        patient.setBirthDate(String.format("%d-%02d-%02d",year,month,day));
        patient.setAddress(address_1);
        patient.setGeneralPractitioner(generalPractitioners);
        patient.setManagingOrganization(managingOrganization);


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

    public static void createObservation(int id) throws Exception{
        OkHttpClient client = new OkHttpClient().newBuilder()
                .build();
        MediaType mediaType = MediaType.parse("application/json");
        ObjectMapper objectMapper = new ObjectMapper();
        Observation observation = new Observation();
        Random random = new Random();

        Coding coding = new Coding();
        int idObs = random.nextInt(10);
        coding.setSystem("http://loinc.org");
        coding.setCode(observationCode[idObs]);
        coding.setDisplay(observations[idObs]);
        Coding[] codings = {coding};

        Code code = new Code();
        code.setCoding(codings);

        Subject subject = new Subject();
        subject.setDisplay(" ");
        subject.setReference("Patient/" + id);

        observation.setCode(code);
        observation.setSubject(subject);

        String jsonBody = objectMapper.writeValueAsString(observation);

        System.out.println(jsonBody);
        RequestBody body = RequestBody.create(mediaType, jsonBody);


        Request request = new Request.Builder()
                .url("http://localhost:8080/fhir/Observation/")
                .method("POST", body)
                .addHeader("Authorization", "Bearer 39ff939jgg")
                .addHeader("Content-Type", "application/json")
                .build();
        Response response = client.newCall(request).execute();

    }

    public static void createOrganization(int id) throws Exception{
        OkHttpClient client = new OkHttpClient().newBuilder()
                .build();
        MediaType mediaType = MediaType.parse("application/json");
        ObjectMapper objectMapper = new ObjectMapper();
        Organization organization = new Organization();
        organization.setName(organizationName[id]);

        String jsonBody = objectMapper.writeValueAsString(organization);

        System.out.println(jsonBody);
        RequestBody body = RequestBody.create(mediaType, jsonBody);


        Request request = new Request.Builder()
                .url("http://localhost:8080/fhir/Organization/")
                .method("POST", body)
                .addHeader("Authorization", "Bearer 39ff939jgg")
                .addHeader("Content-Type", "application/json")
                .build();
        Response response = client.newCall(request).execute();
    }

    public static void createPractitioner(int id) throws Exception{
        OkHttpClient client = new OkHttpClient().newBuilder()
                .build();
        MediaType mediaType = MediaType.parse("application/json");
        ObjectMapper objectMapper = new ObjectMapper();
        Random random = new Random();
        int gender = random.nextInt(1);


        Name name = new Name();

        String[] names = {gender==1? maleNames[random.nextInt(10)] : femaleNames[random.nextInt(10)] };
        name.setGiven(names);
        name.setFamily(lastNames[random.nextInt(10)]);
        Name[] names_1 = {name};
        Practitioner practitioner = new Practitioner();

        practitioner.setName(names_1);

        String jsonBody = objectMapper.writeValueAsString(practitioner);
        System.out.println(jsonBody);
        RequestBody body = RequestBody.create(mediaType, jsonBody);
        Request request = new Request.Builder()
                .url("http://localhost:8080/fhir/Practitioner/")
                .method("POST", body)
                .addHeader("Authorization", "Bearer 39ff939jgg")
                .addHeader("Content-Type", "application/json")
                .build();
        Response response = client.newCall(request).execute();



    }
}
