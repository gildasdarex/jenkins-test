package com.pej.services;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.pej.pojo.FilterModel;
import com.pej.pojo.FilterResult;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

/**
 * Created by darextossa on 7/26/17.
 */
@Service()
public class StatistiqueService {

    @PersistenceContext
    private EntityManager em;


    public FilterResult filter(HttpServletRequest request, Class clazz){
        String tableau = request.getParameter("tableau_repartition");
        String groupByCommune = request.getParameter("groupByCommune");
        String groupBySexe = request.getParameter("groupBySexe");

        return null;
    }


    public String getRepartitionFormationQuery(HttpServletRequest request){
        String tableau = request.getParameter("tableau_repartition");
        String groupByCommune = request.getParameter("groupByCommune");
        String groupBySexe = request.getParameter("groupBySexe");
        String formation = request.getParameter("groupBySexe");
        return null;
    }

    public String getCandidatQuery(HttpServletRequest request){
        FilterResult filterResult = new FilterResult();

        String groupByCommune = request.getParameter("groupByCommune");
        String pageParameter = request.getParameter("page");
        int page =  0;

        if(pageParameter != null && pageParameter != "") page = Integer.parseInt(pageParameter);

        int nextPage =  page;
        int previousPage =  page;

        filterResult.setCurrentPage(page);

        String query = "select count(o), localcommune, localdepartement from Candidat o group by localcommune";

        Query q =  em.createQuery(query);
        q.setFirstResult(page*100);
        q.setMaxResults(100);

        List<Object[]> data = q.getResultList();
        return null;
    }




}
