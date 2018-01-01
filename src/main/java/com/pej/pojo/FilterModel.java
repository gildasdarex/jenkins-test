package com.pej.pojo;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by darextossa on 7/25/17.
 */
public class FilterModel {

    private String colonne;
    private String operation;
    private Object value;

    public FilterModel() {
    }

    public String getColonne() {
        return colonne;
    }

    public void setColonne(String colonne) {
        this.colonne = colonne;
    }

    public String getOperation() {
        return operation;
    }

    public void setOperation(String operation) {
        this.operation = operation;
    }

    public Object getValue() {
        return value;
    }

    public void setValue(Object value) {
        this.value = value;
    }

    public void clean(){
        switch (operation){
            case "equal": this.operation = "="; break;
            case "NoEqual": this.operation = "!=";break;
            case "sup": this.operation = ">";break;
            case "inf": this.operation = "<";break;
            case "supEqual": this.operation = ">=";break;
            case "infEqual": this.operation = "<=";break;
            case "content": this.operation = "like";break;
            case "in": this.operation = "in";break;
            case "ino": this.operation = "not in";break;
        }
    }


    public String getQuery(){
        if(! this.operation.equals("in") && ! this.operation.equals("not in"))
            return " o."+this.colonne + ' ' + this.operation + ' '+ "'" + this.value + "'";
        else {
            ArrayList<String> strings = (ArrayList<String>) this.value;
            return " o." + this.colonne + ' ' + this.operation + ' ' + "(" + String.join(",", strings) + ")";
        }
    }

    public String getQuery(String racine){
        if(! this.operation.equals("in") && ! this.operation.equals("not in"))
           return racine + "."+this.colonne + ' ' + this.operation + ' '+ "'" + this.value + "'";
        else{
            ArrayList<String> strings = (ArrayList<String>) this.value;
            return racine + "."+this.colonne + ' ' + this.operation + ' '+ "(" + String.join(",", strings) + ")";
        }

    }


    public static String getQuery(List<FilterModel> filterModels){
        String query = "";
        for(int i=0; i<=filterModels.size()-1; i++){
            query = query + " "+ filterModels.get(i).getQuery() ;
            if(i != filterModels.size()-1) query = query + " and ";
        }

        return query;
    }

    public static String getQuery(List<FilterModel> filterModels, String racine){
        String query = "";
        for(int i=0; i<=filterModels.size()-1; i++){
            query = query + " "+ filterModels.get(i).getQuery(racine) ;
            if(i != filterModels.size()-1) query = query + " and ";
        }

        return query;
    }
}
