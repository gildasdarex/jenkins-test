package com.pej.controllers;

import java.util.List;

import com.pej.services.LotService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import com.pej.domains.Cabinet;
import com.pej.domains.Commune;
import com.pej.domains.Cooperative;
import com.pej.domains.Lot;
import com.pej.repository.LotRepository;
import com.pej.repository.CabinetRepository;
import com.pej.repository.CommuneRepository;
import com.pej.repository.CooperativeRepository;
import com.pej.services.NotificationService;

@Controller
public class LotController {
    @Autowired
    private LotRepository lotRepository;
    @Autowired
    private CabinetRepository cabinetRepository;
    @Autowired
    private CooperativeRepository cooperativeRepository;
    @Autowired
    private CommuneRepository communeRepository;
    @Autowired
    private NotificationService notifyService;
    @Autowired
    private LotService lotService;

    @GetMapping("/pej/lots")
    String index(Model model, @ModelAttribute("objLot") Lot objLot) {
        List<Lot> lots = (List<Lot>) lotRepository.findAll();
        model.addAttribute("lots", lots);
        return "lots";
    }

    @GetMapping("/pej/lots/add")
    public String editLot(@ModelAttribute("objLot") Lot objLot, ModelMap model) {

        List<Cabinet> cabinets = (List<Cabinet>) cabinetRepository.findAll();
        List<Commune> communes = (List<Commune>) communeRepository.findAll();

        model.addAttribute("cabinets", cabinets);
        model.addAttribute("communes", communes);
        model.addAttribute("objLot", objLot);

        return "frmLot";
    }

    @GetMapping("/pej/lots/{id}")
    public String modifierLot(@PathVariable Integer id, ModelMap model) {

        List<Commune> communes = (List<Commune>) communeRepository.findAll();
        Lot objLot = lotRepository.findOne(id);
        List<Cabinet> cabinets = (List<Cabinet>) cabinetRepository.findAll();

        model.addAttribute("communes", communes);
        model.addAttribute("cabinets", cabinets);
        model.addAttribute("objLot", objLot);

        return "frmLot";
    }

    @PostMapping("/pej/lots")
    public String savelots(@ModelAttribute(value = "objLot") Lot objLot, BindingResult result) {
        if (result.hasErrors()) {
            notifyService.addErrorMessage("ECHEC DE L'ENREGISTREMENT.");
            return "frmLot";
        }

        lotRepository.save(objLot);
        notifyService.addInfoMessage("OPERATION EFFECTUEE AVEC SUCCESS.");
        return "redirect:/pej/lots";
    }

    @RequestMapping(value = "/pej/lots/cooperative/add/{id}", method = RequestMethod.GET)
    String index(Model model, @PathVariable Integer id) {
        List<Cooperative> cooperatives = (List<Cooperative>) cooperativeRepository.getCooperativeNotInLot();
        List<Cooperative> cooperativelots = (List<Cooperative>) cooperativeRepository.getInLotCooperative(id);
        Lot lot = lotRepository.findOne(id);

        model.addAttribute("cooperatives", cooperatives);
        model.addAttribute("lot", lot);
        model.addAttribute("cooperativelots", cooperativelots);

        return "lotcooperatives";
    }



    @RequestMapping(value = "/pej/lots/cooperative/add/{idcooperative}/{idlot}", method = RequestMethod.GET)
    String addOneCooperativeToLot(@PathVariable Integer idcooperative, @PathVariable Integer idlot) {
        lotService.addCooperativeToLot(idcooperative, idlot);

        return "redirect:/pej/lots/cooperative/add/" + idlot;
    }


    @PostMapping("/pej/lots/cooperative/multiple/add/{idlot}")
    String addMultipleCooperativeToLot(@PathVariable Integer idlot, @RequestParam("table_records") List<String> table_records) {

        for(String id : table_records){
            lotService.addCooperativeToLot(Integer.parseInt(id), idlot);
        }
        return "redirect:/pej/lots/cooperative/add/" + idlot;
    }


    @RequestMapping(value = "/pej/lots/cooperative/remove/{idcooperative}/{idlot}", method = RequestMethod.GET)
    String removeOneCooperativeToLot(@PathVariable Integer idcooperative, @PathVariable Integer idlot) {
        lotService.removeCooperativeToLot(idcooperative);

        return "redirect:/pej/lots/cooperative/add/" + idlot;
    }


    @PostMapping("/pej/lots/cooperative/multiple/remove/{idlot}")
    String removeMultipleCooperativeToLot(@PathVariable Integer idlot, @RequestParam("table_records") List<String> table_records) {

        for(String id : table_records){
            lotService.removeCooperativeToLot(Integer.parseInt(id));
        }
        return "redirect:/pej/lots/cooperative/add/" + idlot;
    }
}
