package com.pej.controllers;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import com.pej.domains.Annees;
import com.pej.domains.Formateur;
import com.pej.repository.AnneesRepository;
import com.pej.repository.FormateurRepository;
import com.pej.services.NotificationService;
@Controller
public class AnneesController {
    @Autowired private AnneesRepository anneesRepository;
    @Autowired private NotificationService notifyService;
    @GetMapping("/pej/annees")
    String index(Model model,@ModelAttribute("ObjAnnees") Annees objAnnees) {
        System.out.println("Starting Index Ok");
        List<Annees> annees = (List<Annees>) anneesRepository.findAll();
        model.addAttribute("annees", annees);
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof UserDetails) {
            String username = ((UserDetails)principal).getUsername();
            model.addAttribute("username", username);
        } else {
            String username = principal.toString();
            model.addAttribute("username", username);
        }
        return "annees";
    }

    @GetMapping("/pej/annees/add")
    public String addAnnee(@ModelAttribute("ObjAnnees") Annees objAnnees, ModelMap model){
        model.addAttribute("ObjAnnees", objAnnees);
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof UserDetails) {
            String username = ((UserDetails)principal).getUsername();
            model.addAttribute("username", username);
        } else {
            String username = principal.toString();
            model.addAttribute("username", username);
        }
        return "frmAnnees";
    }
    @GetMapping("/pej/annees/{id}")
    public String editAnnee(@PathVariable Integer id, ModelMap model){
        Annees annes=anneesRepository.findOne(id);
        model.addAttribute("ObjAnnees", annes);
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof UserDetails) {
            String username = ((UserDetails)principal).getUsername();
            model.addAttribute("username", username);
        } else {
            String username = principal.toString();
            model.addAttribute("username", username);
        }
        return "frmAnnees";
    }
    @PostMapping("/pej/annees")
    public String saveannees(@ModelAttribute(value="ObjAnnees")  Annees objAnnees, BindingResult result,Model model) {
        System.out.println("Starting Save Ok");
        if (result.hasErrors()) {
            notifyService.addErrorMessage("Echec Enrégistrement.");
            return "frmAnnees";
        }
        if(objAnnees!=null)
            System.out.println("Nom Formateur: "+objAnnees.getName());
        else
            System.out.println("objAnnees est null: ");

        if(objAnnees.getIdannees()!=null && objAnnees.getIdannees().intValue() >0 ){
            Annees annees =anneesRepository.findOne(objAnnees.getIdannees());
            annees.setName(objAnnees.getName());
            anneesRepository.save(annees);
            return "redirect:/pej/annees";
        }
        anneesRepository.save(objAnnees);
        notifyService.addInfoMessage("Enrégistrement  effectuée avec succès.");
        return "redirect:/pej/annees";
    }
    @GetMapping("/pej/annees/delete/{id}")
    public String deleteAnnees(@PathVariable Integer id, ModelMap model){

        Annees annes=anneesRepository.findOne(id);
        anneesRepository.delete(annes);
        notifyService.addInfoMessage("Suppression  effectuée avec succès.");
        return "redirect:/pej/annees";
    }

}