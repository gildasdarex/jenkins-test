package com.pej.domains;
// Generated 3 janv. 2017 23:38:37 by Hibernate Tools 5.2.0.Beta1


import java.util.*;
import javax.persistence.*;

import org.hibernate.annotations.Proxy;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.pej.domains.Formationformateur;

/**
 * Formateur generated by hbm2java
 */
@Entity
@Table(name = "FORMATEUR")
@Proxy(lazy = false)
public class Formateur implements java.io.Serializable {

    private Integer idformateur;
    private Cabinet cabinet;
    private String nom;
    private String prenom;
    private String email;
    private Date datenaissance;
    private String cardid;
    private String telephone;
    private String datecreation;
    private String userlogin;
    private String photolink;
    private String username;
    private String password;
    @JsonIgnore
    private List<Formation> formations = new ArrayList<Formation>();
    @JsonIgnore
    private List<Entreprise> entreprises = new ArrayList<Entreprise>();
    private Set<Formationformateur> formationformateurs = new HashSet<Formationformateur>(0);
    private List<Periode> periodesSuivi = new ArrayList<Periode>(0);
//    @JsonIgnore
//    private Set<EntrepriseFormateur> entrepriseFormateurs = new HashSet<EntrepriseFormateur>(0);
    @JsonIgnore
    private Set<Suivie> suivies = new HashSet<Suivie>(0);

    public Formateur() {
    }

    public Formateur(Integer idformateur) {
        this.idformateur = idformateur;
    }

    public Formateur(Integer idformateur, Cabinet cabinet, String nom, String prenom, Date datenaissance,
                     String cardid, String telephone, String datecreation, String userlogin, List<Formation> formations) {
        this.idformateur = idformateur;
        this.cabinet = cabinet;
        this.nom = nom;
        this.prenom = prenom;
        this.datenaissance = datenaissance;
        this.cardid = cardid;
        this.telephone = telephone;
        this.datecreation = datecreation;
        this.userlogin = userlogin;
        this.formations = formations;
    }

    @Id
    @GeneratedValue(generator = "SEQ_IDFORMATEUR", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "SEQ_IDFORMATEUR", sequenceName = "SEQ_IDFORMATEUR", allocationSize = 1)
    @Column(name = "IDFORMATEUR", unique = true, nullable = false, precision = 22, scale = 0)
    public Integer getIdformateur() {
        return this.idformateur;
    }

    public void setIdformateur(Integer idformateur) {
        this.idformateur = idformateur;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "IDCABINET")
    public Cabinet getCabinet() {
        return this.cabinet;
    }

    public void setCabinet(Cabinet cabinet) {
        this.cabinet = cabinet;
    }

    @Column(name = "NOM", length = 1024)
    public String getNom() {
        return this.nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    @Column(name = "PRENOM", length = 1024)
    public String getPrenom() {
        return this.prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    @Temporal(TemporalType.DATE)
    @Column(name = "DATENAISSANCE", length = 7)
    public Date getDatenaissance() {
        return this.datenaissance;
    }

    public void setDatenaissance(Date datenaissance) {
        this.datenaissance = datenaissance;
    }

    @Column(name = "CARDID", length = 1024)
    public String getCardid() {
        return this.cardid;
    }

    public void setCardid(String cardid) {
        this.cardid = cardid;
    }

    @Column(name = "TELEPHONE", length = 1024)
    public String getTelephone() {
        return this.telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    @Column(name = "DATECREATION", length = 20)
    public String getDatecreation() {
        return this.datecreation;
    }

    public void setDatecreation(String datecreation) {
        this.datecreation = datecreation;
    }

    @Column(name = "USERLOGIN", length = 1024)
    public String getUserlogin() {
        return this.userlogin;
    }

    public void setUserlogin(String userlogin) {
        this.userlogin = userlogin;
    }

    @Column(name = "PHOTOLINK")
    public String getPhotolink() {
        return this.photolink;
    }

    @Column(name = "USERNAME", unique = true, nullable = false)
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Column(name = "EMAIL", unique = true, nullable = false, length = 1024)
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Transient
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setPhotolink(String photolink) {
        this.photolink = photolink;
    }

    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinTable(name = "FORMATEUR_GIVE_FORMATION", joinColumns = {@JoinColumn(name = "IDFORMATEUR")}, inverseJoinColumns = {@JoinColumn(name = "IDFORMATION")})
    public List<Formation> getFormations() {
        return this.formations;
    }

    public void setFormations(List<Formation> formations) {
        this.formations = formations;
    }

    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinTable(name = "FORMATEUR_FOLLOW_ENTREPRISE", joinColumns = {@JoinColumn(name = "IDFORMATEUR")}, inverseJoinColumns = {@JoinColumn(name = "IDENTREPRISE")})
    public List<Entreprise> getEntreprises() {
        return this.entreprises;
    }

    public void setEntreprises(List<Entreprise> entreprises) {
        this.entreprises = entreprises;
    }


    @OneToMany(fetch = FetchType.LAZY, mappedBy = "formateur")
    public Set<Formationformateur> getFormationformateurs() {
        return this.formationformateurs;
    }

    public void setFormationformateurs(Set<Formationformateur> formationformateurs) {
        this.formationformateurs = formationformateurs;
    }

    @OneToMany(fetch=FetchType.LAZY, mappedBy="formateur")
    public List<Periode> getPeriodesSuivi() {
        return this.periodesSuivi;
    }

    public void setPeriodesSuivi(List<Periode> periodesSuivi) {
        this.periodesSuivi = periodesSuivi;
    }


//    @OneToMany(fetch = FetchType.LAZY, mappedBy = "formateur")
//    public Set<EntrepriseFormateur> getEntrepriseFormateurs() {
//        return this.entrepriseFormateurs;
//    }
//
//    public void setEntrepriseFormateurs(Set<EntrepriseFormateur> entrepriseFormateurs) {
//        this.entrepriseFormateurs = entrepriseFormateurs;
//    }

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "formateur")
    public Set<Suivie> getSuivies() {
        return this.suivies;
    }

    public void setSuivies(Set<Suivie> suivies) {
        this.suivies = suivies;
    }


    @Transient
    public String getIdentite(){
        return this.nom + " " + this.prenom + " ";
    }

    @Transient
    public String getImageName(){
        return this.nom + "_" + this.prenom;
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Formateur)) return false;
        Formateur formateur = (Formateur)obj;
        if(obj == this) return true;
        else if(this.getIdformateur().intValue() == formateur.getIdformateur().intValue()) return true;
        else return false;
    }
}