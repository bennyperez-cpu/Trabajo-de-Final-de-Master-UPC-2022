import com.fasterxml.jackson.databind.ObjectMapper;
import model.Code;
import model.Coding;
import model.Observation;
import model.Subject;
import okhttp3.*;
import java.util.Random;


public class Main {

    static String[] observations = {"Glucosa [Moles/Volumen] in Blood + Hearth Disease + Presure 80", "Glucosa [Moles/Volumen] in Blood + High Pressure", "Glucosa [Moles/Volumen] in Blood + Low Presure", "", "Joan", "Cesar", "Pedro", "Alexis", "Fernando", "Roger" };
    static String[] observationCode = {"15074-8", "Laura", "Gabriela", "Julia", "Veronika", "Monica", "Daniela", "Astrid", "Maritza", "Paola" };
    static String[] lastNames = {"Cardenitash", "Berrocal", "Cardinale", "Pérez", "Zavala", "Ayala", "Bertrán", "Guardiola", "Morais", "Beramendi" };

    public static void main(String[] args) throws Exception{
        for (int i=1; i<=500; ++i ) {
            System.out.println(i);
            createObservation(i);
        }
    }

    public static void createObservation(int id) throws Exception{
        OkHttpClient client = new OkHttpClient().newBuilder()
                .build();
        MediaType mediaType = MediaType.parse("application/json");
        ObjectMapper objectMapper = new ObjectMapper();
        Observation observation = new Observation();
        Random random = new Random();

        Coding coding = new Coding();
        coding.setSystem("http://loinc.org");
        coding.setCode("15074-8");
        coding.setDisplay("Glucosa [Moles/Volumen] in Blood");
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


/*        Request request = new Request.Builder()
                .url("http://localhost:8080/fhir/Observation/")
                .method("POST", body)
                .addHeader("Authorization", "Bearer 39ff939jgg")
                .addHeader("Content-Type", "application/json")
                .build();
        Response response = client.newCall(request).execute();*/


    }




}
