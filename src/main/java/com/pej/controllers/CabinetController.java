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
import com.pej.domains.Cabinet;
import com.pej.repository.CabinetRepository;
import com.pej.services.NotificationService;

@Controller
public class CabinetController {
    @Autowired
    private CabinetRepository cabinetRepository;
    @Autowired
    private NotificationService notifyService;

    @GetMapping("/pej/cabinets")
    String index(Model model, @ModelAttribute("objCabinet") Cabinet objCabinet) {
        List<Cabinet> cabinets = (List<Cabinet>) cabinetRepository.findAll();

        model.addAttribute("cabinets", cabinets);

        return "cabinets";
    }

    @GetMapping("/pej/cabinets/add")
    public String editCabinet(@ModelAttribute("objCabinet") Cabinet objCabinet, ModelMap model) {
        List<Cabinet> antennes = (List<Cabinet>) cabinetRepository.findAll();

        model.addAttribute("antennes", antennes);
        model.addAttribute("objCabinet", objCabinet);

        return "frmCabinet";
    }

    @GetMapping("/pej/cabinet/{id}")
    public String modifierCabinet(@PathVariable Integer id, ModelMap model) {
        Cabinet objCabinet = cabinetRepository.findOne(id);

        model.addAttribute("objCabinet", objCabinet);

        return "frmCabinet";
    }

    @PostMapping("/pej/cabinets")
    public String saveagents(@ModelAttribute(value = "objCabinet") Cabinet objCabinet, BindingResult result, Model model) {

        if (result.hasErrors()) {
            notifyService.addErrorMessage("ECHEC DE L'ENREGISTREMENT.");
        }
        else{
            cabinetRepository.save(objCabinet);
            notifyService.addInfoMessage("ENREGISTREMENT EFFECTUE AVEC SUCCESS.");
        }

        return "redirect:/pej/cabinets";
    }

}
