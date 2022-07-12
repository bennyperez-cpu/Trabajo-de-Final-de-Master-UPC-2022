/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

/**
 *
 * @author alumne
 */
public class User {
    private String Nombres;
    private String Apellidos;
    private String Nombre_de_Usuario;
    private String Contrasenha;
    private String Id_FHIR;

    public User() {
    }

    public User(String nombres, String apellidos, String nombre_de_Usuario, String contrasenha, String id_FHIR) {
        Nombres = nombres;
        Apellidos = apellidos;
        Nombre_de_Usuario = nombre_de_Usuario;
        Contrasenha = contrasenha;
        Id_FHIR = id_FHIR;
    }

    public String getNombres() {
        return Nombres;
    }

    public void setNombres(String nombres) {
        Nombres = nombres;
    }

    public String getApellidos() {
        return Apellidos;
    }

    public void setApellidos(String apellidos) {
        Apellidos = apellidos;
    }

    public String getNombre_de_Usuario() {
        return Nombre_de_Usuario;
    }

    public void setNombre_de_Usuario(String nombre_de_Usuario) {
        Nombre_de_Usuario = nombre_de_Usuario;
    }

    public String getContrasenha() {
        return Contrasenha;
    }

    public void setContrasenha(String contrasenha) {
        Contrasenha = contrasenha;
    }

    public String getId_FHIR() {
        return Id_FHIR;
    }

    public void setId_FHIR(String id_FHIR) {
        Id_FHIR = id_FHIR;
    }

    

    






    
    
}
