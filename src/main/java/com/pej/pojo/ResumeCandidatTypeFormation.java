package com.pej.pojo;

import java.util.List;

/**
 * Created by darextossa on 9/8/17.
 */
public class ResumeCandidatTypeFormation {

    private String idCandidat;
    private String identiteCandidat;
    private List<TypeFormationReport> typeFormationReports;

    public ResumeCandidatTypeFormation() {
    }

    public ResumeCandidatTypeFormation(String idCandidat, String identiteCandidat, List<TypeFormationReport> typeFormationReports) {
        this.idCandidat = idCandidat;
        this.identiteCandidat = identiteCandidat;
        this.typeFormationReports = typeFormationReports;
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

    public List<TypeFormationReport> getTypeFormationReports() {
        return typeFormationReports;
    }

    public void setTypeFormationReports(List<TypeFormationReport> typeFormationReports) {
        this.typeFormationReports = typeFormationReports;
    }
}
