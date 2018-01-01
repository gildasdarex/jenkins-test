package com.pej.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import com.pej.domains.Departement;
import com.pej.domains.Quartier;
import com.pej.domains.Commune;
import com.pej.domains.Arrondissement;
import com.pej.repository.*;

import java.util.ArrayList;
import java.util.List;


@Controller
class HomeController  {
	@Autowired private DepartementRepository departementRepository;
	@Autowired private CommuneRepository communeRepository;
	@Autowired private ArrondissementRepository arrondissementRepository;
	@Autowired private QuartierRepository quartierRepository;
    


	@GetMapping("/pej/decoupement")
    String index(Model model,@ModelAttribute("objDepartement") Departement objDepartement) {
    	System.out.println("Starting Index Ok");
    	List<Departement> departements = (List<Departement>) departementRepository.findAll();
    	List<Commune> communes = (List<Commune>) communeRepository.findAll();
    	List<Arrondissement> aronndissements = (List<Arrondissement>) arrondissementRepository.findAll();
    	List<Quartier> quartiers = (List<Quartier>) quartierRepository.findAll();
    	model.addAttribute("departements", departements);
    	model.addAttribute("communes", communes);
    	model.addAttribute("arrondissements", aronndissements);
    	model.addAttribute("quartiers", quartiers);
    	//model.addAttribute("objDepartement", new Departement());
        return "home";
    }
	
	@GetMapping("/pej/departement")
	public String editDepartement(@ModelAttribute("objDepartement") Departement objDepartement){
		 return "departement";
	}
	
	@GetMapping("/pej/decoupement/arrondissement/add")
	public String addArrondissement(@ModelAttribute("objArrondissement") Arrondissement objArrondissement, ModelMap model){
		List<Departement> departements = (List<Departement>) departementRepository.findAll();
		model.addAttribute("departements", departements);
		 return "frmArrondissement";
	}
	@GetMapping("/pej/decoupement/quartier/add")
	public String addQuartier(@ModelAttribute("objQuartier") Quartier objQuartier, ModelMap model){
		List<Departement> departements = (List<Departement>) departementRepository.findAll();
		model.addAttribute("departements", departements);
		return "frmQuartier";
	}
	
	@GetMapping("/pej/commune")
	public String editCommune(@ModelAttribute("objCommune") Commune objCommune, ModelMap model){
		 List<Departement> departements = (List<Departement>) departementRepository.findAll();
		 model.addAttribute("departements", departements); 
		 model.addAttribute("selectedDepartement", ""); 
		 return "commune";
	}
	
	/*Récupérer la liste des commmune d'un département*/
	@RequestMapping(value = "/pej/departement/{id}/commune", method = RequestMethod.GET)
	public @ResponseBody ArrayList<Commune> findCommune(@PathVariable Integer id) {
		Departement departement=departementRepository.findOne(id);
		return new ArrayList<Commune>(departement.getCommunes());
	}
	
	/*Récupérer la liste des arrondissements d'une commune*/
	@RequestMapping(value = "/pej/commune/{id}/arrondissement", method = RequestMethod.GET)
	public @ResponseBody ArrayList<Arrondissement> findArrondissement(@PathVariable Integer id) {
		Commune commune=communeRepository.findOne(id);
		return new ArrayList<Arrondissement>(commune.getArrondissements());
	}
	
	/*Récupérer la liste des quartiers d'un arrondissement*/
	@RequestMapping(value = "/pej/arrondissement/{id}/quartier", method = RequestMethod.GET)
	public @ResponseBody ArrayList<Quartier> findQuartiers(@PathVariable Integer id) {
		Arrondissement arrondissement=arrondissementRepository.findOne(id);
		return new ArrayList<Quartier>(arrondissement.getQuartiers());
	}
	
	@GetMapping("/pej/arrondissement")
	public String editArrondissement(@ModelAttribute("objArrondissement") Arrondissement objArrondissement){
		 return "arrondissement";
	}

	@GetMapping("/pej/departement/{id}")
	public String updateDepartement(@PathVariable Integer id, ModelMap model){
		
		Departement departement=departementRepository.findOne(id);
		model.addAttribute("objDepartement", departement);
		 return "departement";
	}
	
	@GetMapping("/pej/commune/{id}")
	public String updateCommune(@PathVariable Integer id, ModelMap model){
		List<Departement> departements = (List<Departement>) departementRepository.findAll();
		model.addAttribute("departements", departements); 
		Commune commune=communeRepository.findOne(id);
		model.addAttribute("objCommune", commune);
		return "commune";
	}
	
	@GetMapping("/pej/arrondissement/{id}")
	public String updateArrondissement(@PathVariable Integer id, ModelMap model){
		Arrondissement arrondissement=arrondissementRepository.findOne(id);
		model.addAttribute("objArrondissement", arrondissement);
		 return "arrondissement";
	}
	
    @PostMapping("/pej/departement")
    public String savedepartement(@Valid @ModelAttribute(value="objDepartement")  Departement objDepartement, BindingResult result,Model model) {
    	System.out.println("Starting Save Ok");
        if (result.hasErrors()) {
            return "departement";
        }
        if(objDepartement!=null)
       System.out.println("Lib département: "+objDepartement.getCodedepartement());
        else
        	System.out.println("Objdépartement est null: ");
        
       if(objDepartement.getCodedepartement()!=null && objDepartement.getCodedepartement().intValue() >0 ){
    	   Departement departement=departementRepository.findOne(objDepartement.getCodedepartement().intValue());
    	   departement.setLibdeparteement(objDepartement.getLibdeparteement());
    	   departement.setDescription(objDepartement.getDescription());
    	   departementRepository.save(departement);
           return "redirect:/pej/decoupement";
       }
       departementRepository.save(objDepartement);
       return "redirect:/pej/decoupement";
    }


	@PostMapping("/pej/commune")
    public String savecommune(@Valid @ModelAttribute(value="objCommune")  Commune objCommune, BindingResult result,Model model) {
    	System.out.println("Starting Save Ok");
        if (result.hasErrors()) {
        	List<Departement> departements = (List<Departement>) departementRepository.findAll();
        	model.addAttribute("departements", departements);
            return "commune";
        }
      System.out.println("Département de la commune "+objCommune.getDepartement().getLibdeparteement());
        
       if(objCommune.getCodecommune()!=null && objCommune.getCodecommune().intValue()>0){
    	   Commune commune=communeRepository.findOne(objCommune.getCodecommune().intValue());
    	   commune.setLibcommune(objCommune.getLibcommune());
    	   commune.setDescription(objCommune.getDescription());
    	   commune.setDepartement(objCommune.getDepartement());
    	   communeRepository.save(commune);
           return "redirect:/pej/decoupement";
       }
       communeRepository.save(objCommune);
       return "redirect:/pej/decoupement";
    }
    
    @PostMapping("/pej/arrondissement")
    public String saveArrondissement(@Valid @ModelAttribute(value="objArrondissement")  Arrondissement objArrondissement, BindingResult result,Model model) {
    	System.out.println("Starting Save Ok");
        if (result.hasErrors()) {
        	List<Departement> departements = (List<Departement>) departementRepository.findAll();
        	model.addAttribute("departements", departements);
            return "frmArrondissement";
        }

        
       if(objArrondissement.getCodearrondissement()!=null && objArrondissement.getCodearrondissement().intValue()>0){
    	   Arrondissement arrondissement=arrondissementRepository.findOne(objArrondissement.getCodearrondissement().intValue());
    	   arrondissement.setCodearrondissement(objArrondissement.getCodearrondissement());
    	   arrondissement.setLibarrondissement(objArrondissement.getLibarrondissement());
    	   arrondissement.setCommune(objArrondissement.getCommune());
    	  
    	   arrondissementRepository.save(arrondissement);
           return "redirect:/pej/decoupement";
       }
       arrondissementRepository.save(objArrondissement);
       return "redirect:/pej/decoupement";
    }
    
    @PostMapping("/pej/quartier")
    public String savequartier(@Valid @ModelAttribute(value="objQuartier")  Quartier objQuartier, BindingResult result,Model model) {
    	System.out.println("Starting Save Ok");
        if (result.hasErrors()) {
        	List<Departement> departements = (List<Departement>) departementRepository.findAll();
        	model.addAttribute("departements", departements);
            return "frmQuartier";
        }
 
        
       if(objQuartier.getIdquartier()!=null && objQuartier.getIdquartier().intValue()>0){
    	   Quartier quartier=quartierRepository.findOne(objQuartier.getIdquartier().intValue());
    	   quartier.setLibquartier(objQuartier.getLibquartier());
    	   quartier.setDescription(objQuartier.getDescription());
    	   quartier.setArrondissement(objQuartier.getArrondissement());
    	   quartierRepository.save(quartier);
           return "redirect:/pej/decoupement";
       }
       quartierRepository.save(objQuartier);
       return "redirect:/pej/decoupement";
    }
    
}