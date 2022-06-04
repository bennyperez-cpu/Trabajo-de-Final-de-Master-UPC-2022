import com.fasterxml.jackson.databind.ObjectMapper;
import model.Code;
import model.Coding;
import model.Observation;
import model.Subject;
import okhttp3.*;
import java.util.Random;


public class Main {

    static String[] observations = {"Glucosa [Moles/Volumen] in Blood + Hearth Disease + Normal Pressure(Pressure 80)",
            "Glucosa [Moles/Volumen] in Blood + Hearth Disease + High Pressure(Pressure 125)",
            "Glucosa [Moles/Volumen] in Blood + Hearth Disease + Low Pressure(Pressure 75)",
            "Glucosa [Moles/Volumen] in Blood + Hearth Disease + Normal Pressure(Pressure 80)",
            "Glucosa [Moles/Volumen] in Blood + Hearth Disease + Normal Pressure(Pressure 80) + High Weight",
            "Hearth Disease + Normal Pressure(Pressure 80) + High Weight + Diabetes",
            "High Pressure(Pressure 130) + Normal Weight",
            "Low Pressure(Pressure 75) + High Weight + Hearth Disease",
            "Low Pressure(Pressure 75) + High Weight + Hearth Disease + Diabetes",
            "High Pressure(Pressure 135) + High Weight + Hearth Disease + Diabetes + Stress" };
    static String[] observationCode = {"15074-1", "15074-2", "15074-3", "15074-4", "15074-5", "15074-6", "15074-7", "15074-8", "15074-9", "15074-10" };

    public static void main(String[] args) throws Exception{
        for (int i=10; i<=500+10; ++i ) {
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
}
