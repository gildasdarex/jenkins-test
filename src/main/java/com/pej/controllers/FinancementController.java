package com.pej.controllers;

import com.pej.domains.Bilan;
import com.pej.domains.Candidat;
import com.pej.domains.Financement;
import com.pej.domains.Formation;
import com.pej.repository.*;
import com.pej.services.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@Controller
public class FinancementController {

    @Autowired
    private NotificationService notifyService;
    @Autowired
    private FinancementRepository financementRepository;
    @Autowired
    private FormationRepository formationRepository;
    @Autowired
    private CandidatRepository candidatRepository;

    @GetMapping("/pej/financement/{idCandidat}")
    String index(@PathVariable Integer idCandidat, ModelMap model) {
        List<Financement> financements = financementRepository.findByIdCandidat(idCandidat);
        Candidat candidat = candidatRepository.findOne(idCandidat);
        //List<Bilan> objBilans = (List<Bilan>) bilanRepository.findByFormationIdformation(id);
        //model.addAttribute("formation", formation);
        Double prixTotal = 0.0;
        for(Financement financement: financements){
            prixTotal = prixTotal + financement.getTotal();
        }
        model.addAttribute("financements", financements);
        model.addAttribute("candidat", candidat);
        model.addAttribute("prixTotal", prixTotal);
        model.addAttribute("idCandidat", idCandidat);
        return "financements";
    }


    @RequestMapping(value = "/pej/financement/add/{idCandidat}", method = RequestMethod.GET)
    String addBilan(@ModelAttribute("objFinancement") Financement objFinancement, @PathVariable Integer idCandidat, ModelMap model) {
        Candidat candidat = candidatRepository.findOne(idCandidat);
        //List<Date> dates = formation.getFormationDates();


        model.addAttribute("objFinancement", objFinancement);
        model.addAttribute("idCandidat", idCandidat);
        return "frmFinancement";
    }

    @RequestMapping(value = "/pej/financement/delete/{id}", method = RequestMethod.GET)
    String showBilan(@PathVariable Integer id, ModelMap model) {
        Financement financement = financementRepository.findOne(id);

        financementRepository.delete(financement);
        return "redirect:/pej/financement/" + id;
    }

    @PostMapping("/pej/financement/{idCandidat}")
    public String save(@ModelAttribute(value = "objFinancement") Financement objFinancement, BindingResult result, @PathVariable Integer idCandidat) {
        if (result.hasErrors()) {
            List<ObjectError> errors = result.getAllErrors();
            for (ObjectError error : errors) {
                notifyService.addErrorMessage("ECHEC DE LENREGISTREMENT " + error.getDefaultMessage());
            }
            return "redirect:/pej/financement/add/" + idCandidat;
        }

        financementRepository.save(objFinancement);
        notifyService.addInfoMessage("OPERATION EFFECTUEE AVEC SUCCESS");
        return "redirect:/pej/financement/" + idCandidat;
    }


}
