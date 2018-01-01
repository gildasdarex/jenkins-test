package com.pej.domains;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Proxy;

import javax.persistence.*;
import java.util.Date;
@Entity
@Table(name = "ENTREPRISEFORMATEUR")
@Proxy(lazy=false)
public class EntrepriseFormateur implements java.io.Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 1L;
    private Integer identrepriseformateur;
    @JsonIgnore
    private Formateur formateur;
    @JsonIgnore
    private Entreprise entreprise;

    public EntrepriseFormateur() {
    }

    public EntrepriseFormateur(Integer identrepriseformateur) {
        this.identrepriseformateur = identrepriseformateur;
    }

    public EntrepriseFormateur(Integer identrepriseformateur, Formateur formateur, Entreprise entreprise) {
        this.identrepriseformateur = identrepriseformateur;
        this.formateur = formateur;
        this.entreprise = entreprise;
    }

    @Id
    @GeneratedValue(generator = "SEQ_IDENTREPRISEFORMATEUR", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "SEQ_IDENTREPRISEFORMATEUR", sequenceName = "SEQ_IDENTREPRISEFORMATEUR",allocationSize=1)
    @Column(name = "identrepriseformateur", unique = true, nullable = false, precision = 22, scale = 0)
    public Integer getIdentrepriseformateur() {
        return this.identrepriseformateur;
    }

    public void setIdentrepriseformateur(Integer identrepriseformateur) {
        this.identrepriseformateur = identrepriseformateur;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "IDFORMATEUR")
    public Formateur getFormateur() {
        return this.formateur;
    }

    public void setFormateur(Formateur formateur) {
        this.formateur = formateur;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "IDENTREPRISE")
    public Entreprise getEntreprise() {
        return this.entreprise;
    }

    public void setEntreprise(Entreprise entreprise) {
        this.entreprise = entreprise;
    }

}
