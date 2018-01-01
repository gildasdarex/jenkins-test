package com.pej.pojo;

import com.pej.annotations.ExcelCell;
import org.dozer.Mapping;

/**
 * Created by darextossa on 8/8/17.
 */
public class ExcelCandidat {

    @Mapping("nom")
    @ExcelCell(headerName = "Nom", columnOrder = 1)
    private String nom;
    @Mapping("prenom")
    @ExcelCell(headerName = "Prenom", columnOrder = 2)
    private String prenom;
    @Mapping("age")
    @ExcelCell(headerName = "Age", columnOrder = 3)
    private String age;
    @Mapping("numerocandidat")
    @ExcelCell(headerName = "Numero du candidat", columnOrder = 4)
    private String numerocandidat;
    @Mapping("arrondissement")
    @ExcelCell(headerName = "Arrondissement", columnOrder = 5)
    private String arrondissement;
    @Mapping("telprincipal")
    @ExcelCell(headerName = "Telephone", columnOrder = 6)
    private String telephone;

    public ExcelCandidat() {
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

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getNumerocandidat() {
        return numerocandidat;
    }

    public void setNumerocandidat(String numerocandidat) {
        this.numerocandidat = numerocandidat;
    }

    public String getArrondissement() {
        return arrondissement;
    }

    public void setArrondissement(String arrondissement) {
        this.arrondissement = arrondissement;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }
}
