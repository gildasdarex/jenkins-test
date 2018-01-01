package com.pej.pojo;

import com.pej.domains.Formation;
import com.pej.domains.Presence;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by darextossa on 7/21/17.
 */
public class CandidatPresence {

    private Integer idCandidat;
    private String identite;
    private List<Presence> presences;
    private Formation formation;

    public CandidatPresence() {
    }

    public Integer getIdCandidat() {
        return idCandidat;
    }

    public void setIdCandidat(Integer idCandidat) {
        this.idCandidat = idCandidat;
    }

    public String getIdentite() {
        return identite;
    }

    public void setIdentite(String identite) {
        this.identite = identite;
    }

    public List<Presence> getPresences() {
        return presences;
    }

    public void setPresences(List<Presence> presences) {
        this.presences = presences;
    }

    public Formation getFormation() {
        return formation;
    }

    public void setFormation(Formation formation) {
        this.formation = formation;
    }

    public String isPresent(Date date){

        Presence presence = presences.stream()
                .filter(x -> date.equals(x.getDate()))
                .findAny()
                .orElse(null);

        if(presence == null) {
            return "DATE A VENIR";
        }

        return presence.getObservation().toUpperCase();

    }


    public boolean isCurrentDate(Date date){
        Calendar calDate = Calendar.getInstance();
        calDate.setTime(date);

        Date currentDate = new Date();
        Calendar calCurrentDate = Calendar.getInstance();
        calCurrentDate.setTime(currentDate);

        int duration = calDate.compareTo(calCurrentDate);

        if(duration>0) return false;
        else return true;
    }
}
