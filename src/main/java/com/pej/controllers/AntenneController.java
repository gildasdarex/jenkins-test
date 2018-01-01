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

import com.pej.domains.Antenne;
import com.pej.domains.Departement;
import com.pej.repository.AntenneRepository;
import com.pej.repository.DepartementRepository;
import com.pej.services.NotificationService;;

@Controller
public class AntenneController {
@Autowired private AntenneRepository antenneRepository;
@Autowired private DepartementRepository departementRepository;
@Autowired private NotificationService notifyService;	
	@GetMapping("/pej/antennes")
    String index(Model model,@ModelAttribute("objAntenne") Antenne objAntenne) {
    	System.out.println("Starting Index Ok");
    	List<Antenne> antennes = (List<Antenne>) antenneRepository.findAll();  	
    	model.addAttribute("antennes", antennes);
        return "antennes";
    }
	
	@GetMapping("/pej/antennes/add")
	public String editAtenne(@ModelAttribute("objAntenne") Antenne objAntenne, ModelMap model){
		List<Departement> departements = (List<Departement>) departementRepository.findAll();
		model.addAttribute("departements", departements);
		 model.addAttribute("objAntenne", objAntenne); 
		 return "frmAntenne";
	}
	@GetMapping("/pej/antennes/{id}")
	public String modifierAntenne(@PathVariable Integer id, ModelMap model){
		List<Departement> departements = (List<Departement>) departementRepository.findAll();
		Antenne objAntenne=antenneRepository.findOne(id);
		model.addAttribute("departements", departements);
		 model.addAttribute("objAntenne", objAntenne); 
		 return "frmAntenne";
	}


    @PostMapping("/pej/antennes")
    public String saveagents(@ModelAttribute(value="objAgent")  Antenne objAntenne, BindingResult result,Model model) {

		if (result.hasErrors()) {
        	notifyService.addErrorMessage("Echec enregistrement");
            return "frmAntenne";
        }

        
       if(objAntenne.getIdantenne()!=null && objAntenne.getIdantenne().intValue() >0 ){
    	   Antenne antenne =antenneRepository.findOne(objAntenne.getIdantenne());
    	   antenne.setLibantenne(objAntenne.getLibantenne());
    	   antenne.setResponsable(objAntenne.getResponsable());
    	   antenne.setDepartement(objAntenne.getDepartement());

    	   antenneRepository.save(antenne);
    	   notifyService.addInfoMessage("Modification effectué avec succès");
           return "redirect:/pej/antennes";
       }
       else{
	       antenneRepository.save(objAntenne);
	       notifyService.addInfoMessage("Enregistrement effectué avec succès");
	       return "redirect:/pej/antennes";
       }
    }

}
