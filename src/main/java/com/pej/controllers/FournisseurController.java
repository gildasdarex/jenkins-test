package com.pej.controllers;

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
import com.pej.domains.Fournisseur;
import com.pej.domains.Materiel;
import com.pej.repository.FournisseurRepository;
import com.pej.repository.MaterielRepository;
import com.pej.services.NotificationService;

import java.util.List;

/**
 * Created by D O N A T I E N on 26/12/2016.
 */

@Controller
public class FournisseurController {
    @Autowired(required = true)
    private FournisseurRepository fournisseurRepository;
    @Autowired
    private NotificationService notifyService;

    @GetMapping("/pej/fournisseurs")
    String index(Model model, @ModelAttribute("ObjFournisseur") Fournisseur objFournisseur) {
        List<Fournisseur> fournisseurs = (List<Fournisseur>) fournisseurRepository.findAll();
        model.addAttribute("fournisseurs", fournisseurs);
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof UserDetails) {
            String username = ((UserDetails) principal).getUsername();
            model.addAttribute("username", username);
        } else {
            String username = principal.toString();
            model.addAttribute("username", username);
        }
        return "fournisseurs";
    }

    @GetMapping("/pej/fournisseurs/add")
    public String addFournisseur(@ModelAttribute("ObjFournisseur") Fournisseur objFournisseur, ModelMap model) {
        model.addAttribute("ObjFournisseur", objFournisseur);
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof UserDetails) {
            String username = ((UserDetails) principal).getUsername();
            model.addAttribute("username", username);
        } else {
            String username = principal.toString();
            model.addAttribute("username", username);
        }
        return "frmFournisseur";
    }

    @GetMapping("/pej/fournisseurs/{id}")
    public String editFournisseur(@PathVariable Integer id, ModelMap model) {
        Fournisseur fournisseur = fournisseurRepository.findOne(id);
        model.addAttribute("ObjFournisseur", fournisseur);
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof UserDetails) {
            String username = ((UserDetails) principal).getUsername();
            model.addAttribute("username", username);
        } else {
            String username = principal.toString();
            model.addAttribute("username", username);
        }
        return "frmFournisseur";
    }

    @PostMapping("/pej/fournisseurs")
    public String savefournisseurs(@ModelAttribute(value = "ObjFournisseur") Fournisseur objFournisseur, BindingResult result, Model model) {
        System.out.println("Starting Save Ok");
        if (result.hasErrors()) {
            notifyService.addErrorMessage("Echec Enrégistrement.");
            return "frmFournisseur";
        }
        if (objFournisseur != null) {

            System.out.println("Nom Fournisseur: " + objFournisseur);


        } else
            System.out.println("objMateriel est null: ");

        if (objFournisseur.getIdfournisseur() != null && objFournisseur.getIdfournisseur().intValue() > 0) {
            Fournisseur fournisseur = fournisseurRepository.findOne(objFournisseur.getIdfournisseur());
            fournisseur.setNomfournisseur(objFournisseur.getNomfournisseur());
            fournisseur.setContactfournisseur(objFournisseur.getContactfournisseur());
            fournisseurRepository.save(fournisseur);
            return "redirect:/pej/fournisseurs";
        }
        // System.out.println("objet fournisseur "+objFournisseur);

        fournisseurRepository.save(objFournisseur);
        notifyService.addInfoMessage("Enrégistrement  effectuée avec succès.");
        return "redirect:/pej/fournisseurs";
    }

    @GetMapping("/pej/fournisseurs/delete/{id}")
    public String deleteFournisseur(@PathVariable Integer id, ModelMap model) {

        Fournisseur fournisseur = fournisseurRepository.findOne(id);
        fournisseurRepository.delete(fournisseur);
        notifyService.addInfoMessage("Suppression  effectuée avec succès.");
        return "redirect:/pej/fournisseurs";
    }

}

