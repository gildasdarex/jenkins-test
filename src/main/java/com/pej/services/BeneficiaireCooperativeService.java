package com.pej.services;

import com.pej.domains.Beneficiairecooperative;
import com.pej.domains.Candidat;
import com.pej.domains.Cooperative;
import com.pej.repository.BeneficiaireCooperativeRepository;
import com.pej.repository.CandidatRepository;
import com.pej.repository.CooperativeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by darextossa on 7/17/17.
 */

@Service()
public class BeneficiaireCooperativeService {

    @Autowired
    private CandidatRepository candidatRepository;
    @Autowired
    private BeneficiaireCooperativeRepository beneficiaireCooperativeRepository;
    @Autowired
    private CooperativeRepository cooperativeRepository;

    public boolean addBeneficiaire(Integer idgroupe, Integer idcandidat){
        Cooperative cooperative = cooperativeRepository.findOne(idgroupe);
        Candidat candidat = candidatRepository.findOne(idcandidat);
        Beneficiairecooperative beneficiairecooperative = new Beneficiairecooperative();
        beneficiairecooperative.setCandidat(candidat);
        beneficiairecooperative.setCooperative(cooperative);

        if (cooperative != null && candidat != null) {
            beneficiaireCooperativeRepository.save(beneficiairecooperative);
            return true;
        } else  return false;
    }


    public boolean removeCandidatFromCooperative(Integer idgroupe, Integer idcandidat){
        Beneficiairecooperative beneficiairecooperative = beneficiaireCooperativeRepository.findBc(idcandidat, idgroupe);
        if (beneficiairecooperative != null)
            beneficiaireCooperativeRepository.delete(beneficiairecooperative.getIdcandidatgroupe());
        return true;
    }

    public List<Beneficiairecooperative> getBeneficiairecooperativeForCandidat(Integer idCandidat){
        return beneficiaireCooperativeRepository.findByCandidatIdcandidat(idCandidat);
    }


    public int getNextPage(int page ,Integer cooperativeId){
        page = page + 1;

        Pageable pageable =  new PageRequest( page, 100 );

        List<Candidat> candidats = (List<Candidat>) candidatRepository.getInCooperativeCandidat(cooperativeId);

        if(candidats !=null && candidats.size() != 0) return page;
        else return page-1;
    }

    public int getPreviousPage(int page, Integer cooperativeId){
        if(page ==0) return 0;
        page = page - 1;

        Pageable pageable =  new PageRequest( page, 100 );

        List<Candidat> candidats = (List<Candidat>) candidatRepository.getInCooperativeCandidat(cooperativeId);

        if(candidats !=null && candidats.size() != 0) return page;
        else return page+1;
    }



}
