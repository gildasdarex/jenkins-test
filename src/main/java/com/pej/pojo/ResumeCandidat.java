package com.pej.pojo;

import java.util.Date;

/**
 * Created by darextossa on 7/22/17.
 */
public class ResumeCandidat {

    private String idCandidat;
    private String identiteCandidat;
    private String libelleTypeFormation;
    private String libelleFormation;
    private String idFormation;
    private String identiteDesFormateurs;
    private String cabinetDesFormateurs;
    private String duree;
    private int nbrPresence;
    private int nbrAbsence;
    private Date dateDeDebut;
    private Date dateDeFin;


    public ResumeCandidat() {
    }


    public String getIdCandidat() {
        return idCandidat;
    }

    public void setIdCandidat(String idCandidat) {
        this.idCandidat = idCandidat;
    }

    public String getIdentiteCandidat() {
        return identiteCandidat;
    }

    public void setIdentiteCandidat(String identiteCandidat) {
        this.identiteCandidat = identiteCandidat;
    }

    public String getLibelleTypeFormation() {
        return libelleTypeFormation;
    }

    public void setLibelleTypeFormation(String libelleTypeFormation) {
        this.libelleTypeFormation = libelleTypeFormation;
    }

    public String getLibelleFormation() {
        return libelleFormation;
    }

    public void setLibelleFormation(String libelleFormation) {
        this.libelleFormation = libelleFormation;
    }

    public String getIdFormation() {
        return idFormation;
    }

    public void setIdFormation(String idFormation) {
        this.idFormation = idFormation;
    }

    public String getIdentiteDesFormateurs() {
        return identiteDesFormateurs;
    }

    public void setIdentiteDesFormateurs(String identiteDesFormateurs) {
        this.identiteDesFormateurs = identiteDesFormateurs;
    }

    public String getCabinetDesFormateurs() {
        return cabinetDesFormateurs;
    }

    public void setCabinetDesFormateurs(String cabinetDesFormateurs) {
        this.cabinetDesFormateurs = cabinetDesFormateurs;
    }

    public Date getDateDeDebut() {
        return dateDeDebut;
    }

    public void setDateDeDebut(Date dateDeDebut) {
        this.dateDeDebut = dateDeDebut;
    }

    public Date getDateDeFin() {
        return dateDeFin;
    }

    public void setDateDeFin(Date dateDeFin) {
        this.dateDeFin = dateDeFin;
    }

    public int getNbrPresence() {
        return nbrPresence;
    }

    public void setNbrPresence(int nbrPresence) {
        this.nbrPresence = nbrPresence;
    }

    public int getNbrAbsence() {
        return nbrAbsence;
    }

    public void setNbrAbsence(int nbrAbsence) {
        this.nbrAbsence = nbrAbsence;
    }

    public String getDuree() {
        return duree;
    }

    public void setDuree(String duree) {
        this.duree = duree;
    }
}
