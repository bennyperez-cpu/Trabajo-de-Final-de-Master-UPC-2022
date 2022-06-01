package model;

public class Subject {
    private String reference;
    private String display;

    public Subject() {
    }

    public Subject(String reference, String display) {
        this.reference = reference;
        this.display = display;
    }

    public String getReference() {
        return reference;
    }

    public void setReference(String reference) {
        this.reference = reference;
    }

    public String getDisplay() {
        return display;
    }

    public void setDisplay(String display) {
        this.display = display;
    }
}
