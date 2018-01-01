package com.pej.pojo;


import com.pej.annotations.ExcelCell;
import org.dozer.Mapping;

public class ExcelFormateur {

    @Mapping("nom")
    @ExcelCell(headerName = "Nom", columnOrder = 1)
    private String nom;
    @Mapping("prenom")
    @ExcelCell(headerName = "Prenom", columnOrder = 2)
    private String prenom;
    @Mapping("cabinet.intitule")
    @ExcelCell(headerName = "Cabinet", columnOrder = 3)
    private String cabinet;
    @Mapping("email")
    @ExcelCell(headerName = "Email", columnOrder = 4)
    private String email;
    @Mapping("telephone")
    @ExcelCell(headerName = "Telephone", columnOrder = 5)
    private String telephone;
    @ExcelCell(headerName = "Nombre de formations donnees", columnOrder = 6)
    private String nombreDeFormations;


    public ExcelFormateur() {
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getCabinet() {
        return cabinet;
    }

    public void setCabinet(String cabinet) {
        this.cabinet = cabinet;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getNombreDeFormations() {
        return nombreDeFormations;
    }

    public void setNombreDeFormations(String nombreDeFormations) {
        this.nombreDeFormations = nombreDeFormations;
    }
}
