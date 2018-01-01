package com.pej.domains;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
public class EntrepriseFormBean {
    private Departement departementChoisie;
    private  Commune communeChoisie;
    private Arrondissement arrondissementChoisie;
    private Quartier quartierChoisie;
    private Candidat candidatChoisie;
    private List<Departement> departements=new ArrayList<>();
    private List<Commune> communes=new ArrayList<>();
    private List<Arrondissement> arrondissements=new ArrayList<>();
    private List<Quartier> quartiers=new ArrayList<>();
    private List<Candidat> candidats=new ArrayList<>();
    private Integer identreprise;
    private String raisonsociale;
    private String numeroifu;
    private String contact;
    private String typeentreprise;
    private String longitude;
    private String latitude;

    public EntrepriseFormBean(Departement departementChoisie, Commune communeChoisie, Arrondissement arrondissementChoisie,Candidat candidatChoisie, Quartier quartierChoisie, List<Departement> departements, List<Commune> communes, List<Arrondissement> arrondissements,List<Candidat> candidats,String typeentreprise, List<Quartier> quartiers,String longitude,String latitude) {
        this.departementChoisie = departementChoisie;
        this.communeChoisie = communeChoisie;
        this.arrondissementChoisie = arrondissementChoisie;
        this.quartierChoisie = quartierChoisie;
        this.candidatChoisie=candidatChoisie;
        this.departements = departements;
        this.communes = communes;
        this.candidats = candidats;
        this.arrondissements = arrondissements;
        this.quartiers = quartiers;
        this.typeentreprise=typeentreprise;
        this.longitude=longitude;
        this.latitude=latitude;
    }

    public EntrepriseFormBean() {
    }

    public Departement getDepartementChoisie() {
        return departementChoisie;
    }

    public void setDepartementChoisie(Departement departementChoisie) {
        this.departementChoisie = departementChoisie;
    }

    public Commune getCommuneChoisie() {
        return communeChoisie;
    }

    public void setCommuneChoisie(Commune communeChoisie) {
        this.communeChoisie = communeChoisie;
    }

    public Arrondissement getArrondissementChoisie() {
        return arrondissementChoisie;
    }

    public void setArrondissementChoisie(Arrondissement arrondissementChoisie) {
        this.arrondissementChoisie = arrondissementChoisie;
    }

    public Quartier getQuartierChoisie() {
        return quartierChoisie;
    }

    public void setQuartierChoisie(Quartier quartierChoisie) {
        this.quartierChoisie = quartierChoisie;
    }

    public List<Departement> getDepartements() {
        return departements;
    }

    public void setDepartements(List<Departement> departements) {
        this.departements = departements;
    }

    public List<Commune> getCommunes() {
        return communes;
    }

    public void setCommunes(List<Commune> communes) {
        this.communes = communes;
    }

    public List<Arrondissement> getArrondissements() {
        return arrondissements;
    }

    public void setArrondissements(List<Arrondissement> arrondissements) {
        this.arrondissements = arrondissements;
    }

    public List<Quartier> getQuartiers() {
        return quartiers;
    }

    public void setQuartiers(List<Quartier> quartiers) {
        this.quartiers = quartiers;
    }

    public Integer getIdentreprise() {
        return identreprise;
    }

    public void setIdentreprise(Integer identreprise) {
        this.identreprise = identreprise;
    }

    public String getRaisonsociale() {
        return raisonsociale;
    }

    public void setRaisonsociale(String raisonsociale) {
        this.raisonsociale = raisonsociale;
    }

    public String getNumeroifu() {
        return numeroifu;
    }

    public void setNumeroifu(String numeroifu) {
        this.numeroifu = numeroifu;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getTypeentreprise() {
        return typeentreprise;
    }

    public void setTypeentreprise(String typeentreprise) {
        this.typeentreprise = typeentreprise;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public List<Candidat> getCandidats() {
        return candidats;
    }

    public void setCandidats(List<Candidat> candidats) {
        this.candidats = candidats;
    }

    public Candidat getCandidatChoisie() {
        return candidatChoisie;
    }

    public void setCandidatChoisie(Candidat candidatChoisie) {
        this.candidatChoisie = candidatChoisie;
    }
}