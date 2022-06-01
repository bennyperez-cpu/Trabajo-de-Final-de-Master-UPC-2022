package model;

public class Observation {

    private String resourceType = "Observation";
    private Code code;
    private Subject subject;

    public Observation() {
    }

    public Observation(String resourceType, Code code, Subject subject) {
        this.resourceType = resourceType;
        this.code = code;
        this.subject = subject;
    }

    public String getResourceType() {
        return resourceType;
    }

    public void setResourceType(String resourceType) {
        this.resourceType = resourceType;
    }

    public Code getCode() {
        return code;
    }

    public void setCode(Code code) {
        this.code = code;
    }

    public Subject getSubject() {
        return subject;
    }

    public void setSubject(Subject subject) {
        this.subject = subject;
    }
}
