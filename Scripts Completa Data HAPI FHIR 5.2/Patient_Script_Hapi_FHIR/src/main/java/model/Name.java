package model;

public class Name {
    private String use = "official";
    private String[] given;
    private String family;

    public String[] getGiven() {
        return given;
    }

    public String getFamily() {
        return family;
    }

    public void setGiven(String[] given) {
        this.given = given;
    }

    public void setFamily(String family) {
        this.family = family;
    }

    public String getUse() {
        return use;
    }

    public void setUse(String use) {
        this.use = use;
    }
}
