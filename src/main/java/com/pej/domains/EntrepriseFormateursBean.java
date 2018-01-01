package com.pej.domains;
import java.util.ArrayList;
import java.util.List;
public class EntrepriseFormateursBean {

    private List<Entreprise> entreprises=new ArrayList<>();
    private List<Formateur> formateurs=new ArrayList<>();

    public EntrepriseFormateursBean(List<Entreprise> entreprises, List<Formateur> formateurs) {
        this.entreprises = entreprises;
        this.formateurs = formateurs;
    }

    public EntrepriseFormateursBean() {
    }

    public List<Entreprise> getEntreprises() {
        return entreprises;
    }

    public void setEntreprises(List<Entreprise> entreprises) {
        this.entreprises = entreprises;
    }

    public List<Formateur> getFormateurs() {
        return formateurs;
    }

    public void setFormateurs(List<Formateur> formateurs) {
        this.formateurs = formateurs;
    }
}
