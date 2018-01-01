package com.pej.services;

import com.pej.domains.*;
import com.pej.pojo.CandidatPresence;
import com.pej.pojo.HelperEnum;
import com.pej.pojo.OdkAgent;
import com.pej.pojo.OdkCandidat;
import com.pej.repository.*;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by darextossa on 7/17/17.
 */

@Service()
public class AgentService {
    Logger logger = LogManager.getLogger(AgentService.class);

    @Autowired private org.dozer.Mapper mapper;
    @Autowired private AgentRepository agentRepository;



    public boolean saveFromFile(List<OdkAgent> odkAgents){

        List<Agent> agents = new ArrayList<>();
        for (OdkAgent odkAgent : odkAgents) {
            Agent agent = mapper.map(odkAgent, Agent.class);
            agents.add(agent);
        }

        for (Agent agent : agents) {
            agentRepository.save(agent);
        }

        return true;
    }



}
