package com.pej.controllers;

import java.util.List;

import com.pej.services.CooperativeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import com.pej.domains.Candidat;
import com.pej.domains.Cooperative;
import com.pej.domains.Formation;
import com.pej.domains.Formationcooperative;
import com.pej.domains.Presence;
import com.pej.repository.CooperativeRepository;
import com.pej.repository.CandidatRepository;
import com.pej.repository.FormationBeneficiareRepository;
import com.pej.repository.FormationCooperativeRepository;
import com.pej.repository.FormationRepository;
import com.pej.repository.PresenceRepository;

@Controller
public class FormationCooperativeController {
    @Autowired
    private CooperativeRepository cooperativeRepository;
    @Autowired
    private FormationCooperativeRepository formationcooperativeRepository;
    @Autowired
    private FormationRepository formationRepository;
    @Autowired
    private CooperativeService cooperativeService;
    @Autowired
    private CandidatRepository candidatRepository;


    @RequestMapping(value = "/pej/formationgroupe/formation/{id}", method = RequestMethod.GET)
    String index(Model model, @PathVariable Integer id) {
        List<Cooperative> cooperatives = (List<Cooperative>) cooperativeRepository.getNotInFormationCooperative(id);
        List<Cooperative> cooperativeformation = (List<Cooperative>) cooperativeRepository.getInFormationCooperative(id);

        Formation formation = formationRepository.findOne(id);

        model.addAttribute("formation", formation);
        model.addAttribute("cooperatives", cooperatives);
        model.addAttribute("cooperativeformation", cooperativeformation);

        return "formationcooperatives";
    }

    @GetMapping("/pej/formationgroupe/{idformation}/cooperative/{idgroupe}")
    public String addFormationToCooperative(@PathVariable Integer idformation, @PathVariable Integer idgroupe) {
        cooperativeService.addFormationToCooperative(idformation, idgroupe);

        return "redirect:" + "/pej/formationgroupe/formation/" + idformation;
    }


    @PostMapping("/pej/formationgroupe/multiple/cooperative/{idformation}")
    public String addFormationToMultipleCooperative(@PathVariable Integer idformation, @RequestParam("table_records") List<String> table_records) {
        for(String id : table_records){
            cooperativeService.addFormationToCooperative(idformation, Integer.parseInt(id));
        }

        return "redirect:" + "/pej/formationgroupe/formation/" + idformation;
    }

//    @GetMapping("/pej/formationgroupe/rm/{idformation}/cooperative/{idcandidat}")
//    public String removeFormationBenenficiaire(@PathVariable Integer idformation, @PathVariable Integer idcandidat) {
//
//        Formationcooperative formationcooperative = formationcooperativeRepository.findFb(idcandidat, idformation);
//        if (formationcooperative != null)
//            formationcooperativeRepository.delete(formationcooperative.getIdformationcooperative());
//
//        return "redirect:" + "/pej/formationbeneficiaires/formation/" + idformation;
//    }


    @GetMapping("/pej/formationgroupe/rm/{idformation}/cooperative/{idgroupe}")
    public String removeFormationFromCooperative(@PathVariable Integer idformation, @PathVariable Integer idgroupe) {

        cooperativeService.deleteFormationFromCooperative(idformation, idgroupe);
        return "redirect:" + "/pej/formationgroupe/formation/" + idformation;
    }


    @PostMapping("/pej/formationgroupe/multiple/rm/cooperative/{idformation}")
    public String removeFormationFromMultipleCooperative(@PathVariable Integer idformation, @RequestParam("table_records") List<String> table_records) {
        for(String id : table_records){
            cooperativeService.deleteFormationFromCooperative(idformation, Integer.parseInt(id));
        }

        return "redirect:" + "/pej/formationgroupe/formation/" + idformation;
    }


}
