package com.pej.controllers;
import com.pej.services.EntrepriseService;
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
import com.pej.domains.*;
import com.pej.repository.*;
import com.pej.services.NotificationService;

import java.util.*;

@Controller
public class EntrepriseController {
    @Autowired private EntrepriseRepository entrepriseRepository;
    @Autowired private DepartementRepository departementRepository;
    @Autowired private CommuneRepository communeRepository;
    @Autowired private ArrondissementRepository arrondissementRepository;
    @Autowired private QuartierRepository quartierRepository;
    @Autowired private CandidatRepository candidatRepository;
    @Autowired private FormateurRepository formateurRepository;
    @Autowired private UsersRepository usersRepository;
    @Autowired private EntreprisesFormateursRepository entreprisesFormateursRepository;
    @Autowired private NotificationService notifyService;
    @Autowired private EntrepriseService entrepriseService;
    @Autowired private SuivieRepository suivieRepository;
    @Autowired private PeriodeRepository periodeRepository;

    private Entreprise objEntrepriseEdit;
    private EntrepriseFormateursForm objetEntrepriseFormateursForm;



    @GetMapping("/pej/entreprise/candidat/{idCandidat}")
    String candidatEntreprise(Model model, @PathVariable Integer idCandidat) {
       List<Entreprise> entreprises = entrepriseRepository.findByCandidatIdcandidat(idCandidat);

       if(entreprises == null || entreprises.isEmpty())
           return "redirect:/pej/entreprises/new/"+ idCandidat;
       return null;
    }

    @GetMapping("/pej/entreprises/new/{idCandidat}")
    public String editEntreprise(@ModelAttribute("objEntreprise") Entreprise objEntreprise, ModelMap model, @PathVariable Integer idCandidat){

        model.addAttribute("objEntreprise", objEntreprise);
        model.addAttribute("departements", departementRepository.findAll());
        model.addAttribute("communes", communeRepository.findAll());
        model.addAttribute("arrondissement", arrondissementRepository.findAll());
        model.addAttribute("quartiers", quartierRepository.findAll());
        model.addAttribute("candidat", candidatRepository.findOne(idCandidat));
        model.addAttribute("username", getUsername());
        model.addAttribute("formateurs", formateurRepository.findAll());

        return "frmEntreprise";
    }



    @GetMapping("/pej/entreprises")
    String index(Model model, @RequestParam(value = "idFormateur", required = false, defaultValue = "0") int idFormateur) {
        User principal = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Collection<GrantedAuthority> grantedAuthorities = principal.getAuthorities();
        Formateur formateur = null;

        List<String> roleNames = new ArrayList<>();
        List<Entreprise> entreprises = new ArrayList<>();

        for (GrantedAuthority grantedAuthority : grantedAuthorities) {
            roleNames.add(grantedAuthority.getAuthority().toString());
        }


        if (roleNames.size() == 1 && roleNames.contains("FORMATEUR")) {
            String username = principal.getUsername();
            formateur = formateurRepository.findOneByUsername(username);
            entreprises = (List<Entreprise>) entreprisesFormateursRepository.getEntreprisesDuFormateur(formateur.getIdformateur());
        }
        else if(idFormateur != 0){
            formateur = formateurRepository.findOne(idFormateur);
            entreprises = (List<Entreprise>) entreprisesFormateursRepository.getEntreprisesDuFormateur(formateur.getIdformateur());
        }
        else {
            entreprises = (List<Entreprise>) entrepriseRepository.findAll();
        }


        model.addAttribute("entreprises", entreprises);
        model.addAttribute("username", getUsername());
        model.addAttribute("type", "1");
        return "entreprises";
    }


    @GetMapping("/pej/entreprises/periodsuivi/{idEntreprise}")
    public String editPeriodSuivi(ModelMap model, @PathVariable Integer idEntreprise){

        User principal = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Collection<GrantedAuthority> grantedAuthorities = principal.getAuthorities();

        List<String> roleNames = new ArrayList<>();
        Entreprise entreprise = entrepriseRepository.findOne(idEntreprise);

        for (GrantedAuthority grantedAuthority : grantedAuthorities) {
            roleNames.add(grantedAuthority.getAuthority().toString());
        }


        if (roleNames.size() == 1 && roleNames.contains("FORMATEUR")) {
            String username = principal.getUsername();
            Formateur formateur = formateurRepository.findOneByUsername(username);
            Periode periode = periodeRepository.findByEntrepriseIdentrepriseAndFormateurIdformateur(idEntreprise, formateur.getIdformateur());

            if(periode == null) {
                periode = new Periode();
                periode.setDateDebut(new Date());
                periode.setDatefin(new Date());
                periode.setEntreprise(entreprise);
                periode.setFormateur(formateur);
            }
            model.addAttribute("username", getUsername());
            model.addAttribute("periodeSuivi", periode);

            return "frmPeriodSuivi";
        }
        else {
            List<Formateur> formateurs = entreprise.getFormateurs();
            List<Periode> periodes = new ArrayList<>();
            for (Formateur formateur : formateurs){
                Periode periode = periodeRepository.findByEntrepriseIdentrepriseAndFormateurIdformateur(idEntreprise, formateur.getIdformateur());
                if(periode != null) periodes.add(periode);
            }

            model.addAttribute("username", getUsername());
            model.addAttribute("periodeSuivis", periodes);

            return "listPeriodSuivi";
        }

    }

//    @GetMapping("/pej/entreprises/periodsuivi/{idEntreprise}")
//    public String editPeriodSuivi(ModelMap model, @PathVariable Integer idEntreprise){
//
//        User principal = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//        Collection<GrantedAuthority> grantedAuthorities = principal.getAuthorities();
//        Formateur formateur = null;
//
//        List<String> roleNames = new ArrayList<>();
//        Entreprise entreprise = entrepriseRepository.findOne(idEntreprise);
//
//        for (GrantedAuthority grantedAuthority : grantedAuthorities) {
//            roleNames.add(grantedAuthority.getAuthority().toString());
//        }
//
//
//        if (roleNames.size() == 1 && roleNames.contains("FORMATEUR")) {
//            String username = principal.getUsername();
//            formateur = formateurRepository.findOneByUsername(username);
//            Periode periode = periodeRepository.findByEntrepriseIdentrepriseAndFormateurIdformateur(idEntreprise, formateur.getIdformateur());
//
//            if(periode == null) {
//                periode = new Periode();
//                periode.setDateDebut(new Date());
//                periode.setDatefin(new Date());
//                periode.setEntreprise(entreprise);
//                periode.setFormateur(formateur);
//            }
//            model.addAttribute("username", getUsername());
//            model.addAttribute("periodeSuivi", periode);
//
//            return "frmPeriodSuivi";
//        }
//        else {
//            formateur = entreprise.getFormateurs().get(0);
//            Periode periode = periodeRepository.findByEntrepriseIdentrepriseAndFormateurIdformateur(idEntreprise, formateur.getIdformateur());
//
//            if(periode == null) {
//                periode = new Periode();
//                periode.setDateDebut(new Date());
//                periode.setDatefin(new Date());
//                periode.setEntreprise(entreprise);
//                periode.setFormateur(formateur);
//            }
//            model.addAttribute("username", getUsername());
//            model.addAttribute("periodeSuivi", periode);
//
//            return "frmPeriodSuivi";
//
//        }
//
//    }


    @PostMapping("/pej/entreprises/periodsuivi")
    public String savePeriodSuivi(@ModelAttribute(value="periodeSuivi")  Periode periode, BindingResult result,Model model){

        if (result.hasErrors()) {
            List<ObjectError> errors = result.getAllErrors();
            for (ObjectError error : errors) {
                System.out.println("error to create new period suivi " + error.toString());
            }
            return "redirect:/pej/entreprises";
        }

        System.out.println("period suivi formateur " + periode.getFormateur().getIdentite());

        periodeRepository.save(periode);

        return "redirect:/pej/entreprises";

    }

    @GetMapping("/pej/entreprises/calendrierformateur/{idEntreprise}")
    public String entrepriseCalendrier(ModelMap model, @PathVariable Integer idEntreprise){

        User principal = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Collection<GrantedAuthority> grantedAuthorities = principal.getAuthorities();


        List<String> roleNames = new ArrayList<>();
        Entreprise entreprise = entrepriseRepository.findOne(idEntreprise);

        for (GrantedAuthority grantedAuthority : grantedAuthorities) {
            roleNames.add(grantedAuthority.getAuthority().toString());
        }



        if (roleNames.size() == 1 && roleNames.contains("FORMATEUR")) {
            Formateur formateur = formateurRepository.findOneByUsername(getUsername());

            List<Suivie> suivies = suivieRepository.findByEntrepriseIdentrepriseAndFormateurIdformateurOrderByDateDesc(idEntreprise, formateur.getIdformateur());

            if(suivies == null || suivies.isEmpty()) suivies = new ArrayList<Suivie>();

            Suivie suivie = new Suivie();
            suivie.setEntreprise(entreprise);
            suivie.setFormateur(formateur);
            model.addAttribute("suivie", suivie);
            model.addAttribute("suivies", suivies);

            return "entreprises_calendrier";
        }
        else {
            List<Suivie> suivies = suivieRepository.findByEntrepriseIdentrepriseOrderByDateDesc(idEntreprise);
            model.addAttribute("suivies", suivies);

            return "list_entreprises_calendrier";
        }


    }


//    @GetMapping("/pej/entreprises/calendrierformateur/{idEntreprise}")
//    public String entrepriseCalendrier(ModelMap model, @PathVariable Integer idEntreprise){
//
//        User principal = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//        Collection<GrantedAuthority> grantedAuthorities = principal.getAuthorities();
//        Formateur formateur = formateurRepository.findOneByUsername(getUsername());
//
//        List<String> roleNames = new ArrayList<>();
//        Entreprise entreprise = entrepriseRepository.findOne(idEntreprise);
//
//        for (GrantedAuthority grantedAuthority : grantedAuthorities) {
//            roleNames.add(grantedAuthority.getAuthority().toString());
//        }
//
//        List<Suivie> suivies = suivieRepository.findByEntrepriseIdentrepriseOrderByDateDesc(idEntreprise);
//
//        if (roleNames.size() == 1 && roleNames.contains("FORMATEUR")) {
//            Suivie suivie = new Suivie();
//            suivie.setEntreprise(entreprise);
//            suivie.setFormateur(formateur);
//            suivie.setDate(new Date());
//            model.addAttribute("suivie", suivie);
//            model.addAttribute("formateur", "oui");
//        }
//        else {
//            formateur = entreprise.getFormateurs().get(0);
//
//            Suivie suivie = new Suivie();
//            suivie.setEntreprise(entreprise);
//            suivie.setFormateur(formateur);
//            suivie.setDate(new Date());
//            model.addAttribute("suivie", suivie);
//            model.addAttribute("formateur", "oui");
//
//            model.addAttribute("formateur", "non");
//        }
//
//        model.addAttribute("suivies", suivies);
//
//        return "entreprises_calendrier";
//
//    }



    @GetMapping("/pej/entreprise/{id}")
    public String editEntreprise(@PathVariable Integer id, ModelMap model){
        Entreprise entreprise=entrepriseRepository.findOne(id);


        model.addAttribute("objEntreprise", entreprise);
        model.addAttribute("departements", departementRepository.findAll());
        model.addAttribute("candidat", entreprise.getCandidat());
        model.addAttribute("username", getUsername());
        model.addAttribute("quartiers", quartierRepository.findAll());
        model.addAttribute("formateurs", formateurRepository.findAll());


        return "frmEntreprise";
    }


    @PostMapping("/pej/entreprises")
    public String saveentreprises(@ModelAttribute(value="objEntreprise")  Entreprise objEntreprise, BindingResult result,Model model) {

        if (result.hasErrors()) {
            List<ObjectError> errors = result.getAllErrors();
            for (ObjectError error : errors) {
                System.out.println("error to create new entreprise " + error.toString());
            }
            return "redirect:/pej/entreprises";
        }

        entrepriseService.saveEntreprise(objEntreprise);
        notifyService.addInfoMessage("Enregistrement effectuée avec succès.");

        return "redirect:/pej/entreprises";
    }


    @PostMapping("/pej/entreprises/calendrier/add")
    public String saveSuivie(@ModelAttribute(value="suivie")  Suivie suivie, BindingResult result, Model model) {

        if (result.hasErrors()) {
            List<ObjectError> errors = result.getAllErrors();
            for (ObjectError error : errors) {
                System.out.println("error to create new suivie " + error.toString());
            }
            return "redirect:/pej/entreprises";
        }

        //suivie.setDate(new Date());

        suivieRepository.save(suivie);
        notifyService.addInfoMessage("Suivie enregistree avec succès.");

        return "redirect:/pej/entreprises";
    }


    private String getUsername(){
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof UserDetails)
            return ((UserDetails)principal).getUsername();
        else
            return principal.toString();
    }

}
