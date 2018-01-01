package com.pej.controllers;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import com.pej.domains.*;
import com.pej.pojo.FilterResult;
import com.pej.repository.*;
import com.pej.services.CustomUserDetail;
import com.pej.services.FormationService;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@Controller
public class FormationController {
    Logger logger = LogManager.getLogger(FormationController.class);

    @Autowired
    private FormationRepository formationRepository;
    @Autowired
    private FormateurRepository formateurRepository;
    @Autowired
    private TypeformationRepository typeformationRepository;
    @Autowired
    private FormationService formationService;
    @Autowired
    private LotRepository lotRepository;
    @Autowired
    private CooperativeRepository cooperativeRepository;
    @Autowired
    private CabinetRepository cabinetRepository;


    @GetMapping("/pej/formations")
    String index(Model model, @RequestParam(value = "idformateur", required = false, defaultValue = "") String idformateur) {

        List<Cabinet> cabinets = (List<Cabinet>) cabinetRepository.findAll();
        List<Cooperative> cooperatives = (List<Cooperative>) cooperativeRepository.findAll();
        List<Lot> lots = (List<Lot>) lotRepository.findAll();
        List<Typeformation> typeformations = (List<Typeformation>) typeformationRepository.findAll();


        User principal = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Collection<GrantedAuthority> grantedAuthorities = principal.getAuthorities();
        Formateur formateur = null;

        List<String> roleNames = new ArrayList<>();
        List<Formation> formations = new ArrayList<>();

        for (GrantedAuthority grantedAuthority : grantedAuthorities) {
            roleNames.add(grantedAuthority.getAuthority().toString());
        }


        if (roleNames.size() == 1 && roleNames.contains("FORMATEUR")) {
            String username = principal.getUsername();
            formateur = formateurRepository.findOneByUsername(username);
            formations = (List<Formation>) formateur.getFormations();
        }
        else if(! idformateur.equals("")){
            formateur = formateurRepository.findOne(Integer.parseInt(idformateur));
            formations = (List<Formation>) formateur.getFormations();
        }
        else {
            formations = (List<Formation>) formationRepository.findAll();
        }

        model.addAttribute("formations", formations);
        model.addAttribute("total", formations.size());
        model.addAttribute("cabinets", cabinets);
        model.addAttribute("lots", lots);
        model.addAttribute("typeFormations", typeformations);
        model.addAttribute("cooperatives", cooperatives);

        return "formations";
    }

    @GetMapping("/pej/formations/add")
    public String create(@ModelAttribute("objFormation") Formation objFormation, ModelMap model) {
        List<Formateur> formateurs = (List<Formateur>) formateurRepository.findAll();
        List<Typeformation> typeformations = (List<Typeformation>) typeformationRepository.findAll();

        model.addAttribute("formateurs", formateurs);
        model.addAttribute("objFormation", objFormation);
        model.addAttribute("typeformations", typeformations);
        return "frmFormation";
    }


    @GetMapping("/pej/formations/add/{id}")
    public String edit(@PathVariable Integer id, ModelMap model) {
        List<Formateur> formateurs = (List<Formateur>) formateurRepository.findAll();
        List<Typeformation> typeformations = (List<Typeformation>) typeformationRepository.findAll();
        Formation objFormation = formationRepository.findOne(id);

        model.addAttribute("formateurs", formateurs);
        model.addAttribute("objFormation", objFormation);
        model.addAttribute("typeformations", typeformations);

        return "frmEditFormation";
    }

    @GetMapping("/pej/formations/rm/{id}")
    public String deleteFormation(@PathVariable Integer id) {
        formationService.deleteFormation(id);
        return "redirect:/pej/formations";
    }


    @PostMapping("/pej/formations/multiple/rm")
    public String deleteFormations(@RequestParam("table_records") List<String> table_records) {

        for(String id: table_records){
            formationService.deleteFormation(Integer.parseInt(id));
        }
        return "redirect:/pej/formations";
    }

    @PostMapping("/pej/formations")
    public String save(@ModelAttribute(value = "objFormation") Formation objFormation, BindingResult result) {

        if (result.hasErrors()) {
            List<ObjectError> errors = result.getAllErrors();
            for (ObjectError error : errors) {
                System.out.println("error to create new formation " + error.toString());
            }
            return "redirect:/pej/formations";
        }


        formationService.saveFormation(objFormation);

        return "redirect:/pej/formations";
    }


    @SuppressWarnings("unchecked")
    @PostMapping("/pej/formations/search")
    String searchContrat(HttpServletRequest request, Model model) {

        //FilterResult filterResult = filterService.filter(request, Candidat.class);

        String criteriaParameter = request.getParameter("criteria");

        System.out.println(" candidat filtre " + criteriaParameter);

        if(criteriaParameter.equals("[]")) return "redirect:/pej/formations";


        FilterResult filterResult = formationService.filter(request);

        List<Cabinet> cabinets = (List<Cabinet>) cabinetRepository.findAll();
        List<Cooperative> cooperatives = (List<Cooperative>) cooperativeRepository.findAll();
        List<Lot> lots = (List<Lot>) lotRepository.findAll();
        List<Typeformation> typeformations = (List<Typeformation>) typeformationRepository.findAll();



        User principal = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();


        String hasNext = "true";
        String hasPrevious = "true";

        if(filterResult.getCurrentPage() == filterResult.getNextPage()) hasNext = "false";
        if(filterResult.getCurrentPage() == filterResult.getPreviousPage()) hasPrevious = "false";



        model.addAttribute("formations", filterResult.getData());
        model.addAttribute("total", filterResult.getTotal());
        model.addAttribute("cabinets", cabinets);
        model.addAttribute("lots", lots);
        model.addAttribute("typeFormations", typeformations);
        model.addAttribute("cooperatives", cooperatives);
        model.addAttribute("username", principal.getUsername());
        model.addAttribute("nextPage", filterResult.getNextPage());
        model.addAttribute("currentPage", filterResult.getCurrentPage());
        model.addAttribute("previousPage", filterResult.getPreviousPage());
        model.addAttribute("hasNext", hasNext);
        model.addAttribute("hasPrevious", hasPrevious);
        model.addAttribute("listOfPages", filterResult.getListOfPages());
        model.addAttribute("criteria", criteriaParameter);


        return "formations";

    }

}
