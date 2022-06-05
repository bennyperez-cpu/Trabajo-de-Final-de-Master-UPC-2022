package model;

public class Patient {
        private Name[] name;

        private String resourceType = "Patient";

        private Address[] address;
        private String id;
        private String gender;
        private String birthDate;
        private generalPractitioner[] generalGeneralPractitioner;
        private managingOrganization managingManagingOrganization;

    public Patient() {
    }

    public Patient(Name[] name, String resourceType, Address[] address, String id, String gender, String birthDate, generalPractitioner[] generalGeneralPractitioner, managingOrganization managingManagingOrganization) {
        this.name = name;
        this.resourceType = resourceType;
        this.address = address;
        this.id = id;
        this.gender = gender;
        this.birthDate = birthDate;
        this.generalGeneralPractitioner = generalGeneralPractitioner;
        this.managingManagingOrganization = managingManagingOrganization;
    }

    public Name[] getName() {
        return name;
    }

    public void setName(Name[] name) {
        this.name = name;
    }

    public String getResourceType() {
        return resourceType;
    }

    public void setResourceType(String resourceType) {
        this.resourceType = resourceType;
    }

    public Address[] getAddress() {
        return address;
    }

    public void setAddress(Address[] address) {
        this.address = address;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(String birthDate) {
        this.birthDate = birthDate;
    }

    public generalPractitioner[] getGeneralPractitioner() {
        return generalGeneralPractitioner;
    }

    public void setGeneralPractitioner(generalPractitioner[] generalGeneralPractitioner) {
        this.generalGeneralPractitioner = generalGeneralPractitioner;
    }

    public managingOrganization getManagingOrganization() {
        return managingManagingOrganization;
    }

    public void setManagingOrganization(managingOrganization managingManagingOrganization) {
        this.managingManagingOrganization = managingManagingOrganization;
    }
}
