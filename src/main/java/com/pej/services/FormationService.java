package com.pej.services;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.pej.domains.*;
import com.pej.pojo.FilterModel;
import com.pej.pojo.FilterResult;
import com.pej.pojo.HelperEnum;
import com.pej.pojo.OdkCandidat;
import com.pej.repository.*;
import com.pej.utils.AlCollectionUtils;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Created by darextossa on 7/17/17.
 */

@Service()
public class FormationService {
    Logger logger = LogManager.getLogger(FormationService.class);

    @Autowired private FormationRepository formationRepository;
    @Autowired private FormationCooperativeRepository formationCooperativeRepository;
    @Autowired private FormationBeneficiareRepository formationBeneficiareRepository;
    @Autowired private PresenceRepository presenceRepository;
    @Autowired private FormateurRepository formateurRepository;
    @Autowired private FilterService filterService;


    public boolean deleteFormation(Integer id){

       Formation formation = formationRepository.findOne(id);
       Set<Formationbeneficaire> formationbeneficaires = formation.getFormationbeneficaires();
       Set<Formationcooperative> formationcooperatives = formation.getFormationcooperatives();
       Set<Presence> presences = formation.getPresences();
       List<Formateur> formateurs = formation.getFormateurSet();

       for(Formateur formateur : formateurs){
           formateur.getFormations().remove(formation);
           formateurRepository.save(formateur);
       }


       formationBeneficiareRepository.delete(formationbeneficaires);
       formationCooperativeRepository.delete(formationcooperatives);
       presenceRepository.delete(presences);
       formationRepository.delete(formation);

       return true;
    }


    public void saveFormation(Formation formation){
        Integer idFormationBeforePersist = formation.getIdformation();

        if (idFormationBeforePersist != null && idFormationBeforePersist.intValue() > 0)
            editFormation(formation);
        else{
            List<Integer> idFormateurs = formation.getFormateurSet().stream()
                                           .map(Formateur::getIdformateur)
                                           .collect(Collectors.toList());

            formation = formationRepository.save(formation);
            for(Integer id : idFormateurs){
                Formateur formateur = formateurRepository.findOne(id);
                formateur.getFormations().add(formation);
                formateurRepository.save(formateur);
            }
        }
    }


    private void editFormation(Formation formation){
        Formation oldFormation = formationRepository.findOne(formation.getIdformation());
        List<Integer> oldIdFormateurs = oldFormation.getFormateurSet().stream()
                .map(Formateur::getIdformateur)
                .collect(Collectors.toList());

        List<Integer> newIdFormateurs = formation.getFormateurSet().stream()
                .map(Formateur::getIdformateur)
                .collect(Collectors.toList());


        for(Integer id : oldIdFormateurs){
            if(! newIdFormateurs.contains(id)){
                Formateur formateur = formateurRepository.findOne(id);
                formateur.getFormations().remove(formation);
                formateurRepository.save(formateur);
            }
        }

        for(Integer id : newIdFormateurs){
            if(! oldIdFormateurs.contains(id)){
                Formateur formateur = formateurRepository.findOne(id);
                formateur.getFormations().add(formation);
                formateurRepository.save(formateur);
            }
        }

        formationRepository.save(formation);

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

        List<FilterModel> filtersByCooperative = copyOfCriteres.stream().
                filter(f -> f.getColonne().equals("cooperative.idgroupe")).
                collect(Collectors.toList());

        List<FilterModel> filtersByCabinet = copyOfCriteres.stream().
                filter(f -> f.getColonne().equals("cooperative.lot.cabinet.idcabinet")).
                collect(Collectors.toList());


        List<FilterModel> otherFilters = copyOfCriteres.stream().
                filter(f -> ! f.getColonne().equals("cooperative.lot.cabinet.idcabinet")  && ! f.getColonne().equals("cooperative.idgroupe") && ! f.getColonne().equals("cooperative.lot.idlot")).
                collect(Collectors.toList());


        String endQuery = FilterModel.getQuery(otherFilters);



        if(AlCollectionUtils.isNotEmpty(filtersByLot) && AlCollectionUtils.isEmpty(filtersByCooperative) && AlCollectionUtils.isEmpty(filtersByCabinet)){
            queryString = " select o from Formation o join o.formationcooperatives fc  where " + filtersByLot.get(0).getQuery("fc") ;
            countQueryString = " select count(o) from Formation o join o.formationcooperatives fc  where " + filtersByLot.get(0).getQuery("fc") ;

            if(endQuery != null && !endQuery.equals("")){
                queryString = queryString + " and " + endQuery;
                countQueryString = countQueryString + " and " + endQuery;
            }
        }

        else if(AlCollectionUtils.isEmpty(filtersByLot) && AlCollectionUtils.isNotEmpty(filtersByCooperative) && AlCollectionUtils.isEmpty(filtersByCabinet)){

            queryString = " select o from Formation o join o.formationcooperatives fc  where " + filtersByCooperative.get(0).getQuery("fc") ;
            countQueryString = " select count(o) from Formation o join o.formationcooperatives fc  where " + filtersByCooperative.get(0).getQuery("fc") ;

            if(endQuery != null && !endQuery.equals("")){
                queryString = queryString + " and " + endQuery;
                countQueryString = countQueryString + " and " + endQuery;
            }
        }

        else if(AlCollectionUtils.isEmpty(filtersByLot) && AlCollectionUtils.isEmpty(filtersByCooperative) && AlCollectionUtils.isNotEmpty(filtersByCabinet)){
            queryString = " select o from Formation o join o.formationcooperatives fc  where " + filtersByCabinet.get(0).getQuery("fc") ;
            countQueryString = " select count(o) from Formation o join o.formationcooperatives fc  where " + filtersByCabinet.get(0).getQuery("fc") ;

            if(endQuery != null && !endQuery.equals("")){
                queryString = queryString + " and " + endQuery;
                countQueryString = countQueryString + " and " + endQuery;
            }
        }
        else if(AlCollectionUtils.isEmpty(filtersByLot) && AlCollectionUtils.isNotEmpty(filtersByCooperative) && AlCollectionUtils.isNotEmpty(filtersByCabinet)){
            queryString = " select o from Formation o join o.formationcooperatives fc  where " + filtersByCabinet.get(0).getQuery("fc") + " and "+ filtersByCooperative.get(0).getQuery("fc");
            countQueryString = " select count(o) from Formation o join o.formationcooperatives fc  where " + filtersByCabinet.get(0).getQuery("fc") + " and "+ filtersByCooperative.get(0).getQuery("fc");

            if(endQuery != null && !endQuery.equals("")){
                queryString = queryString + " and " + endQuery;
                countQueryString = countQueryString + " and " + endQuery;
            }
        }
        else if(AlCollectionUtils.isNotEmpty(filtersByLot) && AlCollectionUtils.isEmpty(filtersByCooperative) && AlCollectionUtils.isNotEmpty(filtersByCabinet)){
            queryString = " select o from Formation o join o.formationcooperatives fc  where " + filtersByCabinet.get(0).getQuery("fc") + " and "+ filtersByLot.get(0).getQuery("fc");
            countQueryString = " select count(o) from Formation o join o.formationcooperatives fc  where " + filtersByCabinet.get(0).getQuery("fc") + " and "+ filtersByLot.get(0).getQuery("fc");

            if(endQuery != null && !endQuery.equals("")){
                queryString = queryString + " and " + endQuery;
                countQueryString = countQueryString + " and " + endQuery;
            }
        }
        else if(AlCollectionUtils.isNotEmpty(filtersByLot) && AlCollectionUtils.isNotEmpty(filtersByCooperative) && AlCollectionUtils.isEmpty(filtersByCabinet)){
            queryString = " select o from Formation o join o.formationcooperatives fc  where " + filtersByCooperative.get(0).getQuery("fc") + " and "+ filtersByLot.get(0).getQuery("fc");
            countQueryString = " select count(o) from Formation o join o.formationcooperatives fc  where " + filtersByCooperative.get(0).getQuery("fc") + " and "+ filtersByLot.get(0).getQuery("fc");

            if(endQuery != null && !endQuery.equals("")){
                queryString = queryString + " and " + endQuery;
                countQueryString = countQueryString + " and " + endQuery;
            }
        }
        else if(AlCollectionUtils.isNotEmpty(filtersByLot) && AlCollectionUtils.isNotEmpty(filtersByCooperative) && AlCollectionUtils.isNotEmpty(filtersByCabinet)){
            queryString = " select o from Formation o join o.formationcooperatives fc  where " + filtersByLot.get(0).getQuery("fc") + " and "+ filtersByCabinet.get(0).getQuery("fc") + " and "+ filtersByCooperative.get(0).getQuery("fc") ;
            countQueryString = " select count(o) from Formation o join o.formationcooperatives fc  where  " + filtersByLot.get(0).getQuery("fc") + " and "+ filtersByCabinet.get(0).getQuery("fc") + " and "+ filtersByCooperative.get(0).getQuery("fc");

            if(endQuery != null && !endQuery.equals("")){
                queryString = queryString + " and " + endQuery;
                countQueryString = countQueryString + " and " + endQuery;
            }
        }
        else{
            queryString  = filterService.getQuery(copyOfCriteres, Formation.class);
            countQueryString  = filterService.count(copyOfCriteres, Formation.class);
        }


        System.out.println("select with array " + queryString);

        filterResult = filterService.filter(request, queryString, countQueryString, 1000);

        return filterResult;

    }




}
