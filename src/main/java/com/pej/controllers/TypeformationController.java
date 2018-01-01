package com.pej.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import com.pej.domains.Typeformation;
import com.pej.repository.TypeformationRepository;
import com.pej.services.NotificationService;

@Controller
public class TypeformationController {
    @Autowired
    private TypeformationRepository typeformationRepository;
    @Autowired
    private NotificationService notifyService;

    @GetMapping("/pej/typeformations")
    String index(Model model) {
        List<Typeformation> typeformations = (List<Typeformation>) typeformationRepository.findAll();
        model.addAttribute("typeformations", typeformations);
        return "typeformations";
    }

    @GetMapping("/pej/typeformations/add")
    public String create(@ModelAttribute("objTypeformation") Typeformation objTypeformation, ModelMap model) {

        model.addAttribute("objTypeformation", objTypeformation);
        return "frmTypeformation";
    }

    @GetMapping("/pej/typeformation/{id}")
    public String edit(@PathVariable Integer id, ModelMap model) {

        Typeformation objTypeformation = typeformationRepository.findOne(id);

        model.addAttribute("objTypeformation", objTypeformation);
        return "frmTypeformation";
    }

    @PostMapping("/pej/typeformations")
    public String save(@ModelAttribute(value = "objTypeformation") Typeformation objTypeformation, BindingResult result, Model model) {

        if (result.hasErrors()) {
            notifyService.addErrorMessage("ECHEC DE L'ENREGISTREMENT.");
        }
        else{
            typeformationRepository.save(objTypeformation);
            notifyService.addInfoMessage("ENREGISTREMENT EFFECTUE AVEC SUCCESS.");
        }

        return "redirect:/pej/typeformations";
    }

}
