package model;

public class Organization {
    private String resourceType = "Organization";
    private String name;

    public Organization() {
    }

    public Organization(String resourceType, String name) {
        this.resourceType = resourceType;
        this.name = name;
    }

    public String getResourceType() {
        return resourceType;
    }

    public void setResourceType(String resourceType) {
        this.resourceType = resourceType;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
