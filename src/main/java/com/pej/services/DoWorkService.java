package com.pej.services;

import com.pej.domains.Agent;
import com.pej.domains.Candidat;
import com.pej.domains.Statutcandidat;
import com.pej.pojo.HelperEnum;
import com.pej.pojo.OdkCandidat;
import com.pej.repository.AgentRepository;
import com.pej.repository.CandidatRepository;
import com.pej.repository.StatutcandidatRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by darextossa on 7/23/17.
 */

@Service
public class DoWorkService {
    @Autowired
    private CandidatRepository candidatRepository;
    @Autowired private org.dozer.Mapper mapper;
    @Autowired private AgentRepository agentRepository;
    @Autowired private StatutcandidatRepository statutcandidatRepository;

    @Async("workExecutor")
    public void doWork(List<OdkCandidat> odkCandidats) {

        List<Candidat> candidats = new ArrayList<>();
        for (OdkCandidat odkCandidat : odkCandidats) {
            Candidat candidat = mapper.map(odkCandidat, Candidat.class);
            Agent agent = agentRepository.getAgent(odkCandidat.getAe());
            Statutcandidat statutcandidat = null;
            if(odkCandidat.getStatut().equals("2") )
                statutcandidat = statutcandidatRepository.findOneByIntitule("BENEFICIAIRE DE FORMATION");
            else if(odkCandidat.getStatut().equals("1") )
                statutcandidat = statutcandidatRepository.findOneByIntitule("ELIGIBLE");
            else if(odkCandidat.getStatut().equals("3") )
                statutcandidat = statutcandidatRepository.findOneByIntitule("BENEFICIAIRE DE FINANCEMENT");
            else if(odkCandidat.getStatut().equals("4") )
                statutcandidat = statutcandidatRepository.findOneByIntitule("BENEFICIAIRE DE FORMATION ET DE FINANCEMENT");
            else
                statutcandidat = statutcandidatRepository.findOneByIntitule("CANDIDAT");
            candidat.setAgent(agent);
            candidat.setStatutcandidat(statutcandidat);
            candidat.setSexe(HelperEnum.getSexe(odkCandidat.getIs_sexe()));
            candidat.setDocidentite(HelperEnum.getDocumentIdentite(odkCandidat.getCandidat_document_identite()));
            candidat.setActiviteprincipale(HelperEnum.getActivite(odkCandidat.getActivite_principale()));
            candidats.add(candidat);
        }

        for (Candidat candidat : candidats) {
            candidatRepository.save(candidat);
        }
    }
}
