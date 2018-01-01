package com.pej.pojo;

/**
 * Created by darextossa on 9/8/17.
 */
public class TypeFormationReport {

    private String typeFormation;
    private String nbrePresence;

    public TypeFormationReport() {
    }

    public TypeFormationReport(String typeFormation, String nbrePresence) {
        this.typeFormation = typeFormation;
        this.nbrePresence = nbrePresence;
    }

    public String getTypeFormation() {
        return typeFormation;
    }

    public void setTypeFormation(String typeFormation) {
        this.typeFormation = typeFormation;
    }

    public String getNbrePresence() {
        return nbrePresence;
    }

    public void setNbrePresence(String nbrePresence) {
        this.nbrePresence = nbrePresence;
    }
}
