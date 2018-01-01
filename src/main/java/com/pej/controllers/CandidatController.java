package com.pej.controllers;

import com.github.dandelion.datatables.core.ajax.DataSet;
import com.github.dandelion.datatables.core.ajax.DatatablesCriterias;
import com.github.dandelion.datatables.core.ajax.DatatablesResponse;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.pej.domains.*;
import com.pej.pojo.FilterModel;
import com.pej.pojo.FilterResult;
import com.pej.pojo.OdkCandidat;
import com.pej.services.CandidatService;
import com.pej.services.FilterService;
import com.poiji.internal.Poiji;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.util.FileCopyUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
//import org.apache.camel.*;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import com.pej.filtres.FCandidat;
import com.pej.repository.*;
import com.pej.services.NotificationService;
import com.pej.utils.*;

import java.lang.reflect.Field;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.stream.IntStream;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.web.multipart.MultipartFile;


@Controller
public class CandidatController {
    @Autowired
    private DepartementRepository departementRepository;
    @Autowired
    private CommuneRepository communeRepository;
    @Autowired
    private ArrondissementRepository arrondissementRepository;
    @Autowired
    private QuartierRepository quartierRepository;
    @Autowired
    private CandidatRepository candidatRepository;
    @Autowired
    private AgentRepository agentRepository;
    @Autowired
    private StatutcandidatRepository statutcandidatRepository;
    @Autowired
    private DonRepository donRepository;
    @Autowired
    private NotificationService notifyService;
    @Autowired
    private CandidatJpaRepo candidatJpaRepo;
    @Autowired
    private ParametresRepository parametresRepository;
    @Autowired
    private ServletContext servletContext;
    @Autowired
    private DossierRepository dossierRepository;
    @Autowired
    private FournisseurRepository fournisseurRepository;
    @Autowired
    private MaterielRepository materielRepository;
    @Autowired
    private FicheFinancementRepository ficheFinancementRepository;
    @Autowired
    private org.dozer.Mapper mapper;
    @Autowired
    private CandidatService candidatService;
    @Autowired
    private FilterService filterService;
    @Autowired
    private LotRepository lotRepository;
    @Autowired
    private TypeformationRepository typeformationRepository;
    @Autowired
    private CooperativeRepository cooperativeRepository;


    Fichefinancement fiche = new Fichefinancement();

    @Value("${tmp.location}")
    String tempLocation;

    @Value("${tmp.candidats.images}")
    String imageLocation;

    @GetMapping("/pej/candidats")
    String index(Model model, @ModelAttribute("objDepartement") Departement objDepartement, @RequestParam(value = "page", required = false, defaultValue = "0") int page) {
        Pageable pageable =  new PageRequest( page, 100 );

        List<Candidat> candidats = (List<Candidat>) candidatRepository.findAllWithPAgination(pageable);
        List<String> arrondissements = (List<String>) candidatRepository.selectArrondissement();
        List<Commune> communes = (List<Commune>) communeRepository.findAll();
        List<Departement> departements = (List<Departement>) departementRepository.findAll();
        List<Statutcandidat> statuts = (List<Statutcandidat>) statutcandidatRepository.findAll();
        List<Lot> lots = (List<Lot>) lotRepository.findAll();
        List<Cooperative> cooperatives = (List<Cooperative>) cooperativeRepository.findAll();
        List<Typeformation> typeformations = (List<Typeformation>) typeformationRepository.findAll();


        User principal = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();


        int nextPage = candidatService.getNextPage(page);
        int previousPage = candidatService.getPreviousPage(page);

        String hasNext = "true";
        String hasPrevious = "true";

        if(page == nextPage) hasNext = "false";
        if(page == previousPage) hasPrevious = "false";


        long sizeOfData = candidatRepository.getNumberOfData();
        long numberOfIndex = sizeOfData/100;
        int size = ((Double)Math.ceil(numberOfIndex)).intValue();

        int[] listOfPages = IntStream.range(1, size).toArray();






        model.addAttribute("candidats", candidats);
        model.addAttribute("total", candidatRepository.getTotal());
        model.addAttribute("communes", communes);
        model.addAttribute("departements", departements);
        model.addAttribute("statuts", statuts);
        model.addAttribute("username", principal.getUsername());
        model.addAttribute("nextPage", nextPage);
        model.addAttribute("previousPage", previousPage);
        model.addAttribute("hasNext", hasNext);
        model.addAttribute("hasPrevious", hasPrevious);
        model.addAttribute("listOfPages", listOfPages);
        model.addAttribute("arrondissements", arrondissements);

        model.addAttribute("lots", lots);
        model.addAttribute("typeFormations", typeformations);
        model.addAttribute("cooperatives", cooperatives);


        return "candidats";
    }

    @GetMapping("/pej/candidats/image/{id}")
    public String uploadFile(@PathVariable Integer id, ModelMap model) {
        Candidat candidat = candidatRepository.findOne(id);
        Utilisateur user = new Utilisateur();


        FileBucket fileModel = new FileBucket();

        model.addAttribute("fileBucket", fileModel);
        model.addAttribute("candidat", candidat);

        return "frmImageCandidat";
    }


    @RequestMapping(value = "/pej/candidats/image/upload", method = RequestMethod.POST)
    public String singleFileUpload(@Valid FileBucket fileBucket, HttpServletRequest request) throws IOException {

        String idcandidat = request.getParameter("idcandidat");
        Candidat candidat = candidatRepository.findOne(Integer.parseInt(idcandidat));

        MultipartFile multipartFile = fileBucket.getFile();


        File convFile = new File(imageLocation + "/" + candidat.getNumerocandidat() + ".jpg");
        convFile.createNewFile();
        FileOutputStream fos = new FileOutputStream(convFile);
        fos.write(multipartFile.getBytes());
        fos.close();

        return "redirect:/pej/candidats";

    }


    @RequestMapping(value = "/pej/candidats/table")
    @ResponseBody
    public DatatablesResponse<Candidat> data(HttpServletRequest request){
        List<Candidat> candidats = (List<Candidat>) candidatRepository.findAll();

        DatatablesCriterias criterias = DatatablesCriterias.getFromRequest(request);
        DataSet<Candidat> dataSet = new DataSet<>(candidats, (long)candidats.size(), (long)candidats.size());
        return DatatablesResponse.build(dataSet, criterias);
    }

    /*Récupérer la liste des candidats au format json*/
    @RequestMapping(value = "/pej/candidatjson", method = RequestMethod.GET)
    public
    @ResponseBody
    List<Candidat> findCandidatJson() throws IOException {
        List<Candidat> candidats = (List<Candidat>) candidatRepository.findAll();
        Candidats listCnd = new Candidats(candidats);
        JSONUtils.createAndWriteToFile("C:/FFOutput/candidats.json", listCnd, Candidats.class);
        return candidats;
    }

    @RequestMapping(value = "/pej/candidats/add", method = RequestMethod.GET)
    String addCandidat(@ModelAttribute("objCandidat") Candidat objCandidat, ModelMap model) {
        List<Departement> departements = (List<Departement>) departementRepository.findAll();
        List<Commune> communes = (List<Commune>) communeRepository.findAll();
        List<Arrondissement> arrondissements = (List<Arrondissement>) arrondissementRepository.findAll();
        List<Quartier> quartiers = (List<Quartier>) quartierRepository.findAll();

        model.addAttribute("departements", departements);
        model.addAttribute("communes", communes);
        model.addAttribute("arrondissements", arrondissements);
        model.addAttribute("quartiers", quartiers);

        return "frmCandidat";
    }


    @RequestMapping(value = "/pej/candidats/commune/{id}", method = RequestMethod.GET)
    public
    @ResponseBody
    ArrayList<Commune> findCommune(@PathVariable Integer id) {
        Departement departement = departementRepository.findOne(id);
        return new ArrayList<Commune>(departement.getCommunes());
    }

    /*Récupérer la liste des arrondissement d'une commune*/
    @RequestMapping(value = "/pej/candidats/arrondissement/{id}", method = RequestMethod.GET)
    public
    @ResponseBody
    ArrayList<Arrondissement> findArrondissement(@PathVariable Integer id) {
        Commune commune = communeRepository.findOne(id);
        return new ArrayList<Arrondissement>(commune.getArrondissements());
    }

    /*Récupérer la liste des quartier  d'un arrondissement*/
    @RequestMapping(value = "/pej/candidats/quartier/{id}", method = RequestMethod.GET)
    public
    @ResponseBody
    ArrayList<Quartier> findQuartier(@PathVariable Integer id) {
        Arrondissement arrondissement = arrondissementRepository.findOne(id);
        return new ArrayList<Quartier>(arrondissement.getQuartiers());
    }

    /*Récupérer la liste des quartier  d'un arrondissement*/
    @RequestMapping(value = "/pej/candidats/agent/{id}", method = RequestMethod.GET)
    public
    @ResponseBody
    Agent findAgent(@PathVariable String id) {
        Agent agent = agentRepository.getAgent(id);
        return agent;
    }

    @PostMapping("/pej/candidats")
    public String savecandidat(@Valid @ModelAttribute(value = "objCandidat")
                                       Candidat objCandidat,
                               BindingResult result, Model model) {


        Agent agent = agentRepository.getAgent(objCandidat.getNumeroagent());
//        if (objCandidat.getIdcandidat() != null && objCandidat.getIdcandidat().intValue() > 0) {
//            try {
//                Candidat candidat = candidatRepository.findOne(objCandidat.getIdcandidat());
//                candidat.setAgent(agent);
//                candidat.setNom(objCandidat.getNom());
//                candidat.setPrenom(objCandidat.getPrenom());
//                candidat.setDatenaissance(objCandidat.getDatenaissance());
//                candidat.setDocidentite(objCandidat.getDocidentite());
//                candidat.setNiveau(candidat.getNiveau());
//                candidat.setDiplome(objCandidat.getDiplome());
//                candidat.setCodearrondissement(objCandidat.getCodearrondissement());
//                candidat.setRefdocidentite(objCandidat.getRefdocidentite());
//                candidat.setTelprincipal(objCandidat.getTelprincipal());
//                candidat.setTelalternatif(objCandidat.getTelalternatif());
//                candidat.setAge(objCandidat.getAge());
//                candidat.setSexe(objCandidat.getSexe());
//                candidat.setSituationmatrimoniale(objCandidat.getSituationmatrimoniale());
//                candidat.setParentechefmenage(objCandidat.getParentechefmenage());
//                candidat.setNbpersonnemenage(objCandidat.getNbpersonnemenage());
//                candidat.setMenagebeneficiaire(objCandidat.getMenagebeneficiaire());
//                candidat.setScolarise(objCandidat.getScolarise());
//                candidat.setDernierniveauetude(objCandidat.getDernierniveauetude());
//                candidat.setLecture(objCandidat.getLecture());
//                candidat.setQualificationpersonelle(objCandidat.getQualificationpersonelle());
//                candidat.setWorklastmonth(objCandidat.getWorklastmonth());
//                candidat.setRecherchetravail(objCandidat.getRecherchetravail());
//                candidat.setMotifnonrecherche(objCandidat.getMotifnonrecherche());
//                candidat.setActiviteactuelle(objCandidat.getActiviteactuelle());
//                candidat.setAsoncompte(objCandidat.getAsoncompte());
//
//                candidat.setNbmoistravail(objCandidat.getNbmoistravail());
//                candidat.setNbjoursmoyen(objCandidat.getNbjoursmoyen());
//                candidat.setNbheuremoyen(objCandidat.getNbheuremoyen());
//                candidat.setRevenumoyen(objCandidat.getRevenumoyen());
//                candidat.setDomainesouhait(objCandidat.getDomainesouhait());
//                candidat.setTravailgroupe(objCandidat.getTravailgroupe());
//                candidat.setNumcarteagratter(objCandidat.getNumcarteagratter());
//                candidat.setQuartier(objCandidat.getQuartier());
//                candidat.setCommune(objCandidat.getCommune());
//                candidat.setDepartement(objCandidat.getDepartement());
//                candidat.setDateenregistrement(objCandidat.getDateenregistrement());
//                candidat.setNumeroagent(objCandidat.getNumeroagent());
//                candidat.setNumerofiche(objCandidat.getNumerofiche());
//
//                candidatRepository.save(objCandidat);
//                notifyService.addInfoMessage("Candidat modififé avec succès.");
//            } catch (Exception e) {
//                notifyService.addErrorMessage("Echec lors de la modification du candidat. " + e.getMessage());
//            }
//        }
        Statutcandidat st = statutcandidatRepository.findOne(1);
        if (st == null) {
            notifyService.addErrorMessage("Aucun statut par défaut configuré pour les candidats");
            return "";
        }
        objCandidat.setStatutcandidat(st);
        objCandidat.setAgent(agent);
        System.out.print("Agent récupéré: " + agent.getNom());
        System.out.print("Saving candidat: " + objCandidat.toString());

        try {
            candidatRepository.save(objCandidat);
            notifyService.addInfoMessage("Candidat enregistré avec succès.");
        } catch (Exception e) {
            notifyService.addErrorMessage("Echec lors de l'enregistrement du candidat. " + e.getMessage());
        }
        return "redirect:/pej/candidats";
    }

    /*Changer de statut pour un candidat en cliquant sur le bouton valider*/
    @RequestMapping(value = "/pej/candidats/valider/{id}", method = RequestMethod.GET)
    public
    @ResponseBody
    Candidat upgradeStatut(@PathVariable Integer id) {
        Candidat candidat = candidatRepository.findOne(id);
        Statutcandidat statut = statutcandidatRepository.findOne(candidat.getStatutcandidat().getId() + 1);
        if (statut != null) {
            candidat.setStatutcandidat(statut);
            candidatRepository.save(candidat);
        }

        return candidat;
    }


    /*Upgrade en max des candidats*/
    @RequestMapping(value = "/pej/candidats/validermax/{id}", method = RequestMethod.GET)
    public String upgradeStatuts(@PathVariable String id) {
        String[] ids = id.split("@");
        List<Candidat> candidatToUpgrade = new ArrayList<Candidat>();
        for (int i = 0; i < ids.length; i++) {
            Candidat candidat = candidatRepository.findOne(Integer.parseInt(ids[i]));
            if (candidat == null) continue;
            Statutcandidat statut = statutcandidatRepository.findOne(candidat.getStatutcandidat().getId() + 1);
            if (statut != null) {
                candidat.setStatutcandidat(statut);
                candidatRepository.save(candidat);
                candidatToUpgrade.add(candidat);
            }
        }
        //return candidatToUpgrade;
        return "redirect:/pej/candidats";
    }

    /*Récupérer la page d'accord de don à un candidat*/
    @RequestMapping(value = "/pej/candidats/don/{id}", method = RequestMethod.GET)
    public String getDonPage(@PathVariable Integer id, ModelMap model) {
        Candidat candidat = candidatRepository.findOne(id);
        List dons = (List<Don>) donRepository.getDonByCandidat(id);

        Don objDon = new Don();
        objDon.setCandidat(candidat);

        model.addAttribute("dons", dons);
        model.addAttribute("objDon", objDon);
        return "frmDon";
    }

    /*Enregistrer un don pour un candidat*/
    @PostMapping("/pej/candidats/don")
    public String saveDon(@Valid @ModelAttribute(value = "objDon") Don objDon, BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "redirect:/pej/candidats";
        }

        Candidat candidat = candidatRepository.findOne(objDon.getCandidat().getIdcandidat());
        if (candidat != null) {
            objDon.setCandidat(candidat);
            donRepository.save(objDon);
        }

        return "redirect:/pej/candidats";
    }

    @GetMapping("/pej/candidats/{id}")
    public String updateCandidat(@PathVariable Integer id, ModelMap model) {
        List<Departement> departements = (List<Departement>) departementRepository.findAll();
        Candidat candidat = candidatRepository.findOne(id);

        model.addAttribute("departements", departements);
        model.addAttribute("objCandidat", candidat);


        return "frmCandidat";
    }


    @GetMapping("/pej/candidats/upload")
    public String uploadFile(ModelMap model) {
        FileBucket fileModel = new FileBucket();

        model.addAttribute("fileBucket", fileModel);

        return "frmUploadCandidat";
    }


    @RequestMapping(value = "/singleUpload", method = RequestMethod.GET)
    public String getSingleUploadPage(ModelMap model) {
        FileBucket fileModel = new FileBucket();

        model.addAttribute("fileBucket", fileModel);

        return "singleFileUploader";
    }

    @RequestMapping(value = "/pej/candidats/upload", method = RequestMethod.POST)
    public String singleFileUpload(@Valid FileBucket fileBucket, BindingResult result, ModelMap model) throws IOException {

        MultipartFile multipartFile = fileBucket.getFile();

        File convFile = new File(tempLocation + "/" + multipartFile.getOriginalFilename());
        convFile.createNewFile();
        FileOutputStream fos = new FileOutputStream(convFile);
        fos.write(multipartFile.getBytes());
        fos.close();
        List<OdkCandidat> odkCandidats = Poiji.fromExcel(convFile, OdkCandidat.class);
        candidatService.saveFromFile(odkCandidats);
        return "redirect:/pej/candidats";

    }

    @GetMapping("/pej/candidats/tirage")
    String tirage(Model model, @ModelAttribute("objDepartement") Departement objDepartement) {
        List<Candidat> candidats = (List<Candidat>) candidatRepository.getEligibleCandidat(2);
        List<Commune> communes = (List<Commune>) communeRepository.findAll();

        Tirage objTirage = new Tirage();

        model.addAttribute("objTirage", objTirage);
        model.addAttribute("communes", communes);
        model.addAttribute("candidats", candidats);

        return "tirages";
    }


    @PostMapping("/pej/candidats/tirage")
    String tirer(Model model, @ModelAttribute("objTirage") Tirage objTirage) {
        Sort.Direction ordre = (objTirage.getOrdre().equalsIgnoreCase("Croissant")) ? Sort.Direction.ASC : Sort.Direction.DESC;
        Pageable request = new PageRequest(0, objTirage.getNombre(), ordre, "numcarteagratter");

        List<Candidat> candidats = (List<Candidat>) candidatJpaRepo.getEligibleCandidat(request, objTirage.getCommune().getCodecommune()).getContent();

        List<Commune> communes = (List<Commune>) communeRepository.findAll();

        Statutcandidat statutcandidat = statutcandidatRepository.findOne(3);
        for (int i = 0; i < candidats.size(); i++) {
            candidats.get(i).setStatutcandidat(statutcandidat);
            candidatRepository.save(candidats.get(i));
        }

        model.addAttribute("objTirage", objTirage);
        model.addAttribute("communes", communes);
        model.addAttribute("candidats", candidats);


        return "tirages";
    }

    private void saveImage(String filename, MultipartFile image)
            throws RuntimeException, IOException {
        try {
            File file = new File(servletContext.getRealPath("/") + "/"
                    + filename);

            FileUtils.writeByteArrayToFile(file, image.getBytes());
        } catch (IOException e) {
            throw e;
        }
    }

    @GetMapping("/pej/candidats/picture/{id}")
    public String uploadPicture(@PathVariable Integer id, ModelMap model) {
        FileBucket fileModel = new FileBucket();
        fileModel.setId(id);

        model.addAttribute("fileBucket", fileModel);

        return "frmUploadPicture";
    }


    @RequestMapping(value = "/pej/candidats/picture", method = RequestMethod.POST)
    public String uploadPicture(@Valid FileBucket fileBucket, BindingResult result, ModelMap model) throws IOException {
        Parametres parametres = parametresRepository.findOne(1);
        System.out.println("servletContext.getContextPath() " + servletContext.getRealPath("/"));
        System.out.println("Fetching file");


        try {
            //MultipartFile multipartFile = fileBucket.getFile();
            UUID uuid = UUID.randomUUID();
            String fileName = uuid.toString();
            fileName = fileName + "." + FilenameUtils.getExtension(fileBucket.getFile().getOriginalFilename());
            byte[] imageByteArray = new byte[fileBucket.getFile().getBytes().length];

            String fileLocation = new File("src\\main\\resources\\static\\images").getAbsolutePath() + "\\" + fileName;
            FileOutputStream fos = new FileOutputStream(fileLocation);
            fos.write(fileBucket.getFile().getBytes());
            fos.close();
            System.out.println("servletContext.getContext " + servletContext.getContext("/"));

            //String fileLocation = new File("static\\images").getAbsolutePath() + "\\" + fileName;
            //System.out.println("fileLocation "+fileLocation);
            //FileCopyUtils.copy(fileBucket.getFile().getBytes(), new File(fileLocation));
            Candidat candidat = candidatRepository.findOne(fileBucket.getId());
            candidat.setPhotolink(fileName);
            candidatRepository.save(candidat);
            notifyService.addSucceesgMessage("Photo enregistrée avec succès.");
            System.out.println("End copy file");

        } catch (Exception e) {
            notifyService.addErrorMessage("Une erreur est survenue lors du chargement de la photo.");
            System.out.println("Erreur " + e.getMessage());
            // TODO: handle exception
        }


        return "redirect:/pej/candidats";

    }

    @RequestMapping(value = "/pej/candidats/eligible", method = RequestMethod.GET)
    private String getEligibles() {
        List<Candidat> candidatToUpgrade = (List<Candidat>) candidatRepository.getEligibleCandidat();
        Statutcandidat statut = statutcandidatRepository.findOne(2);
        for (int i = 0; i < candidatToUpgrade.size(); i++) {
            candidatToUpgrade.get(i).setStatutcandidat(statut);
            ;
            candidatRepository.save(candidatToUpgrade.get(i));
        }
        notifyService.addSucceesgMessage("Candidats éligibles sélectionnés.");
        return "redirect:/pej/candidats";
    }

    @GetMapping("/pej/candidats/dossier/{id}")
    public String dossier(@PathVariable Integer id, ModelMap model) {
        Candidat candidat = candidatRepository.findOne(id);
        FileBucket fileModel = new FileBucket();
        fileModel.setId(id);
        model.addAttribute("candidat", candidat);
        model.addAttribute("fileBucket", fileModel);
        List<Dossiers> monDossier = (List<Dossiers>) dossierRepository.getDossierCandidat(id);
        model.addAttribute("dossiers", monDossier);
        return "frmUploadDossier";
    }


    @RequestMapping(value = "/pej/candidats/dossier/", method = RequestMethod.POST)
    public String uploadDossier(@Valid FileBucket fileBucket, BindingResult result, ModelMap model) throws IOException {
        Parametres parametres = parametresRepository.findOne(1);
        System.out.println("servletContext.getContextPath() " + servletContext.getRealPath("/"));
        System.out.println("Fetching file");

        //MultipartFile multipartFile = fileBucket.getFile();
        UUID uuid = UUID.randomUUID();
        String fileName = uuid.toString();
        fileName = fileName + "." + FilenameUtils.getExtension(fileBucket.getFile().getOriginalFilename());
        byte[] imageByteArray = new byte[999999];


        //String fileLocation = new File("static\\images").getAbsolutePath() + "\\" + fileName;
        String fileLocation = servletContext.getRealPath("/") + fileName;

			 /*FileOutputStream fos = new FileOutputStream(fileLocation);
             fos.write(imageByteArray);
			 fos.close();*/


        //String fileLocation = new File("static\\images").getAbsolutePath() + "\\" + fileName;
        System.out.println("fileLocation " + fileLocation);
        FileCopyUtils.copy(fileBucket.getFile().getBytes(), new File(fileLocation));
        Candidat candidat = candidatRepository.findOne(fileBucket.getId());

        Dossiers dossier = new Dossiers();
        dossier.setCandidat(candidat);
        dossier.setNature(fileBucket.getNature());
        dossier.setLink(fileName);
        dossierRepository.save(dossier);

        notifyService.addSucceesgMessage("Fichier enregistrée avec succès.");
        System.out.println("End copy file");
        return "redirect:/pej/candidats/dossier/" + candidat.getIdcandidat();

    }

    @GetMapping("/pej/candidats/materiels/{id}")
    public String candidatMateriels(@PathVariable Integer id, ModelMap model) {
        List<Fichefinancement> fichefinancements = (List<Fichefinancement>) ficheFinancementRepository.getFicheFinancement(id);
        model.addAttribute("fichefinancements", fichefinancements);
        model.addAttribute("idcandidat", id);
        return "fichemateriels";
    }

    @GetMapping("/pej/candidats/materiels/edit/{id}/{slug}")
    public String editFicheFinancement(@PathVariable Integer id, @PathVariable Integer slug, ModelMap model) {
        fiche = ficheFinancementRepository.findOne(slug);
        List<Fournisseur> fournisseurs = (List<Fournisseur>) fournisseurRepository.findAll();
        List<Materiel> materiels = (List<Materiel>) materielRepository.findAll();
        Candidat candidat = candidatRepository.findOne(id);
        model.addAttribute("fournisseurs", fournisseurs);
        model.addAttribute("materiels", materiels);
        model.addAttribute("candidats", candidat);
        model.addAttribute("objFichefinancement", fiche);
        return "frmFicheFinancement";
    }

    @GetMapping("/pej/candidats/materiels/delete/{id}/{slug}")
    public String deleteFicheFinancement(@PathVariable Integer id, @PathVariable Integer slug, ModelMap model) {
        Fichefinancement fiche = ficheFinancementRepository.findOne(slug);
        ficheFinancementRepository.delete(fiche);
        notifyService.addInfoMessage("Suppression  effectuée avec succès.");
        return "redirect:/pej/candidats/materiels/{id}";
    }

    @GetMapping("/pej/candidats/materiels/add/{id}")
    public String addcandidatMateriels(@PathVariable Integer id, @ModelAttribute("objFichefinancement") Fichefinancement objFicheFinancement, ModelMap model) {
        List<Fournisseur> fournisseurs = (List<Fournisseur>) fournisseurRepository.findAll();
        List<Materiel> materiels = (List<Materiel>) materielRepository.findAll();
        Candidat candidat = candidatRepository.findOne(id);
        model.addAttribute("fournisseurs", fournisseurs);
        model.addAttribute("materiels", materiels);
        model.addAttribute("candidats", candidat);
        return "frmFicheFinancement";
    }

    @GetMapping("/pej/candidats/materiels/add/action/{id}")
    public String actioncandidatMateriels(@PathVariable Integer id, @ModelAttribute("objFichefinancement") Fichefinancement objFicheFinancement, ModelMap model) {
        if (fiche.getIdfichefinancement() != null && fiche.getIdfichefinancement().intValue() > 0) {
            Fichefinancement objet = ficheFinancementRepository.findOne(fiche.getIdfichefinancement());
            objet.setCandidat(objFicheFinancement.getCandidat());
            objet.setMateriel(objFicheFinancement.getMateriel());
            objet.setFournisseur(objFicheFinancement.getFournisseur());
            objet.setPrixunitaire(objFicheFinancement.getPrixunitaire());
            objet.setQuantite(objFicheFinancement.getQuantite());
            objFicheFinancement.setCandidat(candidatRepository.findOne(id));
            objet.setCandidat(objFicheFinancement.getCandidat());
            ficheFinancementRepository.save(objet);
            notifyService.addInfoMessage("Enrégistrement  effectuée avec succès.");
            fiche = new Fichefinancement();
            return "redirect:/pej/candidats/materiels/{id}";
        }
        objFicheFinancement.setCandidat(candidatRepository.findOne(id));
        ficheFinancementRepository.save(objFicheFinancement);
        notifyService.addInfoMessage("Enrégistrement  effectuée avec succès.");
        return "redirect:/pej/candidats/materiels/add/{id}";
    }

    @GetMapping("/pej/candidats/profil/{id}")
    public String showprofile(@PathVariable Integer id, ModelMap model) {
        Candidat candidat = candidatRepository.findOne(id);
        if (candidat == null) {
            notifyService.addErrorMessage("Ce candidat n'existe pas.");
            return "redirect:/pej/candidats";
        }
        model.addAttribute("candidat", candidat);
        return "contact";
    }

    @SuppressWarnings("unchecked")
    @PostMapping("/pej/candidats/search")
    String searchContrat(HttpServletRequest request, Model model) {

        //FilterResult filterResult = filterService.filter(request, Candidat.class);

        String criteriaParameter = request.getParameter("criteria");

        System.out.println(" candidat filtre " + criteriaParameter);

        if(criteriaParameter.equals("[]")) return "redirect:/pej/formateurs";


        FilterResult filterResult = candidatService.filter(request);

        List<Commune> communes = (List<Commune>) communeRepository.findAll();
        List<Departement> departements = (List<Departement>) departementRepository.findAll();
        List<Statutcandidat> statuts = (List<Statutcandidat>) statutcandidatRepository.findAll();
        List<String> arrondissements = (List<String>) candidatRepository.selectArrondissement();
        List<Lot> lots = (List<Lot>) lotRepository.findAll();
        List<Cooperative> cooperatives = (List<Cooperative>) cooperativeRepository.findAll();
        List<Typeformation> typeformations = (List<Typeformation>) typeformationRepository.findAll();



        User principal = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();


        String hasNext = "true";
        String hasPrevious = "true";

        if(filterResult.getCurrentPage() == filterResult.getNextPage()) hasNext = "false";
        if(filterResult.getCurrentPage() == filterResult.getPreviousPage()) hasPrevious = "false";



        model.addAttribute("candidats", filterResult.getData());
        model.addAttribute("total", filterResult.getTotal());
        model.addAttribute("communes", communes);
        model.addAttribute("departements", departements);
        model.addAttribute("lots", lots);
        model.addAttribute("typeFormations", typeformations);
        model.addAttribute("cooperatives", cooperatives);
        model.addAttribute("statuts", statuts);
        model.addAttribute("username", principal.getUsername());
        model.addAttribute("nextPage", filterResult.getNextPage());
        model.addAttribute("currentPage", filterResult.getCurrentPage());
        model.addAttribute("previousPage", filterResult.getPreviousPage());
        model.addAttribute("hasNext", hasNext);
        model.addAttribute("hasPrevious", hasPrevious);
        model.addAttribute("listOfPages", filterResult.getListOfPages());
        model.addAttribute("criteria", criteriaParameter);
        model.addAttribute("arrondissements", arrondissements);


        return "candidats";

    }
}
