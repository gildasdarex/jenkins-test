package com.pej.services;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.pej.domains.*;
import com.pej.pojo.*;
import com.pej.repository.*;
import com.pej.utils.AlCollectionUtils;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by darextossa on 7/17/17.
 */

@Service()
public class CandidatService {
    Logger logger = LogManager.getLogger(CandidatService.class);

    @Autowired
    private org.dozer.Mapper mapper;
    @Autowired private AgentRepository agentRepository;
    @Autowired private CandidatRepository candidatRepository;
    @Autowired private StatutcandidatRepository statutcandidatRepository;
    @Autowired private PresenceRepository presenceRepository;
    @Autowired private FormationCooperativeRepository formationCooperativeRepository;
    @Autowired private BeneficiaireCooperativeRepository beneficiaireCooperativeRepository;
    @Autowired private  DoWorkService workService;
    @Autowired private  FilterService filterService;




    public boolean saveFromFile(List<OdkCandidat> odkCandidats){

        workService.doWork(odkCandidats);

        return true;
    }

    public int getNextPage(int page){
        page = page + 1;

        Pageable pageable =  new PageRequest( page, 100 );

        List<Candidat> candidats = (List<Candidat>) candidatRepository.findAllWithPAgination(pageable);

        if(candidats !=null && candidats.size() != 0) return page;
        else return page-1;
    }

    public int getPreviousPage(int page){
        if(page ==0) return 0;
        page = page - 1;

        Pageable pageable =  new PageRequest( page, 100 );

        List<Candidat> candidats = (List<Candidat>) candidatRepository.findAllWithPAgination(pageable);

        if(candidats !=null && candidats.size() != 0) return page;
        else return page+1;
    }


    public List<CandidatPresence> convertToCandidatPresence(List<Candidat> candidats, Formation formation){
        List<CandidatPresence> candidatPresences = new ArrayList<>();
        for(Candidat candidat: candidats){
            CandidatPresence candidatPresence = convertToCandidatPresence( candidat,  formation);
            candidatPresences.add(candidatPresence);
        }

        return candidatPresences;

    }


    public List<Candidat> getCandidatForFormation(Integer idformation){
        List<Candidat> candidats = new ArrayList<>();
        List<Formationcooperative> formationcooperatives = formationCooperativeRepository.findByFormationIdformation(idformation);

        for(Formationcooperative formationcooperative : formationcooperatives){
            Cooperative cooperative = formationcooperative.getCooperative();
            List<Beneficiairecooperative> beneficiairecooperatives = beneficiaireCooperativeRepository.findByCooperativeIdgroupe(cooperative.getIdgroupe());
            for (Beneficiairecooperative beneficiairecooperative : beneficiairecooperatives){
                candidats.add(beneficiairecooperative.getCandidat());
            }

        }

        return candidats;
    }


    public CandidatPresence convertToCandidatPresence(Candidat candidat, Formation formation){
        CandidatPresence candidatPresence = new CandidatPresence();
        List<Presence> presences = presenceRepository.findByCandidatIdcandidatAndFormationIdformation(
                candidat.getIdcandidat(),
                formation.getIdformation()
                );

        candidatPresence.setIdCandidat(candidat.getIdcandidat());
        candidatPresence.setFormation(formation);
        candidatPresence.setPresences(presences);
        candidatPresence.setIdentite(candidat.getIdentite());


        return candidatPresence;

    }



    public FilterResult filter(HttpServletRequest request){

        FilterResult filterResult = new FilterResult();

        String criteriaParameter = request.getParameter("criteria");

        List<FilterModel> criteres = new ArrayList<>();


        if(criteriaParameter != null && criteriaParameter != ""){
            Gson gson = new Gson();
            Type type = new TypeToken<List<FilterModel>>() {}.getType();
            criteres = gson.fromJson(criteriaParameter, type);
        }

        List<FilterModel> copyOfCriteres = ((List) ((ArrayList) criteres).clone());


        for (FilterModel filterModel : copyOfCriteres){
            filterModel.clean();
        }


        String queryString = "";
        String countQueryString = "";

        List<FilterModel> filtersByLot = copyOfCriteres.stream().
                filter(f -> f.getColonne().equals("cooperative.lot.idlot")).
                collect(Collectors.toList());

        List<FilterModel> filtersByFormation = copyOfCriteres.stream().
                filter(f -> f.getColonne().equals("formation.typeformation.idtypeformation")).
                collect(Collectors.toList());

        List<FilterModel> filtersByGroupe = copyOfCriteres.stream().
                filter(f -> f.getColonne().equals("cooperative.idgroupe")).
                collect(Collectors.toList());


        List<FilterModel> otherFilters = copyOfCriteres.stream().
                filter(f -> ! f.getColonne().equals("formation.typeformation.idtypeformation") && ! f.getColonne().equals("cooperative.idgroupe") && ! f.getColonne().equals("cooperative.lot.idlot")).
                collect(Collectors.toList());


        String endQuery = FilterModel.getQuery(otherFilters);

        if(AlCollectionUtils.isNotEmpty(filtersByLot) && AlCollectionUtils.isEmpty(filtersByGroupe) && AlCollectionUtils.isEmpty(filtersByFormation)){
            queryString = " select o from Candidat o join o.beneficiairecooperatives bf  where " + filtersByLot.get(0).getQuery("bf") ;
            countQueryString = " select count(o) from Candidat o join o.beneficiairecooperatives bf  where " + filtersByLot.get(0).getQuery("bf");

            if(endQuery != null && !endQuery.equals("")){
                queryString = queryString + " and " + endQuery;
                countQueryString = countQueryString + " and " + endQuery;
            }
        }

        else if(AlCollectionUtils.isEmpty(filtersByLot) && AlCollectionUtils.isNotEmpty(filtersByGroupe) && AlCollectionUtils.isEmpty(filtersByFormation)){

            queryString = " select o from Candidat o join o.beneficiairecooperatives bf  where " + filtersByGroupe.get(0).getQuery("bf") ;
            countQueryString = " select count(o) from Candidat o join o.beneficiairecooperatives bf  where " + filtersByGroupe.get(0).getQuery("bf");

            if(endQuery != null && !endQuery.equals("")){
                queryString = queryString + " and " + endQuery;
                countQueryString = countQueryString + " and " + endQuery;
            }
        }

        else if(AlCollectionUtils.isEmpty(filtersByLot) && AlCollectionUtils.isEmpty(filtersByGroupe) && AlCollectionUtils.isNotEmpty(filtersByFormation)){
            queryString = " select o from Candidat o join o.beneficiairecooperatives bf join bf.cooperative.formationcooperatives f where " + filtersByFormation.get(0).getQuery("f") ;
            countQueryString = " select count(o) from Candidat o join o.beneficiairecooperatives bf join bf.cooperative.formationcooperatives f where " + filtersByFormation.get(0).getQuery("f") ;

            if(endQuery != null && !endQuery.equals("")){
                queryString = queryString + " and " + endQuery;
                countQueryString = countQueryString + " and " + endQuery;
            }
        }
        else if(AlCollectionUtils.isEmpty(filtersByLot) && AlCollectionUtils.isNotEmpty(filtersByGroupe) && AlCollectionUtils.isNotEmpty(filtersByFormation)){
            queryString = " select o from Candidat o join o.beneficiairecooperatives bf join bf.cooperative.formationcooperatives f where " + filtersByGroupe.get(0).getQuery("bf") + " and "+ filtersByFormation.get(0).getQuery("f") ;
            countQueryString = " select count(o) from Candidat o join o.beneficiairecooperatives bf join bf.cooperative.formationcooperatives f where " + filtersByGroupe.get(0).getQuery("bf") + " and "+ filtersByFormation.get(0).getQuery("f") ;

            if(endQuery != null && !endQuery.equals("")){
                queryString = queryString + " and " + endQuery;
                countQueryString = countQueryString + " and " + endQuery;
            }
        }
        else if(AlCollectionUtils.isNotEmpty(filtersByLot) && AlCollectionUtils.isEmpty(filtersByGroupe) && AlCollectionUtils.isNotEmpty(filtersByFormation)){
            queryString = " select o from Candidat o join o.beneficiairecooperatives bf join bf.cooperative.formationcooperatives f where " + filtersByLot.get(0).getQuery("bf") + " and "+ filtersByFormation.get(0).getQuery("f") ;
            countQueryString = " select count(o) from Candidat o join o.beneficiairecooperatives bf join bf.cooperative.formationcooperatives f where " + filtersByLot.get(0).getQuery("bf") + " and "+ filtersByFormation.get(0).getQuery("f") ;

            if(endQuery != null && !endQuery.equals("")){
                queryString = queryString + " and " + endQuery;
                countQueryString = countQueryString + " and " + endQuery;
            }
        }
        else if(AlCollectionUtils.isNotEmpty(filtersByLot) && AlCollectionUtils.isNotEmpty(filtersByGroupe) && AlCollectionUtils.isEmpty(filtersByFormation)){
            queryString = " select o from Candidat o join o.beneficiairecooperatives bf where " + filtersByLot.get(0).getQuery("bf") + " and "+ filtersByGroupe.get(0).getQuery("bf") ;
            countQueryString = " select count(o) from Candidat o join o.beneficiairecooperatives bf where " + filtersByLot.get(0).getQuery("bf") + " and "+ filtersByGroupe.get(0).getQuery("bf") ;

            if(endQuery != null && !endQuery.equals("")){
                queryString = queryString + " and " + endQuery;
                countQueryString = countQueryString + " and " + endQuery;
            }

        }
        else if(AlCollectionUtils.isNotEmpty(filtersByLot) && AlCollectionUtils.isNotEmpty(filtersByGroupe) && AlCollectionUtils.isNotEmpty(filtersByFormation)){
            queryString = " select o from Candidat o join o.beneficiairecooperatives bf join bf.cooperative.formationcooperatives f where " + filtersByLot.get(0).getQuery("bf") + " and "+ filtersByFormation.get(0).getQuery("f") + " and "+ filtersByGroupe.get(0).getQuery("bf") ;
            countQueryString = " select count(o) from Candidat o join o.beneficiairecooperatives bf join bf.cooperative.formationcooperatives f where " + filtersByLot.get(0).getQuery("bf") + " and "+ filtersByFormation.get(0).getQuery("f") + " and "+ filtersByGroupe.get(0).getQuery("bf");

            if(endQuery != null && !endQuery.equals("")){
                queryString = queryString + " and " + endQuery;
                countQueryString = countQueryString + " and " + endQuery;
            }
        }
        else{
            queryString  = filterService.getQuery(copyOfCriteres, Candidat.class);
            countQueryString  = filterService.count(copyOfCriteres, Candidat.class);
        }


        System.out.println("select with array " + queryString);

        filterResult = filterService.filter(request, queryString, countQueryString, 100);

        return filterResult;

    }


//    private void generateExcel(List<Candidat> candidats){
//        List<ExcelCandidat> excelCandidats = new ArrayList<>();
//
//        for
//
//    }




}
