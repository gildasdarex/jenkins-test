package com.pej.domains;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Proxy;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by D O N A T I E N on 31/12/2016.
 */
@Entity
@Table(name = "fournisseur")
@Proxy(lazy=false)
public class Fournisseur implements java.io.Serializable {

    private Integer idfournisseur;
    private String nomfournisseur;
    private String contactfournisseur;
    @JsonIgnore
    private Set<Fichefinancement> fichefinancements = new HashSet<Fichefinancement>(0);

    public Fournisseur() {
    }

    public Fournisseur(Integer idfournisseur,String nomfournisseur,String contactfournisseur) {
        this.idfournisseur=idfournisseur;
        this.nomfournisseur = nomfournisseur;
        this.contactfournisseur=contactfournisseur;
    }

    @Id
    @GeneratedValue(generator = "SEQ_IDFOURNISSEUR", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "SEQ_IDFOURNISSEUR", sequenceName = "SEQ_IDFOURNISSEUR",allocationSize=1)

    @Column(name="idfournisseur", unique=true, nullable=false, length=20)
    public Integer getIdfournisseur() {
        return this.idfournisseur;
    }

    public void setIdfournisseur(Integer idfournisseur) {
        this.idfournisseur = idfournisseur;
    }

    @Column(name = "nomfournisseur", length = 255)
    public String getNomfournisseur() {
        return this.nomfournisseur;
    }

    public void setNomfournisseur(String nomfournisseur) {
        this.nomfournisseur = nomfournisseur;
    }
    @Column(name = "contactfournisseur", length = 255)
    public String getContactfournisseur() {
        return contactfournisseur;
    }

    public void setContactfournisseur(String contactfournisseur) {
        this.contactfournisseur = contactfournisseur;
    }

    @OneToMany(fetch=FetchType.LAZY, mappedBy="fournisseur")
    public Set<Fichefinancement> getFichefinancements() {
        return this.fichefinancements;
    }

    public void setFichefinancements(Set<Fichefinancement> fichefinancements) {
        this.fichefinancements = fichefinancements;
    }

    @Override
    public String toString() {
        return ""+idfournisseur;
    }
}
