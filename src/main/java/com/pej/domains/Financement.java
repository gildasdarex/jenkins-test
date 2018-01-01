package com.pej.domains;


import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Proxy;

import javax.persistence.*;

/**
 * Created by D O N A T I E N on 26/12/2016.
 */
@Entity
@Table(name = "FINANCEMENT")
@Proxy(lazy=false)
public class Financement implements java.io.Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 1L;
    private Integer idfichefinancement;
    private String materiel;
    private String fournisseur;
    private String numeroFournisseur;
    private Integer idCandidat;
    private Integer prixUnitaire;
    private Integer quantite;
    private Double total;

    public Financement() {
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

    @Column(name="MATERIEL", length=1024)
    public String getMateriel() {
        return materiel;
    }

    public void setMateriel(String materiel) {
        this.materiel = materiel;
    }

    @Column(name="FOURNISSEUR", length=1024)
    public String getFournisseur() {
        return fournisseur;
    }

    public void setFournisseur(String fournisseur) {
        this.fournisseur = fournisseur;
    }

    @Column(name="NUMFOURNISSEUR", length=1024)
    public String getNumeroFournisseur() {
        return numeroFournisseur;
    }

    public void setNumeroFournisseur(String numeroFournisseur) {
        this.numeroFournisseur = numeroFournisseur;
    }


    public Integer getIdCandidat() {
        return idCandidat;
    }

    public void setIdCandidat(Integer idCandidat) {
        this.idCandidat = idCandidat;
    }

    public Integer getPrixUnitaire() {
        return prixUnitaire;
    }

    public void setPrixUnitaire(Integer prixUnitaire) {
        this.prixUnitaire = prixUnitaire;
    }

    public Integer getQuantite() {
        return quantite;
    }

    public void setQuantite(Integer quantite) {
        this.quantite = quantite;
    }

    @Transient
    public Double getTotal() {
        return Double.valueOf(prixUnitaire*quantite);
    }

}

