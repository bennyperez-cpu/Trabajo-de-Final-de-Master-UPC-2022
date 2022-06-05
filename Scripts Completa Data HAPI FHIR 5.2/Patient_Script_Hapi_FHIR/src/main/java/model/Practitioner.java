package model;

public class Practitioner {
    private String resourceType = "Practitioner";
    private Name[] name;

    public Practitioner() {
    }

    public Practitioner(String resourceType, Name[] name) {
        this.resourceType = resourceType;
        this.name = name;
    }

    public String getResourceType() {
        return resourceType;
    }

    public void setResourceType(String resourceType) {
        this.resourceType = resourceType;
    }

    public Name[] getName() {
        return name;
    }

    public void setName(Name[] name) {
        this.name = name;
    }
}
