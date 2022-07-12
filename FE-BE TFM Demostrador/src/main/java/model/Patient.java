/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;
import com.fasterxml.jackson.core.JsonProcessingException;
import tools.Usuario_DAO;

import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;

/**
 *
 * @author alumne
 */
public class Patient {

    public static String BLOOD_PRESSURE = "Blood Pressure";

    private String names;
    private String id;
    private String gender;
    private String birthday;
    private String practitioner;
    private String organization;
    private String observation;

    public Patient() {
    }

    public Patient(String names, String id, String gender, String birthday, String practitioner, String organization,
            String observation) {
        this.names = names;
        this.id = id;
        this.gender = gender;
        this.birthday = birthday;
        this.practitioner = practitioner;
        this.organization = organization;
        this.observation = observation;
    }

    public String getNames() {
        return names;
    }

    public void setNames(String names) {
        this.names = names;
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

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getPractitioner() {
        return practitioner;
    }

    public void setPractitioner(String practitioner) {
        this.practitioner = practitioner;
    }

    public String getOrganization() {
        return organization;
    }

    public void setOrganization(String organization) {
        this.organization = organization;
    }

    public String getObservation() {
        return observation;
    }

    public void setObservation(String observation) {
        this.observation = observation;
    }

    @Override
    public String toString() {
        return "Patient{" + "id=" + id + ", birthday=" + birthday + '}';
    }

    public int calculateAge() {
        LocalDate dob = LocalDate.parse(this.birthday);
        LocalDate curDate = LocalDate.now();
        return Period.between(dob, curDate).getYears();
    }

    public boolean hasKeyWord(String keyWord) throws JsonProcessingException {
        
        
        ArrayList<Observation> observations = Usuario_DAO.getObservationsData();
        for(Observation observation: observations) {
            if(observation.getIdPatient().equals(this.id) && observation.getDisplay().contains(keyWord)){
                return true;
            }
        }
        return false;
    }

    public int getPressure() throws JsonProcessingException {//solo se llama si se sabe con certeza que existe un display
        //con informacion sobre la presion
        ArrayList<Observation> observations = Usuario_DAO.getObservationsData();
        for(Observation observation: observations) {
            if(observation.getIdPatient().equals(this.id) && observation.getDisplay().contains(BLOOD_PRESSURE)){
                int index = observation.getDisplay().indexOf(BLOOD_PRESSURE);
                return Integer.parseInt(observation.getDisplay().substring(index).split(" ")[2]);
            }
        }
        return -1;
    }

}
