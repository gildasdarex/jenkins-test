package com.pej.controllers;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import com.pej.domains.*;
import com.pej.pojo.FilterResult;
import com.pej.repository.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.pej.services.NotificationService;

@Controller
public class BilanController {
    @Autowired
    private CooperativeRepository cooperativeRepository;
    @Autowired
    private CommuneRepository communeRepository;

    @Autowired
    private LotRepository lotRepository;
    @Autowired
    private NotificationService notifyService;
    @Autowired
    private TypeformationRepository typeformationRepository;
    @Autowired
    private CabinetRepository cabinetRepository;
    @Autowired
    private BilanRepository bilanRepository;
    @Autowired
    private FormationRepository formationRepository;
    @Autowired
    private FormationCooperativeRepository formationCooperativeRepository;

    @GetMapping("/pej/bilan/{id}")
    String index(@PathVariable Integer id, ModelMap model) {
        Formation formation = formationRepository.findOne(id);
        List<Bilan> objBilans = (List<Bilan>) bilanRepository.findByFormationIdformation(id);
        model.addAttribute("formation", formation);
        model.addAttribute("bilans", objBilans);
        return "bilans";
    }


    @RequestMapping(value = "/pej/bilan/add/{id}", method = RequestMethod.GET)
    String addBilan(@ModelAttribute("objBilan") Bilan objBilan, @PathVariable Integer id, ModelMap model) {
        Formation formation = formationRepository.findOne(id);
        List<Date> dates = formation.getFormationDates();


        model.addAttribute("objBilan", objBilan);
        model.addAttribute("formation", formation);
        model.addAttribute("datesFormations", dates);
        return "frmBilan";
    }

    @RequestMapping(value = "/pej/bilan/delete/{id}", method = RequestMethod.GET)
    String showBilan(@PathVariable Integer id, ModelMap model) {
        Bilan objBilan = bilanRepository.findOne(id);
//        Cooperative objCooperative = objBilan.getCooperative();
//        List<Formationcooperative> formationcooperatives = new ArrayList<Formationcooperative>(objCooperative.getFormationcooperatives());
//        System.out.println("Formation cooperative " + formationcooperatives.get(0).getFormation().getIntitule());
//
//        model.addAttribute("cooperative", objCooperative);
//        model.addAttribute("objBilan", objBilan);
//        model.addAttribute("formationcooperatives", formationcooperatives);
        int idFormation = objBilan.getFormation().getIdformation();
        bilanRepository.delete(objBilan);
        return "redirect:/pej/bilan/" + idFormation;
    }

    @PostMapping("/pej/bilan/{id}")
    public String save(@ModelAttribute(value = "objBilan") Bilan objBilan, BindingResult result, @PathVariable Integer id) {
        if (result.hasErrors()) {
            List<ObjectError> errors = result.getAllErrors();
            for (ObjectError error : errors) {
                notifyService.addErrorMessage("ECHEC DE LENREGISTREMENT " + error.getDefaultMessage());
            }
            return "redirect:/pej/bilan/add/" + id;
        }

        bilanRepository.save(objBilan);
        notifyService.addInfoMessage("OPERATION EFFECTUEE AVEC SUCCESS");
        return "redirect:/pej/bilan/" + id;
    }

    @RequestMapping(value = "/pej/formations/dateformation/{id}", method = RequestMethod.GET)
    public
    @ResponseBody
    List<Date> findDateFormation(@PathVariable Integer id) {
        Formation objformation = formationRepository.findOne(id);
        List<Date> dateFormations = objformation.getFormationDates();
        return dateFormations;
    }
}
