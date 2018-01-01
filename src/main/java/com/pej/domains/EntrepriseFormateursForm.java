package com.pej.domains;
import java.util.ArrayList;
import java.util.List;
public class EntrepriseFormateursForm {

    private Entreprise  entreprise;
    private List<Formateur> formateurs=new ArrayList<>();

    public EntrepriseFormateursForm(Entreprise entreprise, List<Formateur> formateurs) {
        this.entreprise = entreprise;
        this.formateurs = formateurs;
    }

    public EntrepriseFormateursForm() {
    }

    public Entreprise getEntreprise() {
        return entreprise;
    }

    public void setEntreprise(Entreprise entreprise) {
        this.entreprise = entreprise;
    }

    public List<Formateur> getFormateurs() {
        return formateurs;
    }

    public void setFormateurs(List<Formateur> formateurs) {
        this.formateurs = formateurs;
    }
}
