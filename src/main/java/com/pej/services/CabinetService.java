package com.pej.services;

import com.pej.domains.Cooperative;
import com.pej.domains.Formation;
import com.pej.domains.Formationcooperative;
import com.pej.repository.*;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by darextossa on 7/17/17.
 */

@Service()
public class CabinetService {
    Logger logger = LogManager.getLogger(CabinetService.class);

    @Autowired
    private CabinetRepository cabinetRepository;



    public boolean deleteCabinet(Integer idcabinet) {

        return true;
    }



}
