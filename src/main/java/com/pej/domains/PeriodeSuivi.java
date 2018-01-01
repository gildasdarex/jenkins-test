package com.pej.domains;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Proxy;

import javax.persistence.*;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "periode")
@Proxy(lazy=false)
public class PeriodeSuivi implements java.io.Serializable {

    private Integer idperiode;
    private Date dateDebut;
    private Date datefin;
    private Entreprise entreprise;
    private Formateur formateur;

    public PeriodeSuivi() {
    }


    @Id
    @GeneratedValue(generator = "SEQ_IDPERIODE", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "SEQ_IDPERIODE", sequenceName = "SEQ_IDPERIODE",allocationSize=1)

    @Column(name="idperiode", unique=true, nullable=false, length=20)
    public Integer getIdperiode() {
        return this.idperiode;
    }

    public void setIdperiode(Integer idperiode) {
        this.idperiode = idperiode;
    }

    @Temporal(TemporalType.DATE)
    @Column(name = "DATEDEBUT", length = 7)
    public Date getDateDebut() {
        return this.dateDebut;
    }

    public void setDateDebut(Date dateDebut) {
        this.dateDebut = dateDebut;
    }

    @Temporal(TemporalType.DATE)
    @Column(name = "DATEFIN", length = 7)
    public Date getDatefin() {
        return this.datefin;
    }

    public void setDatefin(Date datefin) {
        this.datefin = datefin;
    }

    public Entreprise getEntreprise() {
        return entreprise;
    }

    public void setEntreprise(Entreprise entreprise) {
        this.entreprise = entreprise;
    }

    public Formateur getFormateur() {
        return formateur;
    }

    public void setFormateur(Formateur formateur) {
        this.formateur = formateur;
    }

    @Override
    public String toString() {
        return "" +idperiode;
    }
}