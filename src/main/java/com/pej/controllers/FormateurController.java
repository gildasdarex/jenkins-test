package com.pej.controllers;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.pej.domains.*;
import com.pej.pojo.FilterResult;
import com.pej.repository.*;
import com.pej.services.FilterService;
import com.pej.services.FormateurService;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;
import com.pej.services.NotificationService;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@Controller
public class FormateurController {
    Logger logger = LogManager.getLogger(FormateurController.class);

    @Autowired private FormateurRepository formateurRepository;
    @Autowired private CabinetRepository cabinetRepository;
    @Autowired private UsersRepository userRepository;
    @Autowired private FormateurService formateurService;
    @Autowired private NotificationService notifyService;
    @Autowired private FilterService filterService;
    @Autowired private CooperativeRepository cooperativeRepository;
    @Autowired private TypeformationRepository typeformationRepository;

    private Utilisateur usercourant;

    @Value("${tmp.formateurs.images}")
    String imageLocation;

    @GetMapping("/pej/formateurs")
    String index(Model model, @RequestParam(value = "idcabinet", required = false, defaultValue = "") String idcabinet) {
        List<Formateur> formateurs = new ArrayList<>();
        List<Cabinet> cabinets = (List<Cabinet>) cabinetRepository.findAll();
        List<Cooperative> cooperatives = (List<Cooperative>) cooperativeRepository.findAll();
        List<Typeformation> typeformations = (List<Typeformation>) typeformationRepository.findAll();
        try {
            if(idcabinet.equals("")){
                formateurs = (List<Formateur>) formateurRepository.findAll();
            }else{
                formateurs = formateurRepository.findByCabinetIdcabinet(Integer.parseInt(idcabinet));
            }
            User principal = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            if (principal != null)
                model.addAttribute("username", principal.getUsername());
        } catch (Exception ex) {
           logger.debug("error for /pej/formateurs request");
        }

        model.addAttribute("formateurs", formateurs);
        model.addAttribute("total", formateurs.size());
        model.addAttribute("cabinets", cabinets);
        model.addAttribute("cooperatives", cooperatives);
        model.addAttribute("typeFormations", typeformations);

        return "formateurs";
    }

    @GetMapping("/pej/formateurs/add")
    public String editFormateur(@ModelAttribute("objFormateur") Formateur objFormateur, ModelMap model) {
        List<Cabinet> cabinets = (List<Cabinet>) cabinetRepository.findAll();
        User principal = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if (principal != null)
            model.addAttribute("username", principal.getUsername());
        model.addAttribute("cabinets", cabinets);
        model.addAttribute("objFormateur", objFormateur);

        return "frmFormateur";
    }


    @GetMapping("/pej/delete/formateurs/{id}")
    public String deleteFormateur(@PathVariable Integer id) {
        formateurService.deleteFormateur(id);
        return "redirect:/pej/formateurs";
    }


    @PostMapping("/pej/delete/multiple/formateurs")
    public String deleteAllFormateurs(@RequestParam("table_records") List<String> table_records) {
        for(String id : table_records){
            formateurService.deleteFormateur(Integer.parseInt(id));
        }

        return "redirect:/pej/formateurs";
    }


    @GetMapping("/pej/formateurs/{id}")
    public String editFormateur(@PathVariable Integer id, ModelMap model) {
        Formateur formateur = new Formateur();
        Utilisateur user = new Utilisateur();

        try {
            formateur = formateurRepository.findOne(id);
            user = userRepository.findUserByfristlast(formateur.getNom(), formateur.getPrenom());

            User principal = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            if (principal != null)
                model.addAttribute("username", principal.getUsername());
        } catch (Exception ex) {
            logger.debug("error for /pej/formateurs/{id} request");
        }

        List<Cabinet> cabinets = (List<Cabinet>) cabinetRepository.findAll();
        model.addAttribute("cabinets", cabinets);
        model.addAttribute("objFormateur", formateur);
        model.addAttribute("objUtilisateur", user);

        usercourant = user;

        return "editformateurs";
    }


    @GetMapping("/pej/formateurs/image/{id}")
    public String uploadFile(@PathVariable Integer id, ModelMap model) {
        Formateur formateur = formateurRepository.findOne(id);
        Utilisateur user = new Utilisateur();


        FileBucket fileModel = new FileBucket();

        model.addAttribute("fileBucket", fileModel);
        model.addAttribute("formateur", formateur);

        return "frmImageFormateur";
    }


    @RequestMapping(value = "/pej/formateurs/image/upload", method = RequestMethod.POST)
    public String singleFileUpload(@Valid FileBucket fileBucket, HttpServletRequest request) throws IOException {

        String idformateur = request.getParameter("idformateur");
        Formateur formateur = formateurRepository.findOne(Integer.parseInt(idformateur));

        MultipartFile multipartFile = fileBucket.getFile();


        File convFile = new File(imageLocation + "/" + formateur.getImageName() + ".jpg");
        convFile.createNewFile();
        FileOutputStream fos = new FileOutputStream(convFile);
        fos.write(multipartFile.getBytes());
        fos.close();

        return "redirect:/pej/formateurs";

    }




    @PostMapping("/pej/formateurs")
    public String saveagents(@ModelAttribute(value = "objFormateur") Formateur objFormateur, BindingResult result) {

        boolean success = true;

        if (result.hasErrors()) {
            List<ObjectError> errors = result.getAllErrors();
            for (ObjectError error : errors) {
                logger.debug("error to create new formateur " + error.toString());
            }
            return "frmFormateur";
        }

        if (objFormateur.getIdformateur() != null && objFormateur.getIdformateur().intValue() > 0)
            success = formateurService.editFormateur(objFormateur);
        else
            success = formateurService.createFormateur(objFormateur);

        if(!success)
            notifyService.addErrorMessage("ECHEC DE L'ENREGSTREMENT. VERFIER S'IL N'EXISTE PAS D'ENREGISTREMENT AVEC LE MEME USERNAME OU EMAIL   ");

        return "redirect:/pej/formateurs";
    }


    @SuppressWarnings("unchecked")
    @PostMapping("/pej/formateurs/search")
    String searchContrat(HttpServletRequest request, Model model) {

        String criteriaParameter = request.getParameter("criteria");

        System.out.println(" formateur filtre " + criteriaParameter);

        if(criteriaParameter.equals("[]")) return "redirect:/pej/formateurs";



        FilterResult filterResult = formateurService.filter(request);
        List<Cabinet> cabinets = (List<Cabinet>) cabinetRepository.findAll();
        List<Cooperative> cooperatives = (List<Cooperative>) cooperativeRepository.findAll();
        List<Typeformation> typeformations = (List<Typeformation>) typeformationRepository.findAll();

        User principal = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();



        String hasNext = "true";
        String hasPrevious = "true";

        if(filterResult.getCurrentPage() == filterResult.getNextPage()) hasNext = "false";
        if(filterResult.getCurrentPage() == filterResult.getPreviousPage()) hasPrevious = "false";



        model.addAttribute("formateurs", filterResult.getData());
        model.addAttribute("total", filterResult.getTotal());
        model.addAttribute("cabinets", cabinets);
        model.addAttribute("cooperatives", cooperatives);
        model.addAttribute("typeFormations", typeformations);
        model.addAttribute("username", principal.getUsername());
        model.addAttribute("nextPage", filterResult.getNextPage());
        model.addAttribute("currentPage", filterResult.getCurrentPage());
        model.addAttribute("previousPage", filterResult.getPreviousPage());
        model.addAttribute("hasNext", hasNext);
        model.addAttribute("hasPrevious", hasPrevious);
        model.addAttribute("listOfPages", filterResult.getListOfPages());
        model.addAttribute("criteria", criteriaParameter);

        return "formateurs";

    }

}
