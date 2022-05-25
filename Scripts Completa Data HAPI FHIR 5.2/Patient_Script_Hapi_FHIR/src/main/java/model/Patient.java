package model;

public class Patient {
        private Name name;

        private String resourceType = "Patient";

        private Address address;
        private String id;
        private String gender;
        private String birthDate;
        private Practitioner[] generalPractitioner;
        private Organization managingOrganization;


    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public Patient() {
        }

        public Name getName() {
            return name;
        }

        public void setName(Name name) {
            this.name = name;
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

    public Practitioner[] getGeneralPractitioner() {
            return generalPractitioner;
        }

        public void setGeneralPractitioner(Practitioner[] generalPractitioner) {
            this.generalPractitioner = generalPractitioner;
        }

        public Organization getManagingOrganization() {
            return managingOrganization;
        }

        public void setManagingOrganization(Organization managingOrganization) {
            this.managingOrganization = managingOrganization;
        }

        public String getResourceType() {
            return resourceType;
        }

        public void setResourceType(String resourceType) {
            this.resourceType = resourceType;
        }

    @Override
        public String toString() {
            return "Patient{" + "id=" + id + ", birthday=" + birthDate + '}';
        }

}
