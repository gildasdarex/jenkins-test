package com.pej.controllers;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import com.pej.domains.*;
import com.pej.pojo.FilterResult;
import com.pej.repository.*;
import com.pej.services.CooperativeService;
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

import com.pej.services.NotificationService;

@Controller
public class CooperativeController {
    @Autowired private CooperativeRepository cooperativeRepository;
    @Autowired private CommuneRepository communeRepository;
    @Autowired private DoncooperativeRepository doncooperativeRepository;
    @Autowired private LotRepository lotRepository;
    @Autowired private NotificationService notifyService;
    @Autowired private CooperativeService cooperativeService;
    @Autowired private TypeformationRepository typeformationRepository;
    @Autowired private CabinetRepository cabinetRepository;

    @GetMapping("/pej/cooperatives")
    String index(Model model, @ModelAttribute("objCooperative") Cooperative objCooperative) {
        List<Cooperative> cooperatives = (List<Cooperative>) cooperativeRepository.findAll();
        List<Lot> lots = (List<Lot>) lotRepository.findAll();
        List<Typeformation> typeformations = (List<Typeformation>) typeformationRepository.findAll();
        List<Cabinet> cabinets = (List<Cabinet>) cabinetRepository.findAll();



        model.addAttribute("cooperatives", cooperatives);
        model.addAttribute("total", cooperatives.size());
        model.addAttribute("lots", lots);
        model.addAttribute("typeFormations", typeformations);
        model.addAttribute("cabinets", cabinets);
        return "cooperatives";
    }

    @GetMapping("/pej/cooperatives/add")
    public String create(@ModelAttribute("objCooperative") Cooperative objCooperative, ModelMap model) {
        List<Commune> communes = new ArrayList<>();
        List<Lot> lots = (List<Lot>) lotRepository.findAll();
        List<Cooperative> cooperatives = (List<Cooperative>) cooperativeRepository.findAll();
        List<Typeformation> typeformations = (List<Typeformation>) typeformationRepository.findAll();


        for (Lot lot : lots) {
            String lotCommune = lot.getCommune();

            if(lotCommune != null && !lotCommune.isEmpty()){
                String [] idCommunes = lotCommune.split(",");
                for(String id : idCommunes){
                    communes.add(communeRepository.findOne(Integer.parseInt(id)));
                }
            }
        }

        if(communes.isEmpty()) communes = (List<Commune>) communeRepository.findAll();

        model.addAttribute("lots", lots);
        model.addAttribute("objCooperative", objCooperative);
        model.addAttribute("communes", communes);

        return "frmCooperative";
    }

    @GetMapping("/pej/cooperatives/{id}")
    public String edit(@PathVariable Integer id, ModelMap model) {
        List<Commune> communes = new ArrayList<>();
        List<Lot> lots = (List<Lot>) lotRepository.findAll();
        Cooperative objCooperative = cooperativeRepository.findOne(id);

        for (Lot lot : lots) {
            String communeIds = lot.getCommune();
            String [] communeIdsArray = communeIds.split(",");
            for(String communeId: communeIdsArray){
                communes.add(communeRepository.findOne(Integer.parseInt(communeId)));
            }
        }

        model.addAttribute("lots", lots);
        model.addAttribute("objCooperative", objCooperative);
        model.addAttribute("communes", communes);
        return "frmCooperative";
    }

    @PostMapping("/pej/cooperatives")
    public String save(@ModelAttribute(value = "objCooperative") Cooperative objCooperative, BindingResult result) {
        if (result.hasErrors()) {
            List<ObjectError> errors = result.getAllErrors();
            for (ObjectError error : errors) {
            }
            notifyService.addErrorMessage("ECHEC DE LENREGISTREMENT ");
            return "frmCooperative";
        }


        if (objCooperative.getIdgroupe() != null && objCooperative.getIdgroupe().intValue() > 0) {
            Cooperative cooperative = cooperativeRepository.findOne(objCooperative.getIdgroupe());
            cooperative.setDescription(objCooperative.getDescription());
            cooperative.setLibgroupe(objCooperative.getLibgroupe());
        }

        try {
            cooperativeRepository.save(objCooperative);
        }catch (Exception e){
            notifyService.addErrorMessage("ECHEC DE LENREGISTREMENT ");
            return "redirect:/pej/cooperatives";
        }
        notifyService.addInfoMessage("Opération effectué avec succès");
        return "redirect:/pej/cooperatives";
    }


    /*Récupérer la page d'accord de don à un groupe*/
    @RequestMapping(value = "/pej/cooperatives/don/{id}", method = RequestMethod.GET)
    public String getDonPage(@PathVariable Integer id, ModelMap model) {
        List<DonCooperative> dons = (List<DonCooperative>) doncooperativeRepository.getDonByCopperative(id);

        Cooperative cooperative = cooperativeRepository.findOne(id);
        DonCooperative objDon = new DonCooperative();
        objDon.setCooperative(cooperative);

        model.addAttribute("dons", dons);
        model.addAttribute("cooperative", cooperative);
        model.addAttribute("objDon", objDon);

        return "FrmDonCooperative";
    }

    /*Enregistrer un don pour une coopérative*/
    @PostMapping("/pej/cooperative/don")
    public String saveDon(@Valid @ModelAttribute(value = "objDon") DonCooperative objDon, BindingResult result) {
        if (result.hasErrors()) {
            return "redirect:/pej/cooperatives";
        }
        Cooperative cooperative = cooperativeRepository.findOne(objDon.getCooperative().getIdgroupe());
        if (cooperative != null) {
            objDon.setCooperative(cooperative);
            doncooperativeRepository.save(objDon);
        }

        return "redirect:/pej/cooperatives";
    }



    @SuppressWarnings("unchecked")
    @PostMapping("/pej/cooperatives/search")
    String searchContrat(HttpServletRequest request, Model model) {

        //FilterResult filterResult = filterService.filter(request, Candidat.class);

        String criteriaParameter = request.getParameter("criteria");


        if(criteriaParameter.equals("[]")) return "redirect:/pej/cooperatives";


        FilterResult filterResult = cooperativeService.filter(request);

        List<Lot> lots = (List<Lot>) lotRepository.findAll();
        List<Cabinet> cabinets = (List<Cabinet>) cabinetRepository.findAll();
        List<Typeformation> typeformations = (List<Typeformation>) typeformationRepository.findAll();



        User principal = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();


        String hasNext = "true";
        String hasPrevious = "true";

        if(filterResult.getCurrentPage() == filterResult.getNextPage()) hasNext = "false";
        if(filterResult.getCurrentPage() == filterResult.getPreviousPage()) hasPrevious = "false";



        model.addAttribute("lots", lots);
        model.addAttribute("typeFormations", typeformations);
        model.addAttribute("cabinets", cabinets);
        model.addAttribute("cooperatives", filterResult.getData());
        model.addAttribute("total", filterResult.getTotal());
        model.addAttribute("username", principal.getUsername());
        model.addAttribute("nextPage", filterResult.getNextPage());
        model.addAttribute("currentPage", filterResult.getCurrentPage());
        model.addAttribute("previousPage", filterResult.getPreviousPage());
        model.addAttribute("hasNext", hasNext);
        model.addAttribute("hasPrevious", hasPrevious);
        model.addAttribute("listOfPages", filterResult.getListOfPages());
        model.addAttribute("criteria", criteriaParameter);


        return "cooperatives";

    }


}
