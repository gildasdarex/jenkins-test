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

import com.pej.domains.Statutcandidat;
import com.pej.repository.StatutcandidatRepository;
import com.pej.services.NotificationService;

@Controller
public class StatutCandidatController {
	@Autowired private StatutcandidatRepository statutcandidatRepository;
	@Autowired private NotificationService notifyService;
	@GetMapping("/pej/statutcandidats")
    String index(Model model,@ModelAttribute("objStatutCandidat") Statutcandidat objStatutCandidat) {
    	System.out.println("Starting Index Ok");
    	List<Statutcandidat> statutcandidats = (List<Statutcandidat>) statutcandidatRepository.findAll();  	
    	model.addAttribute("statutcandidats", statutcandidats);
        return "statutcandidats";
    }
	
	@GetMapping("/pej/statutcandidats/add")
	public String editStatutcandidat(@ModelAttribute("objStatutcandidat") Statutcandidat objstatutcandidat, ModelMap model){
		 model.addAttribute("objStatutcandidat", objstatutcandidat); 
		 return "frmStatutcandidat";
	}
	@GetMapping("/pej/statutcandidats/{id}")
	public String editStatutcandidat(@PathVariable Integer id, ModelMap model){
		 Statutcandidat st=statutcandidatRepository.findOne(id);
		 model.addAttribute("objStatutcandidat", st); 
		 return "frmStatutcandidat";
	}
	
    @PostMapping("/pej/statutcandidats")
    public String savestatutcandidats(@ModelAttribute(value="objStatutcandidat")  Statutcandidat objStatutcandidat, BindingResult result,Model model) {
    	System.out.println("Starting Save Ok");
        if (result.hasErrors()) {
        	notifyService.addErrorMessage("Echec enregistrement.");
            return "frmStatutcandidat";
        }
        if(objStatutcandidat!=null)
       System.out.println("Nom statut: "+objStatutcandidat.getIntitule());
        else
        	System.out.println("objStatutcandidat est null: ");
        
       if(objStatutcandidat.getId()!=null && objStatutcandidat.getId().intValue() >0 ){
    	   Statutcandidat statutcandidat =statutcandidatRepository.findOne(objStatutcandidat.getId());
    	   statutcandidat.setIntitule(objStatutcandidat.getIntitule());
    	   statutcandidat.setBeneficiaireniveau(objStatutcandidat.getBeneficiaireniveau());
    	   
    	   statutcandidatRepository.save(statutcandidat);
    	   notifyService.addInfoMessage("Modificcation effectuée avec succès.");
           return "redirect:/pej/statutcandidats";
       }
       notifyService.addInfoMessage("Enregistrement effectuée avec succès.");
       statutcandidatRepository.save(objStatutcandidat);
       return "redirect:/pej/statutcandidats";
    }
}
