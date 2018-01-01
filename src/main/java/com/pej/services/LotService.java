package com.pej.services;

import com.pej.domains.Beneficiairecooperative;
import com.pej.domains.Candidat;
import com.pej.domains.Cooperative;
import com.pej.domains.Lot;
import com.pej.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by darextossa on 7/17/17.
 */

@Service()
public class LotService {

    @Autowired
    private LotRepository lotRepository;
    @Autowired
    private CabinetRepository cabinetRepository;
    @Autowired
    private CooperativeRepository cooperativeRepository;
    @Autowired
    private CommuneRepository communeRepository;

    public boolean addCooperativeToLot(Integer idcooperative, Integer idlot){
        Cooperative cooperative = cooperativeRepository.findOne(idcooperative);
        Lot lot = lotRepository.findOne(idlot);
        cooperative.setLot(lot);
        cooperativeRepository.save(cooperative);

        return true;
    }


    public boolean removeCooperativeToLot(Integer idcooperative){
        Cooperative cooperative = cooperativeRepository.findOne(idcooperative);
        cooperative.setLot(null);
        cooperativeRepository.save(cooperative);

        return true;
    }

}
