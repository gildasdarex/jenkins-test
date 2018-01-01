package com.pej.domains;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Proxy;

import javax.persistence.*;
import java.util.Date;
@Entity
@Table(name = "SUIVIE")
@Proxy(lazy=false)
public class Suivie implements java.io.Serializable {


    private static final long serialVersionUID = 1L;
    private Integer idsuivie;
    private Date datedebut;
    private Date datefin;
    @JsonIgnore
    private Formateur formateur;
    @JsonIgnore
    private Entreprise entreprise;
    private  String statut;
    @JsonIgnore
    private Annees annees;
    @JsonIgnore
    private Periode periode;
    private String theme;
    private String observation;
    private String duree;
    private Date date;
    private String longitude;
    private String latitude;

    public Suivie() {
    }

    public Suivie(Integer idsuivie) {
        this.idsuivie = idsuivie;
    }

    public Suivie(Integer idsuivie,Date datedebut,Date datefin, Formateur formateur, Entreprise entreprise,String statut,Annees annees,Periode periode) {
        this.idsuivie = idsuivie;
        this.datedebut = datedebut;
        this.datefin = datefin;
        this.formateur = formateur;
        this.entreprise = entreprise;
        this.statut=statut;
        this.annees = annees;
        this.periode=periode;
    }

    @Id
    @GeneratedValue(generator = "SEQ_IDSUIVIE", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "SEQ_IDSUIVIE", sequenceName = "SEQ_IDSUIVIE",allocationSize=1)
    @Column(name = "idsuivie", unique = true, nullable = false, precision = 22, scale = 0)
    public Integer getIdsuivie() {
        return this.idsuivie;
    }

    public void setIdsuivie(Integer idsuivie) {
        this.idsuivie = idsuivie;
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
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "IDANNEES")
    public Annees getAnnees() {
        return this.annees;
    }

    public void setPeriode(Periode periode) {
        this.periode = periode;
    }
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "IDPERIODE")
    public Periode getPeriode() {
        return this.periode;
    }

    public void setAnnees(Annees annees) {
        this.annees = annees;
    }

    public Date getDatedebut() {
        return datedebut;
    }

    public void setDatedebut(Date datedebut) {
        this.datedebut = datedebut;
    }

    public Date getDatefin() {
        return datefin;
    }

    public void setDatefin(Date datefin) {
        this.datefin = datefin;
    }

    public String getStatut() {
        return statut;
    }

    public void setStatut(String statut) {
        this.statut = statut;
    }

    public String getObservation() {
        return observation;
    }

    public void setObservation(String observation) {
        this.observation = observation;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getDuree() {
        return duree;
    }

    public void setDuree(String duree) {
        this.duree = duree;
    }

    public String getTheme() {
        return theme;
    }

    public void setTheme(String theme) {
        this.theme = theme;
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
}
