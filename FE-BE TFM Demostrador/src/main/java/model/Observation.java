package model;

public class Observation {

    private String id;
    private String display;
    private String idPatient;


    public Observation() {
    }

    public Observation(String id, String display, String idPatient) {
        this.id = id;
        this.display = display;
        this.idPatient = idPatient;
    }

    public void setIdPatient(String idPatient) {
        this.idPatient = idPatient;
    }

    public String getIdPatient() {
        return idPatient;
    }


    public String getId() {
        return id;
    }


    public void setId(String id) {
        this.id = id;
    }


    public String getDisplay() {
        return display;
    }


    public void setDisplay(String display) {
        this.display = display;
    }

    @Override
    public String toString() {
        return "Observation{" + "id=" + id + ", display=" + display + ", idPatient=" + idPatient + '}';
    }
    
}