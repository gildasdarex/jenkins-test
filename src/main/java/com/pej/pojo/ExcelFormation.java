package com.pej.pojo;

import com.pej.annotations.ExcelCell;
import org.dozer.Mapping;

/**
 * Created by darextossa on 8/8/17.
 */
public class ExcelFormation {

    @ExcelCell(headerName = "Libelle", columnOrder = 1)
    private String libelle;
    @ExcelCell(headerName = "Type de formation", columnOrder = 2)
    private String typeDEFormation;
    @ExcelCell(headerName = "Phase", columnOrder = 3)
    private String phase;
    @ExcelCell(headerName = "Date de debut", columnOrder = 4)
    private String dateDeDebut;
    @ExcelCell(headerName = "Date de fin", columnOrder = 5)
    private String dateDefin;
    @ExcelCell(headerName = "Cooperative", columnOrder = 6)
    private String cooperative;
    @ExcelCell(headerName = "Formateurs", columnOrder = 7)
    private String formateurs;
    @ExcelCell(headerName = "Cavinet", columnOrder = 8)
    private String cabinet;


    public ExcelFormation() {
    }

    public String getLibelle() {
        return libelle;
    }

    public void setLibelle(String libelle) {
        this.libelle = libelle;
    }

    public String getDateDeDebut() {
        return dateDeDebut;
    }

    public void setDateDeDebut(String dateDeDebut) {
        this.dateDeDebut = dateDeDebut;
    }

    public String getDateDefin() {
        return dateDefin;
    }

    public void setDateDefin(String dateDefin) {
        this.dateDefin = dateDefin;
    }

    public String getCooperative() {
        return cooperative;
    }

    public void setCooperative(String cooperative) {
        this.cooperative = cooperative;
    }

    public String getFormateurs() {
        return formateurs;
    }

    public void setFormateurs(String formateurs) {
        this.formateurs = formateurs;
    }

    public String getCabinet() {
        return cabinet;
    }

    public void setCabinet(String cabinet) {
        this.cabinet = cabinet;
    }

    public String getTypeDEFormation() {
        return typeDEFormation;
    }

    public void setTypeDEFormation(String typeDEFormation) {
        this.typeDEFormation = typeDEFormation;
    }

    public String getPhase() {
        return phase;
    }

    public void setPhase(String phase) {
        this.phase = phase;
    }
}
