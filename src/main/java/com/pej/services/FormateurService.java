package com.pej.services;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.pej.domains.*;
import com.pej.pojo.FilterModel;
import com.pej.pojo.FilterResult;
import com.pej.repository.*;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by darextossa on 7/17/17.
 */

@Service()
public class FormateurService {
    Logger logger = LogManager.getLogger(FormateurService.class);

    @Autowired
    private FormateurRepository formateurRepository;
    @Autowired
    private CabinetRepository cabinetRepository;
    @Autowired
    private UserRoleRepository userRoleRepository;
    @Autowired
    private RolesRepository roleRepository;
    @Autowired
    private UsersRepository userRepository;
    @Autowired
    private FormationRepository formationRepository;
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    @Autowired
    private FilterService filterService;
    @PersistenceContext
    private EntityManager em;




    public boolean deleteFormateur(Integer id){
        Formateur formateur = formateurRepository.findOne(id);
        List<Formation> formations = formateur.getFormations();

        for(Formation formation: formations) {
            formationRepository.delete(formation);
        }

        formateurRepository.delete(formateur);

        return true;
    }


    @Transactional(propagation= Propagation.REQUIRED)
    public boolean createFormateur(Formateur formateur){
        try {
            formateur = formateurRepository.save(formateur);
            Utilisateur user = setFormateurUser(formateur);
            setFormateurRole(user);
        }
        catch (Exception ex){
            logger.debug("error when create new user " + ex.getMessage());
            return false ;
        }

        return true;
    }

    @Transactional(propagation= Propagation.REQUIRED)
    public boolean editFormateur(Formateur updateFormateur){
        try{
            Formateur formateur = formateurRepository.findOne(updateFormateur.getIdformateur());

            updateFormateur.setUsername(formateur.getUsername());
            updateFormateur.setDatenaissance(formateur.getDatenaissance());

            formateurRepository.save(updateFormateur);
        }
        catch (Exception ex){
            logger.debug("error when edit  user " + ex.getMessage());
            return false ;
        }

        return true;
    }



    private Utilisateur setFormateurUser(Formateur formateur){
        Utilisateur user = new Utilisateur();

        user.setUsername(formateur.getUsername());
        user.setPassword(bCryptPasswordEncoder.encode(formateur.getPassword()));
        user.setFirstname(formateur.getNom());
        user.setLastname(formateur.getPrenom());
        user.setEmail(formateur.getEmail());

        userRepository.save(user);

        return user;
    }


    private boolean setFormateurRole(Utilisateur user){
        Roles roles = roleRepository.findByName("FORMATEUR");
        UsersRole userrole = new UsersRole();
        userrole.setRoles(roles);
        userrole.setUtilisateur(user);
        userRoleRepository.save(userrole);

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
                filter(f -> f.getColonne().equals("typeformation.idtypeformation")).
                collect(Collectors.toList());

        List<FilterModel> filtersByGroupe = copyOfCriteres.stream().
                filter(f -> f.getColonne().equals("cooperative.idgroupe")).
                collect(Collectors.toList());


        List<FilterModel> filters = copyOfCriteres.stream().
                filter(f -> f.getColonne().equals("typeformation.idtypeformation") || f.getColonne().equals("cooperative.idgroupe")).
                collect(Collectors.toList());

        List<FilterModel> otherFilters = copyOfCriteres.stream().
                filter(f -> ! f.getColonne().equals("typeformation.idtypeformation") && ! f.getColonne().equals("cooperative.idgroupe")).
                collect(Collectors.toList());


        String endQuery = FilterModel.getQuery(otherFilters);

        if(filtersByFormation !=null && !filtersByFormation.isEmpty() && (filtersByGroupe == null || filtersByGroupe.isEmpty())){
            queryString = " select o from Formateur o join o.formations f where " + filtersByFormation.get(0).getQuery("f") ;
            countQueryString = " select count(o) from Formateur o join o.formations f where " + filtersByFormation.get(0).getQuery("f");
        }

        else if(filtersByGroupe !=null && !filtersByGroupe.isEmpty() &&(filtersByFormation == null || filtersByFormation.isEmpty())){
            queryString = " select o from Formateur o join o.formations f join f.formationcooperatives c where " + filtersByGroupe.get(0).getQuery("c");
            countQueryString = " select count(o) from Formateur o join o.formations f join f.formationcooperatives c where " + filtersByGroupe.get(0).getQuery("c");
        }

        else if(filtersByGroupe !=null && filtersByFormation !=null && !filtersByFormation.isEmpty() && !filtersByGroupe.isEmpty()){
            queryString = " select o from Formateur o join o.formations f join f.formationcooperatives c where " + filtersByFormation.get(0).getQuery("f") + " and " +filtersByGroupe.get(0).getQuery("c") ;
            countQueryString = " select count(o) from Formateur o join o.formations f join f.formationcooperatives c where " + filtersByFormation.get(0).getQuery("f") + " and " +filtersByGroupe.get(0).getQuery("c") ;

        }else if(filters == null || filters.isEmpty()){
            queryString  = filterService.getQuery(copyOfCriteres, Formateur.class);
            countQueryString  = filterService.count(copyOfCriteres, Formateur.class);
        }else{

        }

        if(endQuery != null && !endQuery.equals("")){
            queryString = queryString + " and " + endQuery;
            countQueryString = countQueryString + " and " + endQuery;
        }

        System.out.println("select with array " + queryString);

        filterResult = filterService.filter(request, queryString, countQueryString, 100);

        return filterResult;

    }




}
