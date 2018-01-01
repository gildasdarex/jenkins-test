package com.pej.services;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.pej.domains.*;
import com.pej.pojo.FilterModel;
import com.pej.pojo.FilterResult;
import com.pej.repository.*;
import com.pej.utils.AlCollectionUtils;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by darextossa on 7/17/17.
 */

@Service()
public class CooperativeService {
    Logger logger = LogManager.getLogger(CooperativeService.class);

    @Autowired
    private CooperativeRepository cooperativeRepository;
    @Autowired
    private FormationCooperativeRepository formationcooperativeRepository;
    @Autowired
    private FormationRepository formationRepository;
    @Autowired
    private PresenceRepository presenceRepository;
    @Autowired
    private CandidatRepository candidatRepository;
    @Autowired
    private FilterService filterService;


    public boolean addFormationToCooperative(Integer idformation, Integer idgroupe){
        Formation formation = formationRepository.findOne(idformation);
        Cooperative cooperative = cooperativeRepository.findOne(idgroupe);

        Formationcooperative formationcooperative = new Formationcooperative();
        formationcooperative.setCooperative(cooperative);
        formationcooperative.setFormation(formation);

        if (formation != null && cooperative != null) {
            formationcooperativeRepository.save(formationcooperative);
        }

        return true;
    }


    public boolean deleteFormationFromCooperative(Integer idformation, Integer idgroupe){
        Formationcooperative formationcooperative = formationcooperativeRepository.findFb(idgroupe, idformation);
        if (formationcooperative != null)
            formationcooperativeRepository.delete(formationcooperative.getIdformationcooperative());


        return true;
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

        List<FilterModel> filtersByFormation = copyOfCriteres.stream().
                filter(f -> f.getColonne().equals("formation.typeformation.idtypeformation")).
                collect(Collectors.toList());

        List<FilterModel> otherFilters = copyOfCriteres.stream().
                filter(f -> ! f.getColonne().equals("formation.typeformation.idtypeformation") ).
                collect(Collectors.toList());


        String endQuery = FilterModel.getQuery(otherFilters);

        if(AlCollectionUtils.isNotEmpty(filtersByFormation)){

            queryString = " select o from Cooperative o join o.formationcooperatives fc  where " + filtersByFormation.get(0).getQuery("fc") ;
            countQueryString = " select count(o) from Cooperative o join o.formationcooperatives fc  where " + filtersByFormation.get(0).getQuery("fc");

            if(endQuery != null && !endQuery.equals("")){
                queryString = queryString + " and " + endQuery;
                countQueryString = countQueryString + " and " + endQuery;
            }
        }
        else{
            queryString  = filterService.getQuery(copyOfCriteres, Cooperative.class);
            countQueryString  = filterService.count(copyOfCriteres, Cooperative.class);
        }


        filterResult = filterService.filter(request, queryString, countQueryString, 100);

        return filterResult;

    }




}
