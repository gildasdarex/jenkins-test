package com.pej.pojo;

/**
 * Created by darextossa on 7/20/17.
 */
public class HelperEnum {

    public static String getSituationMatrimoniale(int code){
        switch(code){
            case 1: return "";
            default: return "";
        }
    }

    public static String getSexe(String code){
        switch(code){
            case "1": return "Masculin";
            default: return "Feminin";
        }
    }


    public static String getDocumentIdentite(String code){
        switch(code){
            case "1": return "Aucun";
            case "2": return "Carte nationale d’identité";
            case "3": return "Carte LEPI";
            case "4": return "Passeport ";
            case "5": return "Permis de conduire ";
            case "6": return "Carte d’étudiant/scolaire/professionnelle ";
            case "99": return "Autres";
            default: return "";
        }
    }


    public static String getActivite(String code){
        switch(code){
            case "1": return "Agriculture : céréales, tubercules, légumineuses, plantation";
            case "2": return "Agriculture : maraîchage, arbres fruitiers ou oléifères";
            case "3": return "Bâtiment et construction";
            case "4": return "Coiffure et soins corporels ";
            case "5": return "Commerce";
            case "6": return "Electronique, électricité et froid";
            case "7": return "Elevage et pisciculture";
            case "8": return "Entretien et réparations (maintenance)";
            case "9": return "Exploitation minière";
            case "10": return "Hôtellerie et restauration";
            case "11": return "Menuiserie";
            case "12": return "Métaux et mécanique";
            case "13": return "Pêche, chasse, cueillette";
            case "14": return "Pierre, poterie, céramique ";
            case "15": return "Prestataire agricole";
            case "16": return "Services financiers";
            case "17": return "Services informatiques, photocopies";
            case "18": return "Textiles";
            case "19": return "Tourisme";
            case "20": return "Transformation agricole";
            case "21": return "Transport";
            case "99": return "Autre (à préciser)";

            default: return "";
        }

    }
}
