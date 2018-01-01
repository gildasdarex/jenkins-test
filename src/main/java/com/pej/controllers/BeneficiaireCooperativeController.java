package com.pej.controllers;

import java.util.List;
import java.util.stream.IntStream;

import com.pej.domains.*;
import com.pej.pojo.FilterResult;
import com.pej.repository.*;
import com.pej.services.BeneficiaireCooperativeService;
import com.pej.services.FilterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import com.pej.services.NotificationService;

import javax.servlet.http.HttpServletRequest;

@Controller
public class BeneficiaireCooperativeController {
    @Autowired private CandidatRepository candidatRepository;
    @Autowired private BeneficiaireCooperativeRepository beneficiaireCooperativeRepository;
    @Autowired private CooperativeRepository cooperativeRepository;
    @Autowired private NotificationService notifyService;
    @Autowired private BeneficiaireCooperativeService beneficiaireCooperativeService;
    @Autowired private DepartementRepository departementRepository;
    @Autowired private CommuneRepository communeRepository;
    @Autowired private StatutcandidatRepository statutcandidatRepository;
    @Autowired private HttpServletRequest request;
    @Autowired private FilterService filterService;


    @RequestMapping(value = "/pej/beneficiairecooperative/cooperative/{id}", method = RequestMethod.GET)
    String index(Model model, @PathVariable Integer id, @RequestParam(value = "page", required = false, defaultValue = "0") int page) {
        Cooperative cooperative = cooperativeRepository.findOne(id);
        List<Candidat> candidatscooperative = (List<Candidat>) candidatRepository.getInCooperativeCandidat(id);
        List<Commune> communes = (List<Commune>) communeRepository.findAll();
        List<Departement> departements = (List<Departement>) departementRepository.findAll();
        List<Statutcandidat> statuts = (List<Statutcandidat>) statutcandidatRepository.findAll();


        User principal = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();


        model.addAttribute("candidats", candidatscooperative);
        model.addAttribute("cooperative", cooperative);
        model.addAttribute("communes", communes);
        model.addAttribute("departements", departements);
        model.addAttribute("statuts", statuts);
        model.addAttribute("username", principal.getUsername());

        return "beneficiairecooperative";
    }

    @RequestMapping(value = "/pej/beneficiairecooperative/add/cooperative/{id}", method = RequestMethod.GET)
    String addNewBeneficiaire(Model model, @PathVariable Integer id) {
        Cooperative cooperative = cooperativeRepository.findOne(id);
        FilterResult filterResult = filterService.filterFreeCandidats(request);

        List<Commune> communes = (List<Commune>) communeRepository.findAll();
        List<Departement> departements = (List<Departement>) departementRepository.findAll();
        List<Statutcandidat> statuts = (List<Statutcandidat>) statutcandidatRepository.findAll();
        List<String> arrondissements = (List<String>) candidatRepository.selectArrondissement();



        User principal = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();


        String hasNext = "true";
        String hasPrevious = "true";

        if(filterResult.getCurrentPage() == filterResult.getNextPage()) hasNext = "false";
        if(filterResult.getCurrentPage() == filterResult.getPreviousPage()) hasPrevious = "false";


        model.addAttribute("cooperative", cooperative);
        model.addAttribute("candidats", filterResult.getData());
        model.addAttribute("communes", communes);
        model.addAttribute("departements", departements);
        model.addAttribute("statuts", statuts);
        model.addAttribute("username", principal.getUsername());
        model.addAttribute("nextPage", filterResult.getNextPage());
        model.addAttribute("currentPage", filterResult.getCurrentPage());
        model.addAttribute("previousPage", filterResult.getPreviousPage());
        model.addAttribute("hasNext", hasNext);
        model.addAttribute("hasPrevious", hasPrevious);
        model.addAttribute("listOfPages", filterResult.getListOfPages());
        model.addAttribute("criteria", filterResult.getCriteriaParameter());
        model.addAttribute("arrondissements", arrondissements);


        return "addbeneficiairecooperative";
    }

    @RequestMapping(value = "/pej/beneficiairecooperative/search/cooperative", method = RequestMethod.POST)
    String getFreeCandidatBySearch(Model model) {
        Integer id = Integer.parseInt(request.getParameter("idcooperative"));
        Cooperative cooperative = cooperativeRepository.findOne(id);
        FilterResult filterResult = filterService.filterFreeCandidats(request);

        List<Commune> communes = (List<Commune>) communeRepository.findAll();
        List<Departement> departements = (List<Departement>) departementRepository.findAll();
        List<Statutcandidat> statuts = (List<Statutcandidat>) statutcandidatRepository.findAll();
        List<String> arrondissements = (List<String>) candidatRepository.selectArrondissement();



        User principal = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();


        String hasNext = "true";
        String hasPrevious = "true";

        if(filterResult.getCurrentPage() == filterResult.getNextPage()) hasNext = "false";
        if(filterResult.getCurrentPage() == filterResult.getPreviousPage()) hasPrevious = "false";


        model.addAttribute("cooperative", cooperative);
        model.addAttribute("candidats", filterResult.getData());
        model.addAttribute("communes", communes);
        model.addAttribute("departements", departements);
        model.addAttribute("statuts", statuts);
        model.addAttribute("username", principal.getUsername());
        model.addAttribute("nextPage", filterResult.getNextPage());
        model.addAttribute("currentPage", filterResult.getCurrentPage());
        model.addAttribute("previousPage", filterResult.getPreviousPage());
        model.addAttribute("hasNext", hasNext);
        model.addAttribute("hasPrevious", hasPrevious);
        model.addAttribute("listOfPages", filterResult.getListOfPages());
        model.addAttribute("criteria", filterResult.getCriteriaParameter());
        model.addAttribute("arrondissements", arrondissements);


        return "addbeneficiairecooperative";
    }


    @GetMapping("/pej/beneficiairecooperative/{idgroupe}/candidat/{idcandidat}")
    public String addFormationBeneficiaire(@PathVariable Integer idgroupe, @PathVariable Integer idcandidat) {

        boolean success = beneficiaireCooperativeService.addBeneficiaire(idgroupe,idcandidat);
        if (success) {
            notifyService.addSucceesgMessage("CANDIDATA AJOUTE A LA COOPERATIVE AVEC SUCCESS.");
        } else {
            notifyService.addWarningMessage("ECHEC ENREGISTREMENT. CANDIDAT OU COOPERATIVE NON TROUVE");
        }
        return "redirect:" + "/pej/beneficiairecooperative/cooperative/" + idgroupe;
    }


    @PostMapping("/pej/beneficiairecooperative/multiple/{idgroupe}")
    public String addMultipleFormationBeneficiaire(@PathVariable Integer idgroupe,@RequestParam("table_records") List<String> table_records) {
        for(String id :table_records){
            beneficiaireCooperativeService.addBeneficiaire(idgroupe, Integer.parseInt(id));
        }

        return "redirect:" + "/pej/beneficiairecooperative/cooperative/" + idgroupe;
    }


    @GetMapping("/pej/beneficiairecooperative/rm/{idgroupe}/candidat/{idcandidat}")
    public String removeFormationBenenficiaire(@PathVariable Integer idgroupe, @PathVariable Integer idcandidat) {

        beneficiaireCooperativeService.removeCandidatFromCooperative(idgroupe, idcandidat);
        return "redirect:" + "/pej/beneficiairecooperative/cooperative/" + idgroupe;
    }

    @PostMapping("/pej/beneficiairecooperative/remove/{idgroupe}")
    public String removeMultipleFormationBeneficiaire(@PathVariable Integer idgroupe,@RequestParam("table_records") List<String> table_records) {
        for(String id :table_records){
            beneficiaireCooperativeService.removeCandidatFromCooperative(idgroupe, Integer.parseInt(id));
        }

        return "redirect:" + "/pej/beneficiairecooperative/cooperative/" + idgroupe;
    }

}
