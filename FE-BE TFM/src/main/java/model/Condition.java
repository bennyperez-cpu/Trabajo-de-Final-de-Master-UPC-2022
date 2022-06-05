package model;

import com.fasterxml.jackson.core.JsonProcessingException;

public class Condition {
    private int tipo;//1, 2 o 3
    private String conditionDefinition;

    public Condition(int tipo, String conditionDefinition) {
        this.tipo = tipo;
        this.conditionDefinition = conditionDefinition;
    }

    public boolean validate(Patient patient) throws JsonProcessingException {
        if (this.tipo == 1) {
            int age = patient.calculateAge();
            String[] rango = conditionDefinition.split(" ");
            int minAge = Integer.parseInt(rango[0]);
            int maxAge = Integer.parseInt(rango[1]);
            return age >= minAge && age <= maxAge;
        }
        if (this.tipo == 2) {
            return patient.hasKeyWord(conditionDefinition);
        }
        String[] rango = conditionDefinition.split(" ");
        int minPressure = Integer.parseInt(rango[0]);
        int maxPressure = Integer.parseInt(rango[1]);
        int pressure = patient.getPressure();
        return pressure != -1 && pressure >= minPressure && pressure <= maxPressure;
    }
}
