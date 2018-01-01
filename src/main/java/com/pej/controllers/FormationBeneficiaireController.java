package com.pej.controllers;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import com.pej.pojo.CandidatPresence;
import com.pej.services.CandidatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import com.pej.domains.Arrondissement;
import com.pej.domains.Candidat;
import com.pej.domains.Formateur;
import com.pej.domains.Formation;
import com.pej.domains.Formationbeneficaire;
import com.pej.domains.Formationformateur;
import com.pej.domains.Presence;
import com.pej.repository.CandidatRepository;
import com.pej.repository.FormateurRepository;
import com.pej.repository.FormationBeneficiareRepository;
import com.pej.repository.FormationRepository;
import com.pej.repository.PresenceRepository;

@Controller
public class FormationBeneficiaireController {
    @Autowired private CandidatRepository candidatRepository;
    @Autowired private FormationBeneficiareRepository formationBeneficiareRepository;
    @Autowired private FormationRepository formationRepository;
    @Autowired private PresenceRepository presenceRepository;
    @Autowired private FormateurRepository formateurRepository;
    @Autowired private CandidatService candidatService;


    @RequestMapping(value = "/pej/formationbeneficiaires/formation/{id}", method = RequestMethod.GET)
    String index(Model model, @PathVariable Integer id) {
        List<Candidat> candidats = (List<Candidat>) candidatRepository.getNotInFormationCandidat(id);
        List<Candidat> candidatsformation = (List<Candidat>) candidatRepository.getInFormationCandidat(id);
        Formation formation = formationRepository.findOne(id);

        model.addAttribute("formation", formation);
        model.addAttribute("candidats", candidats);
        model.addAttribute("candidatsformation", candidatsformation);

        return "formationbeneficiaire";
    }

    @RequestMapping(value = "/pej/formationbeneficiaires/presence/{idformation}", method = RequestMethod.GET)
    String presence(Model model, @PathVariable Integer idformation, @RequestParam(value = "page", required = false, defaultValue = "1") Integer page) {
        List<Candidat> candidats = candidatService.getCandidatForFormation(idformation);
        Formation formation = formationRepository.findOne(idformation);
        List<CandidatPresence> candidatPresences = candidatService.convertToCandidatPresence(candidats , formation);

        List<Date> dates = formation.getFormationDates();
        List<Date> currentFormationDates = new ArrayList<Date>();
        List<Date> nextCurrentFormationDates = new ArrayList<Date>();
        int pageSize = 3;

        int fromIndex = (page - 1) * pageSize;
        if (dates == null || dates.size() < fromIndex) {
            currentFormationDates = Collections.emptyList();
        } else {
            currentFormationDates = dates.subList(fromIndex, Math.min(fromIndex + pageSize, dates.size()));
        }

        int nextPage = page + 1;

        int fromNextIndex = (nextPage - 1) * pageSize;
        if (dates == null || dates.size() < fromNextIndex) {
            nextCurrentFormationDates = Collections.emptyList();
        } else {
            nextCurrentFormationDates = dates.subList(fromNextIndex, Math.min(fromNextIndex + pageSize, dates.size()));
        }

        if(nextCurrentFormationDates.isEmpty())
            nextPage = page;

        int previousPage = 1;
        if((page -1) == 0 || (page -1) < 0) previousPage = 1 ;
        else previousPage = page - 1;

        model.addAttribute("formation", formation);
        model.addAttribute("candidats", candidatPresences);
        model.addAttribute("nextPage", nextPage);
        model.addAttribute("previousPage", previousPage);
        model.addAttribute("currentFormationDates", currentFormationDates);

        return "presence";
    }

    @GetMapping("/pej/formationbeneficiaires/{idformation}/presence/{idcandidat}")
    public String addFormationBeneficiaire(@PathVariable Integer idformation, @PathVariable Integer idcandidat, ModelMap model) {
        Formation formation = formationRepository.findOne(idformation);
        Candidat candidat = candidatRepository.findOne(idcandidat);

        Presence presence = new Presence();
        presence.setCandidat(candidat);
        presence.setFormation(formation);

        if (formation != null && candidat != null) {
            presence.setNbpresence(1);
            presenceRepository.save(presence);
        }
        return "redirect:" + "/pej/formationbeneficiaires/presence/" + idformation;


    }

    @GetMapping("/pej/formationbeneficiaires/{idformation}/candidat/{idcandidat}")
    public String addFormationPresence(@PathVariable Integer idformation, @PathVariable Integer idcandidat, ModelMap model) {
        Formation formation = formationRepository.findOne(idformation);
        Candidat candidat = candidatRepository.findOne(idcandidat);

        Formationbeneficaire formationbeneficiaire = new Formationbeneficaire();
        formationbeneficiaire.setCandidat(candidat);
        formationbeneficiaire.setFormation(formation);
        if (formation != null && candidat != null)
            formationBeneficiareRepository.save(formationbeneficiaire);


        return "redirect:" + "/pej/formationbeneficiaires/formation/" + idformation;
    }

    @GetMapping("/pej/formationbeneficiaires/rm/{idformation}/cooperative/{idcandidat}")
    public String removeFormationBenenficiaire(@PathVariable Integer idformation, @PathVariable Integer idcandidat, ModelMap model) {

        Formationbeneficaire formationbeneficiaire = formationBeneficiareRepository.findFb(idcandidat, idformation);
        if (formationbeneficiaire != null)
            formationBeneficiareRepository.delete(formationbeneficiaire.getIdformationbeneficiaire());

        return "redirect:" + "/pej/formationbeneficiaires/formation/" + idformation;
    }

    @GetMapping("/pej/formationbeneficiaires/rm/{idformation}/presence/{idcandidat}")
    public String removePresenceFormation(@PathVariable Integer idformation, @PathVariable Integer idcandidat, ModelMap model) {
        Presence presence = presenceRepository.findPresence(idcandidat, idformation);

        if (presence != null) {
            if (presence.getNbpresence() > 1) {
                presence.setNbpresence(presence.getNbpresence() - 1);
                presenceRepository.save(presence);
            } else {
                presenceRepository.delete(presence.getIdpresence());
            }
        }

        return "redirect:" + "/pej/formationbeneficiaires/presence/" + idformation;
    }



    @GetMapping("/pej/presence/add/{idcandidat}/{idformation}/{presenceDate}")
    public String addPresence(@PathVariable Integer idcandidat, @PathVariable Integer idformation, @PathVariable String presenceDate) {
        Candidat candidat = candidatRepository.findOne(idcandidat);
        Formation formation = formationRepository.findOne(idformation);

        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        Date date= null;
        try {
            date = dateFormat.parse(presenceDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        Presence presence = new Presence();
        presence.setFormation(formation);
        presence.setCandidat(candidat);
        presence.setObservation("present");
        presence.setDate(date);

        presenceRepository.save(presence);

        return "redirect:" + "/pej/formationbeneficiaires/presence/" + idformation;
    }


    @GetMapping("/pej/presence/old/add/{idcandidat}/{idformation}/{presenceDate}")
    public String changePresenceToAbsence(@PathVariable Integer idcandidat, @PathVariable Integer idformation, @PathVariable String presenceDate) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        Date date= null;
        try {
            date = dateFormat.parse(presenceDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        Presence presence = presenceRepository.findByCandidatIdcandidatAndFormationIdformationAndDate(idcandidat, idformation, date);
        presence.setObservation("absent");

        presenceRepository.save(presence);

        return "redirect:" + "/pej/formationbeneficiaires/presence/" + idformation;
    }


    @GetMapping("/pej/presence/rm/{idcandidat}/{idformation}/{presenceDate}")
    public String addAbsence(@PathVariable Integer idcandidat, @PathVariable Integer idformation, @PathVariable String presenceDate) {
        Candidat candidat = candidatRepository.findOne(idcandidat);
        Formation formation = formationRepository.findOne(idformation);

        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        Date date= null;
        try {
            date = dateFormat.parse(presenceDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        Presence presence = new Presence();
        presence.setFormation(formation);
        presence.setCandidat(candidat);
        presence.setObservation("absent");
        presence.setDate(date);

        presenceRepository.save(presence);

        return "redirect:" + "/pej/formationbeneficiaires/presence/" + idformation;
    }


    @GetMapping("/pej/presence/old/rm/{idcandidat}/{idformation}/{presenceDate}")
    public String changeAbsenceToPresence(@PathVariable Integer idcandidat, @PathVariable Integer idformation, @PathVariable String presenceDate) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        Date date= null;
        try {
            date = dateFormat.parse(presenceDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        Presence presence = presenceRepository.findByCandidatIdcandidatAndFormationIdformationAndDate(idcandidat, idformation, date);
        presence.setObservation("present");

        presenceRepository.save(presence);

        return "redirect:" + "/pej/formationbeneficiaires/presence/" + idformation;
    }


}
