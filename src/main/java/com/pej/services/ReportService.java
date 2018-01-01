package com.pej.services;

import com.pej.domains.*;
import com.pej.pojo.ResumeCandidat;
import com.pej.repository.CabinetRepository;
import com.pej.repository.CandidatRepository;
import com.pej.repository.PresenceRepository;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * Created by darextossa on 7/17/17.
 */

@Service()
public class ReportService {
    Logger logger = LogManager.getLogger(ReportService.class);

    @Autowired private CandidatRepository candidatRepository;
    @Autowired private BeneficiaireCooperativeService beneficiaireCooperativeService;
    @Autowired private PresenceRepository presenceRepository;



    public List<ResumeCandidat> getFormationResume(Integer idcandidat) {
        List<ResumeCandidat> resumeCandidats = new ArrayList<>();
        //get candidat
        Candidat candidat = candidatRepository.findOne(idcandidat);

        // get all formation follow by a candidat
        List<Beneficiairecooperative> beneficiairecooperatives = beneficiaireCooperativeService.getBeneficiairecooperativeForCandidat(idcandidat);

        List<Formation> formations = new ArrayList<>();
        for(Beneficiairecooperative beneficiairecooperative: beneficiairecooperatives){
            Cooperative cooperative = beneficiairecooperative.getCooperative();
            Set<Formationcooperative> formationcooperatives = cooperative.getFormationcooperatives();
            for(Formationcooperative formationcooperative : formationcooperatives){
                formations.add(formationcooperative.getFormation());
            }
        }

        for(Formation formation : formations){
            ResumeCandidat resumeCandidat = new ResumeCandidat();

            resumeCandidat.setIdCandidat(idcandidat.toString());
            resumeCandidat.setIdentiteCandidat(candidat.getIdentite());
            resumeCandidat.setIdFormation(formation.getIdformation().toString());
            resumeCandidat.setLibelleFormation(formation.getIntitule());
            resumeCandidat.setIdentiteDesFormateurs(formation.getFormateurNames());
            resumeCandidat.setLibelleTypeFormation(formation.getTypeformation().getIntitule());

            resumeCandidat.setDateDeDebut(formation.getDateformation());
            resumeCandidat.setDateDeFin(formation.getDatefin());

            List<Presence> presences = presenceRepository.findByCandidatIdcandidatAndFormationIdformationAndObservation(idcandidat,
                    formation.getIdformation(), "present"
                    );

            List<Presence> absences = presenceRepository.findByCandidatIdcandidatAndFormationIdformationAndObservation(idcandidat,
                    formation.getIdformation(), "absent"
            );

            resumeCandidat.setDuree(formation.getFormationDates().size() + " jours");
            resumeCandidat.setNbrAbsence(absences.size());
            resumeCandidat.setNbrPresence(presences.size());


            resumeCandidats.add(resumeCandidat);

        }

        return resumeCandidats;
    }




}
