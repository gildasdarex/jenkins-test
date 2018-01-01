package com.pej.domains;


import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Proxy;

import javax.persistence.*;

/**
 * Created by D O N A T I E N on 26/12/2016.
 */
@Entity
@Table(name = "FICHEFINANCEMENT")
@Proxy(lazy=false)
public class Fichefinancement implements java.io.Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 1L;
    private Integer idfichefinancement;
    @JsonIgnore
    private Materiel materiel;
    @JsonIgnore
    private Fournisseur fournisseur;
    @JsonIgnore
    private Candidat candidat;
    private Integer prixunitaire;
    private Integer quantite;

    public Fichefinancement() {
    }

    public Fichefinancement(Integer idfichefinancement) {
        this.idfichefinancement = idfichefinancement;
    }

    public Fichefinancement(Integer idfichefinancement, Materiel materiel, Fournisseur fournisseur, Candidat candidat, Integer prixunitaire, Integer quantite) {
        this.idfichefinancement = idfichefinancement;
        this.materiel = materiel;
        this.fournisseur = fournisseur;
        this.candidat = candidat;
        this.prixunitaire = prixunitaire;
        this.quantite = quantite;
    }

    @Id
    @GeneratedValue(generator = "SEQ_IDFICHEFINANCEMENT", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "SEQ_IDFICHEFINANCEMENT", sequenceName = "SEQ_IDFICHEFINANCEMENT",allocationSize=1)
    @Column(name = "idfichefinancement", unique = true, nullable = false, precision = 22, scale = 0)
    public Integer getIdfichefinancement() {
        return this.idfichefinancement;
    }

    public void setIdfichefinancement(Integer idfichefinancement) {
        this.idfichefinancement = idfichefinancement;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "IDMATERIEL")
    public Materiel getMateriel() {
        return this.materiel;
    }

    public void setMateriel(Materiel materiel) {
        this.materiel = materiel;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "IDFOURNISSEUR")
    public Fournisseur getFournisseur() {
        return this.fournisseur;
    }

    public void setFournisseur(Fournisseur fournisseur) {
        this.fournisseur = fournisseur;
    }
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "IDCANDIDAT")
    public Candidat getCandidat() {
        return candidat;
    }

    public void setCandidat(Candidat candidat) {
        this.candidat = candidat;
    }

    public Integer getPrixunitaire() {
        return prixunitaire;
    }

    public void setPrixunitaire(Integer prixunitaire) {
        this.prixunitaire = prixunitaire;
    }

    public Integer getQuantite() {
        return quantite;
    }

    public void setQuantite(Integer quantite) {
        this.quantite = quantite;
    }
}

