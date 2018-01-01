package com.pej.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.pej.domains.Formateur;
import com.pej.domains.Formationformateur;
import com.pej.domains.Fformation;
import com.pej.domains.Typeformation;
import com.pej.repository.FformationRepository;
import com.pej.repository.FormateurRepository;
import com.pej.repository.FormationFormateurRepository;
import com.pej.repository.TypeformationRepository;

@Controller
public class FormationformateurController {
    @Autowired
    private FformationRepository formationRepository;
    @Autowired
    private FormateurRepository formateurRepository;
    @Autowired
    private TypeformationRepository typeformationRepository;
    @Autowired
    private FformationRepository fformationRepository;
    @Autowired
    private FormationFormateurRepository formationformateurRepository;

    @GetMapping("/pej/fformations")
    String index(Model model, @ModelAttribute("objFormation") Fformation objFormation) {
        List<Fformation> formations = (List<Fformation>) formationRepository.findAll();

        model.addAttribute("formations", formations);
        return "fformations";
    }

    @GetMapping("/pej/fformations/add")
    public String editFormation(@ModelAttribute("objFormation") Fformation objFormation, ModelMap model) {
        List<Formateur> formateurs = (List<Formateur>) formateurRepository.findAll();
        List<Typeformation> typeformations = (List<Typeformation>) typeformationRepository.findAll();

        model.addAttribute("formateurs", formateurs);
        model.addAttribute("objFormation", objFormation);
        model.addAttribute("typeformations", typeformations);
        return "ffrmFormation";
    }

    @GetMapping("/pej/fformations/add/{id}")
    public String editFormation(@PathVariable Integer id, ModelMap model) {
        List<Formateur> formateurs = (List<Formateur>) formateurRepository.findAll();
        List<Typeformation> typeformations = (List<Typeformation>) typeformationRepository.findAll();
        Fformation objFormation = formationRepository.findOne(id);

        model.addAttribute("formateurs", formateurs);
        model.addAttribute("objFormation", objFormation);
        model.addAttribute("typeformations", typeformations);

        return "ffrmFormation";
    }

    @PostMapping("/pej/fformations")
    public String saveagents(@ModelAttribute(value = "objFormation") Fformation objFormation, BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "redirect:/pej/fformations";
        }

//        if (objFormation.getIdformation() != null && objFormation.getIdformation().intValue() > 0) {
//            Fformation formation = formationRepository.findOne(objFormation.getIdformation());
//            formation.setIntitule(objFormation.getIntitule());
//            formation.setDatedebut(objFormation.getDatedebut());
//            formation.setDatefin(objFormation.getDatefin());
//            formation.setTheme(objFormation.getTheme());
//            formation.setDescription(objFormation.getDescription());
//            formation.setHeuredebut(objFormation.getHeuredebut());
//            formation.setHeurefin(objFormation.getHeurefin());
//
//            formationRepository.save(formation);
//            return "redirect:/pej/fformations";
//        }
        formationRepository.save(objFormation);
        return "redirect:/pej/fformations";
    }

    @RequestMapping(value = "/pej/formationformateur/formation/{id}", method = RequestMethod.GET)
    String formationformateur(Model model, @PathVariable Integer id) {
        List<Formateur> formateurs = (List<Formateur>) formateurRepository.getNotInFormationformateur(id);
        List<Formateur> formateursformation = (List<Formateur>) formateurRepository.getInFormationformateur(id);
        Fformation formation = formationRepository.findOne(id);

        model.addAttribute("formation", formation);
        model.addAttribute("formateurs", formateurs);
        model.addAttribute("formateursformation", formateursformation);

        return "formationformateurs";
    }

    @GetMapping("/pej/formationformateur/{idformation}/presence/{idformateur}")
    public String addFormationFormateur(@PathVariable Integer idformation, @PathVariable Integer idformateur) {
        Fformation formation = formationRepository.findOne(idformation);
        Formateur formateur = formateurRepository.findOne(idformateur);

        Formationformateur formationformateur = new Formationformateur();
        formationformateur.setFormateur(formateur);
        formationformateur.setFformation(formation);
        if (formation != null && formateur != null) {
            formationformateur.setNbjours(1);
            formationformateurRepository.save(formationformateur);
        }
        return "redirect:" + "/pej/formationformateur/formation/	" + idformation;
    }
}

