package com.pej.pojo;

import com.poiji.internal.annotation.ExcelCell;
import org.dozer.Mapping;

import java.util.Date;

/**
 * Created by darextossa on 7/17/17.
 */
public class OdkCandidat {

    @ExcelCell(0)
    public String ae_submissionDate;
    @ExcelCell(1)//no mapping
    @Mapping("dateenregistrementStr")
    public String ae_date_enregistrement;
    @ExcelCell(2)
    public String deviceid;
    @ExcelCell(3)
    public String ae_starttime;
    @ExcelCell(4)
    public String ae_endtime;
    @ExcelCell(5)
    public String subscriberid;
    @ExcelCell(6)
    public String simid;
    @ExcelCell(7)
    public String devicephonenum;
    @ExcelCell(8)
    @Mapping("agentenregistreur")//A créer
    public String ae;
    @ExcelCell(9)
    public String ae_autre;
    @Mapping("enqueteur")
    @ExcelCell(10)
    public String enqueteur;
    @Mapping("consentement")
    @ExcelCell(11)
    public String consentement;
    @ExcelCell(12)
    @Mapping("localdepartement")//departement
    public String departement;
    @Mapping("localcommune")
    @ExcelCell(13)
    public String commune;
    @Mapping("arrondissement")
    @ExcelCell(14)
    public String arrondissement;
    @Mapping("village")
    @ExcelCell(15)
    public String village;
    @Mapping("nom")
    @ExcelCell(16)
    public String candidat_nom;
    @Mapping("prenom")
    @ExcelCell(17)
    public String candidat_prenom;
    @ExcelCell(18)
    public String candidat_surnom;
    @Mapping("nompere")
    @ExcelCell(19)
    public String candidat_nom_du_pere;
    @Mapping("nommere")
    @ExcelCell(20)
    public String candidat_nom_mere;
    @ExcelCell(21)
    public String candidat_document_identite;
    @ExcelCell(22)
    public String candidat_document_identite_au;
    @ExcelCell(23)
    @Mapping("refdocidentite")
    public String candidat_numero_piece;
    @Mapping("titreautoritelocale")
    @ExcelCell(24)
    public String titre_authorite_local;
    @Mapping("nomautoritelocale")
    @ExcelCell(25)
    public String nom_authorite_local;
    @ExcelCell(26)
    @Mapping("telprincipal")
    public String candidat_telephone_principal;
    @ExcelCell(27)
    @Mapping("telalternatif")
    public String candidat_telephone_alternatif;
    @ExcelCell(28)
    public String ac;
    @ExcelCell(29)
    @Mapping("lieupassagetemps")
    public String candidat_lieu_passage_temps;
    @ExcelCell(30)
    @Mapping("rue")
    public String candidat_nom_rue;
    @ExcelCell(31)
    public String candidat_ilot;
    @ExcelCell(32)
    @Mapping("parcelnomproprio")
    public String candidat_num_parcel_nom_propr;
    @ExcelCell(33)
    @Mapping("pointrepere1")
    public String candidat_point_repere_1;
    @ExcelCell(34)
    @Mapping("pointrepere2")
    public String candidat_point_repere_2;
    @ExcelCell(35)
    public String candidat_personne_ice;
    @ExcelCell(36)
    public String candidat_ice_lien_ice;
    @ExcelCell(37)
    public String candidat_ice_contact1;
    @ExcelCell(38)
    public String candidat_ice_contact2;
    @ExcelCell(39)
    public String is_sexe;
    @Mapping("age")
    @ExcelCell(40)
    public int is_age;
    @ExcelCell(41)
    @Mapping("nourrison")//A créer
    public String is_bebe2ans;
    @ExcelCell(42)//no mapping
    public String langue_parlee;
    @ExcelCell(43)//no mapping
    public String langue_parlee_autre;
    @ExcelCell(44)//no mapping
    public String is_situation_matri;
    @Mapping("beneficiairepsdcc")//A créer
    @ExcelCell(45)
    public String beneficiaire_psdcc;
    @Mapping("liencm")//A créer
    @ExcelCell(46)
    public String is_lien_cm;
    @Mapping("nbpersonnemenage")
    @ExcelCell(47)
    public int is_effectif_menage;
    @Mapping("scolarise")
    @ExcelCell(48)
    public String isq_ecole_formelle;
    @Mapping("dernierniveauetude")
    @ExcelCell(49)
    public String isq_niveau_scolaire;
    @ExcelCell(50)//no mapping
    public String isq_niveau_scolaire_autre;
    @ExcelCell(51)//no mapping
    public String isq_niveau_alphabet_fr;
    @ExcelCell(52)//no mapping
    public String isq_niveau_alphabet_local;
    @ExcelCell(53)
    @Mapping("qualificationpersonelle")
    public String isq_qual_prof;
    @ExcelCell(54)
    @Mapping("autrequalifpersonelle")
    public String isq_qual_prof_autre;
    @ExcelCell(55)
    public String isq_formation_entrepreunariat;
    @Mapping("worklastmonth")
    @ExcelCell(56)
    public String travailer_3_der_mois;
    @ExcelCell(57)
    @Mapping("recherchetravail")
    public String chercher_travail_3_der_mois;
    @ExcelCell(58)//no mapping
    public String raison_chercher_travail;
    @ExcelCell(59)//no mapping
    public String raison_chercher_travail_autre;
    @ExcelCell(60)//no mapping
    public String activite_principale;
    @ExcelCell(61)//no mapping
    public String activite_principale_autre;
    @ExcelCell(62)//no mapping
    public String employeur;
    @Mapping("nbmoistravail")
    @ExcelCell(63)
    public int nb_mois_travail_par_an;
    @Mapping("nbjoursmoyen")
    @ExcelCell(64)
    public int nb_jour_travail_par_mois;
    @Mapping("nbheuremoyen")
    @ExcelCell(65)
    public int nb_heure_travail_par_jour;
    @Mapping("revenumoyen")
    @ExcelCell(66)
    public String revenu_mensuel;
    @Mapping("domainesouhait")
    @ExcelCell(67)
    public String domaine_activite;
    @ExcelCell(68)
    public String domaine_activite_autre;
    @Mapping("travailgroupe")
    @ExcelCell(69)
    public String travail_en_cooperative;
    @ExcelCell(70)
    public String source_info_pej;
    @Mapping("nomradio")
    @ExcelCell(71)
    public String nomradio;
    @Mapping("numeroserie")
    @ExcelCell(72)
    public String numero_serie;
    @Mapping("numerocandidat")
    @ExcelCell(73)
    public String numero_candidat;
    @ExcelCell(74)
    @Mapping("commentaire")//commentaire
    public String commentaire;
    @ExcelCell(75)
    public String meta_instanceName;
    @ExcelCell(76)
    public String after_status;
    @ExcelCell(77)//Ce champ existe pas dans mon fichier
    public String statut;


    public String getAe_submissionDate() {
        return ae_submissionDate;
    }

    public void setAe_submissionDate(String ae_submissionDate) {
        this.ae_submissionDate = ae_submissionDate;
    }

    public String getAe_date_enregistrement() {
        return ae_date_enregistrement;
    }

    public void setAe_date_enregistrement(String ae_date_enregistrement) {
        this.ae_date_enregistrement = ae_date_enregistrement;
    }

    public String getDeviceid() {
        return deviceid;
    }

    public void setDeviceid(String deviceid) {
        this.deviceid = deviceid;
    }

    public String getAe_starttime() {
        return ae_starttime;
    }

    public void setAe_starttime(String ae_starttime) {
        this.ae_starttime = ae_starttime;
    }

    public String getAe_endtime() {
        return ae_endtime;
    }

    public void setAe_endtime(String ae_endtime) {
        this.ae_endtime = ae_endtime;
    }

    public String getSubscriberid() {
        return subscriberid;
    }

    public void setSubscriberid(String subscriberid) {
        this.subscriberid = subscriberid;
    }

    public String getDevicephonenum() {
        return devicephonenum;
    }

    public void setDevicephonenum(String devicephonenum) {
        this.devicephonenum = devicephonenum;
    }

    public String getAe() {
        return ae;
    }

    public void setAe(String ae) {
        this.ae = ae;
    }

    public String getAe_autre() {
        return ae_autre;
    }

    public void setAe_autre(String ae_autre) {
        this.ae_autre = ae_autre;
    }

    public String getEnqueteur() {
        return enqueteur;
    }

    public void setEnqueteur(String enqueteur) {
        this.enqueteur = enqueteur;
    }

    public String getConsentement() {
        return consentement;
    }

    public void setConsentement(String consentement) {
        this.consentement = consentement;
    }

    public String getDepartement() {
        return departement;
    }

    public void setDepartement(String departement) {
        this.departement = departement;
    }

    public String getCommune() {
        return commune;
    }

    public void setCommune(String commune) {
        this.commune = commune;
    }

    public String getArrondissement() {
        return arrondissement;
    }

    public void setArrondissement(String arrondissement) {
        this.arrondissement = arrondissement;
    }

    public String getVillage() {
        return village;
    }

    public void setVillage(String village) {
        this.village = village;
    }

    public String getCandidat_nom() {
        return candidat_nom;
    }

    public void setCandidat_nom(String candidat_nom) {
        this.candidat_nom = candidat_nom;
    }

    public String getCandidat_prenom() {
        return candidat_prenom;
    }

    public void setCandidat_prenom(String candidat_prenom) {
        this.candidat_prenom = candidat_prenom;
    }

    public String getCandidat_surnom() {
        return candidat_surnom;
    }

    public void setCandidat_surnom(String candidat_surnom) {
        this.candidat_surnom = candidat_surnom;
    }

    public String getCandidat_nom_du_pere() {
        return candidat_nom_du_pere;
    }

    public void setCandidat_nom_du_pere(String candidat_nom_du_pere) {
        this.candidat_nom_du_pere = candidat_nom_du_pere;
    }

    public String getCandidat_nom_mere() {
        return candidat_nom_mere;
    }

    public void setCandidat_nom_mere(String candidat_nom_mere) {
        this.candidat_nom_mere = candidat_nom_mere;
    }

    public String getCandidat_document_identite() {
        return candidat_document_identite;
    }

    public void setCandidat_document_identite(String candidat_document_identite) {
        this.candidat_document_identite = candidat_document_identite;
    }

    public String getCandidat_document_identite_au() {
        return candidat_document_identite_au;
    }

    public void setCandidat_document_identite_au(String candidat_document_identite_au) {
        this.candidat_document_identite_au = candidat_document_identite_au;
    }

    public String getCandidat_numero_piece() {
        return candidat_numero_piece;
    }

    public void setCandidat_numero_piece(String candidat_numero_piece) {
        this.candidat_numero_piece = candidat_numero_piece;
    }

    public String getCandidat_telephone_principal() {
        return candidat_telephone_principal;
    }

    public void setCandidat_telephone_principal(String candidat_telephone_principal) {
        this.candidat_telephone_principal = candidat_telephone_principal;
    }

    public String getCandidat_telephone_alternatif() {
        return candidat_telephone_alternatif;
    }

    public void setCandidat_telephone_alternatif(String candidat_telephone_alternatif) {
        this.candidat_telephone_alternatif = candidat_telephone_alternatif;
    }

    public String getAc() {
        return ac;
    }

    public void setAc(String ac) {
        this.ac = ac;
    }

    public String getCandidat_lieu_passage_temps() {
        return candidat_lieu_passage_temps;
    }

    public void setCandidat_lieu_passage_temps(String candidat_lieu_passage_temps) {
        this.candidat_lieu_passage_temps = candidat_lieu_passage_temps;
    }

    public String getCandidat_nom_rue() {
        return candidat_nom_rue;
    }

    public void setCandidat_nom_rue(String candidat_nom_rue) {
        this.candidat_nom_rue = candidat_nom_rue;
    }

    public String getCandidat_ilot() {
        return candidat_ilot;
    }

    public void setCandidat_ilot(String candidat_ilot) {
        this.candidat_ilot = candidat_ilot;
    }

    public String getCandidat_num_parcel_nom_propr() {
        return candidat_num_parcel_nom_propr;
    }

    public void setCandidat_num_parcel_nom_propr(String candidat_num_parcel_nom_propr) {
        this.candidat_num_parcel_nom_propr = candidat_num_parcel_nom_propr;
    }

    public String getCandidat_point_repere_1() {
        return candidat_point_repere_1;
    }

    public void setCandidat_point_repere_1(String candidat_point_repere_1) {
        this.candidat_point_repere_1 = candidat_point_repere_1;
    }

    public String getCandidat_point_repere_2() {
        return candidat_point_repere_2;
    }

    public void setCandidat_point_repere_2(String candidat_point_repere_2) {
        this.candidat_point_repere_2 = candidat_point_repere_2;
    }

    public String getCandidat_personne_ice() {
        return candidat_personne_ice;
    }

    public void setCandidat_personne_ice(String candidat_personne_ice) {
        this.candidat_personne_ice = candidat_personne_ice;
    }

    public String getCandidat_ice_lien_ice() {
        return candidat_ice_lien_ice;
    }

    public void setCandidat_ice_lien_ice(String candidat_ice_lien_ice) {
        this.candidat_ice_lien_ice = candidat_ice_lien_ice;
    }

    public String getCandidat_ice_contact1() {
        return candidat_ice_contact1;
    }

    public void setCandidat_ice_contact1(String candidat_ice_contact1) {
        this.candidat_ice_contact1 = candidat_ice_contact1;
    }

    public String getCandidat_ice_contact2() {
        return candidat_ice_contact2;
    }

    public void setCandidat_ice_contact2(String candidat_ice_contact2) {
        this.candidat_ice_contact2 = candidat_ice_contact2;
    }

    public String getIs_sexe() {
        return is_sexe;
    }

    public void setIs_sexe(String is_sexe) {
        this.is_sexe = is_sexe;
    }

    public int getIs_age() {
        return is_age;
    }

    public void setIs_age(int is_age) {
        this.is_age = is_age;
    }

    public String getIs_bebe2ans() {
        return is_bebe2ans;
    }

    public void setIs_bebe2ans(String is_bebe2ans) {
        this.is_bebe2ans = is_bebe2ans;
    }

    public String getLangue_parlee() {
        return langue_parlee;
    }

    public void setLangue_parlee(String langue_parlee) {
        this.langue_parlee = langue_parlee;
    }

    public String getLangue_parlee_autre() {
        return langue_parlee_autre;
    }

    public void setLangue_parlee_autre(String langue_parlee_autre) {
        this.langue_parlee_autre = langue_parlee_autre;
    }

    public String getIs_situation_matri() {
        return is_situation_matri;
    }

    public void setIs_situation_matri(String is_situation_matri) {
        this.is_situation_matri = is_situation_matri;
    }

    public String getBeneficiaire_psdcc() {
        return beneficiaire_psdcc;
    }

    public void setBeneficiaire_psdcc(String beneficiaire_psdcc) {
        this.beneficiaire_psdcc = beneficiaire_psdcc;
    }

    public String getIs_lien_cm() {
        return is_lien_cm;
    }

    public void setIs_lien_cm(String is_lien_cm) {
        this.is_lien_cm = is_lien_cm;
    }

    public int getIs_effectif_menage() {
        return is_effectif_menage;
    }

    public void setIs_effectif_menage(int is_effectif_menage) {
        this.is_effectif_menage = is_effectif_menage;
    }

    public String getIsq_ecole_formelle() {
        return isq_ecole_formelle;
    }

    public void setIsq_ecole_formelle(String isq_ecole_formelle) {
        this.isq_ecole_formelle = isq_ecole_formelle;
    }

    public String getIsq_niveau_scolaire() {
        return isq_niveau_scolaire;
    }

    public void setIsq_niveau_scolaire(String isq_niveau_scolaire) {
        this.isq_niveau_scolaire = isq_niveau_scolaire;
    }

    public String getIsq_niveau_scolaire_autre() {
        return isq_niveau_scolaire_autre;
    }

    public void setIsq_niveau_scolaire_autre(String isq_niveau_scolaire_autre) {
        this.isq_niveau_scolaire_autre = isq_niveau_scolaire_autre;
    }

    public String getIsq_niveau_alphabet_fr() {
        return isq_niveau_alphabet_fr;
    }

    public void setIsq_niveau_alphabet_fr(String isq_niveau_alphabet_fr) {
        this.isq_niveau_alphabet_fr = isq_niveau_alphabet_fr;
    }

    public String getIsq_niveau_alphabet_local() {
        return isq_niveau_alphabet_local;
    }

    public void setIsq_niveau_alphabet_local(String isq_niveau_alphabet_local) {
        this.isq_niveau_alphabet_local = isq_niveau_alphabet_local;
    }

    public String getIsq_qual_prof() {
        return isq_qual_prof;
    }

    public void setIsq_qual_prof(String isq_qual_prof) {
        this.isq_qual_prof = isq_qual_prof;
    }

    public String getIsq_qual_prof_autre() {
        return isq_qual_prof_autre;
    }

    public void setIsq_qual_prof_autre(String isq_qual_prof_autre) {
        this.isq_qual_prof_autre = isq_qual_prof_autre;
    }

    public String getIsq_formation_entrepreunariat() {
        return isq_formation_entrepreunariat;
    }

    public void setIsq_formation_entrepreunariat(String isq_formation_entrepreunariat) {
        this.isq_formation_entrepreunariat = isq_formation_entrepreunariat;
    }

    public String getTravailer_3_der_mois() {
        return travailer_3_der_mois;
    }

    public void setTravailer_3_der_mois(String travailer_3_der_mois) {
        this.travailer_3_der_mois = travailer_3_der_mois;
    }

    public String getChercher_travail_3_der_mois() {
        return chercher_travail_3_der_mois;
    }

    public void setChercher_travail_3_der_mois(String chercher_travail_3_der_mois) {
        this.chercher_travail_3_der_mois = chercher_travail_3_der_mois;
    }

    public String getRaison_chercher_travail() {
        return raison_chercher_travail;
    }

    public void setRaison_chercher_travail(String raison_chercher_travail) {
        this.raison_chercher_travail = raison_chercher_travail;
    }

    public String getRaison_chercher_travail_autre() {
        return raison_chercher_travail_autre;
    }

    public void setRaison_chercher_travail_autre(String raison_chercher_travail_autre) {
        this.raison_chercher_travail_autre = raison_chercher_travail_autre;
    }

    public String getActivite_principale() {
        return activite_principale;
    }

    public void setActivite_principale(String activite_principale) {
        this.activite_principale = activite_principale;
    }

    public String getActivite_principale_autre() {
        return activite_principale_autre;
    }

    public void setActivite_principale_autre(String activite_principale_autre) {
        this.activite_principale_autre = activite_principale_autre;
    }

    public String getEmployeur() {
        return employeur;
    }

    public void setEmployeur(String employeur) {
        this.employeur = employeur;
    }

    public int getNb_mois_travail_par_an() {
        return nb_mois_travail_par_an;
    }

    public void setNb_mois_travail_par_an(int nb_mois_travail_par_an) {
        this.nb_mois_travail_par_an = nb_mois_travail_par_an;
    }

    public int getNb_jour_travail_par_mois() {
        return nb_jour_travail_par_mois;
    }

    public void setNb_jour_travail_par_mois(int nb_jour_travail_par_mois) {
        this.nb_jour_travail_par_mois = nb_jour_travail_par_mois;
    }

    public int getNb_heure_travail_par_jour() {
        return nb_heure_travail_par_jour;
    }

    public void setNb_heure_travail_par_jour(int nb_heure_travail_par_jour) {
        this.nb_heure_travail_par_jour = nb_heure_travail_par_jour;
    }

    public String getRevenu_mensuel() {
        return revenu_mensuel;
    }

    public void setRevenu_mensuel(String revenu_mensuel) {
        this.revenu_mensuel = revenu_mensuel;
    }

    public String getDomaine_activite() {
        return domaine_activite;
    }

    public void setDomaine_activite(String domaine_activite) {
        this.domaine_activite = domaine_activite;
    }

    public String getDomaine_activite_autre() {
        return domaine_activite_autre;
    }

    public void setDomaine_activite_autre(String domaine_activite_autre) {
        this.domaine_activite_autre = domaine_activite_autre;
    }

    public String getTravail_en_cooperative() {
        return travail_en_cooperative;
    }

    public void setTravail_en_cooperative(String travail_en_cooperative) {
        this.travail_en_cooperative = travail_en_cooperative;
    }

    public String getSource_info_pej() {
        return source_info_pej;
    }

    public void setSource_info_pej(String source_info_pej) {
        this.source_info_pej = source_info_pej;
    }

    public String getNomradio() {
        return nomradio;
    }

    public void setNomradio(String nomradio) {
        this.nomradio = nomradio;
    }

    public String getNumero_serie() {
        return numero_serie;
    }

    public void setNumero_serie(String numero_serie) {
        this.numero_serie = numero_serie;
    }

    public String getNumero_candidat() {
        return numero_candidat;
    }

    public void setNumero_candidat(String numero_candidat) {
        this.numero_candidat = numero_candidat;
    }

    public String getCommentaire() {
        return commentaire;
    }

    public void setCommentaire(String commentaire) {
        this.commentaire = commentaire;
    }

    public String getMeta_instanceName() {
        return meta_instanceName;
    }

    public void setMeta_instanceName(String meta_instanceName) {
        this.meta_instanceName = meta_instanceName;
    }

    public String getStatut() {
        return statut;
    }

    public void setStatut(String statut) {
        this.statut = statut;
    }

    public String getSimid() {
        return simid;
    }

    public void setSimid(String simid) {
        this.simid = simid;
    }

    public String getTitre_authorite_local() {
        return titre_authorite_local;
    }

    public void setTitre_authorite_local(String titre_authorite_local) {
        this.titre_authorite_local = titre_authorite_local;
    }

    public String getNom_authorite_local() {
        return nom_authorite_local;
    }

    public void setNom_authorite_local(String nom_authorite_local) {
        this.nom_authorite_local = nom_authorite_local;
    }

    public String getAfter_status() {
        return after_status;
    }

    public void setAfter_status(String after_status) {
        this.after_status = after_status;
    }
}
