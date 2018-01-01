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
import com.pej.domains.Materiel;
import com.pej.repository.MaterielRepository;
import com.pej.services.NotificationService;

import java.util.List;

/**
 * Created by D O N A T I E N on 26/12/2016.
 */
@Controller
public class MaterielController {
    @Autowired private MaterielRepository materielRepository;
    @Autowired private NotificationService notifyService;
    @GetMapping("/pej/materiels")
    String index(Model model,@ModelAttribute("ObjMateriel") Materiel objAnnees) {
        System.out.println("Starting Index Ok");
        List<Materiel> materiels = (List<Materiel>) materielRepository.findAll();
        model.addAttribute("materiels", materiels);
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof UserDetails) {
            String username = ((UserDetails)principal).getUsername();
            model.addAttribute("username", username);
        } else {
            String username = principal.toString();
            model.addAttribute("username", username);
        }
        return "materiels";
    }

    @GetMapping("/pej/materiels/add")
    public String addMateriel(@ModelAttribute("ObjMateriel") Materiel objAnnees, ModelMap model){
        model.addAttribute("ObjMateriel", objAnnees);
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof UserDetails) {
            String username = ((UserDetails)principal).getUsername();
            model.addAttribute("username", username);
        } else {
            String username = principal.toString();
            model.addAttribute("username", username);
        }
        return "frmMateriel";
    }
    @GetMapping("/pej/materiels/{id}")
    public String editMateriel(@PathVariable Integer id, ModelMap model){
        Materiel materiel=materielRepository.findOne(id);
        model.addAttribute("ObjMateriel", materiel);
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof UserDetails) {
            String username = ((UserDetails)principal).getUsername();
            model.addAttribute("username", username);
        } else {
            String username = principal.toString();
            model.addAttribute("username", username);
        }
        return "frmMateriel";
    }
    @PostMapping("/pej/materiels")
    public String saveannees(@ModelAttribute(value="ObjMateriel")  Materiel objMateriel, BindingResult result,Model model) {
        System.out.println("Starting Save Ok");
        if (result.hasErrors()) {
            notifyService.addErrorMessage("Echec Enrégistrement.");
            return "frmMateriel";
        }
        if(objMateriel!=null)
            System.out.println("Nom Matériel: "+objMateriel.getNommateriel());
        else
            System.out.println("objMateriel est null: ");

        if(objMateriel.getIdmateriel()!=null && objMateriel.getIdmateriel().intValue() >0 ){
            Materiel materiel =materielRepository.findOne(objMateriel.getIdmateriel());
            materiel.setNommateriel(objMateriel.getNommateriel());
            materielRepository.save(materiel);
            return "redirect:/pej/materiels";
        }
        materielRepository.save(objMateriel);
        notifyService.addInfoMessage("Enrégistrement  effectuée avec succès.");
        return "redirect:/pej/materiels";
    }
    @GetMapping("/pej/materiels/delete/{id}")
    public String deleteMateriels(@PathVariable Integer id, ModelMap model){

        Materiel materiel=materielRepository.findOne(id);
        System.out.println(materiel);
        materielRepository.delete(materiel);
        notifyService.addInfoMessage("Suppression  effectuée avec succès.");
        return "redirect:/pej/materiels";
    }

}