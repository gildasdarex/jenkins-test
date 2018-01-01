package com.pej.domains;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import org.hibernate.annotations.Proxy;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
@Entity
@Table(name="ENTREPRISE")
@Proxy(lazy=false)
public class Entreprise implements java.io.Serializable {


    /**
     *
     */
    private static final long serialVersionUID = 1L;
    private Integer identreprise;
    @JsonManagedReference
    private Quartier quartier;
    @JsonManagedReference
    private Candidat candidat;
    private String raisonsocial;
    private String numeroifu;
    private String contact;
    private String longitude;
    private String latitude;
    private String indication;
    private String typeentreprise;
    private List<Formateur> formateurs = new ArrayList<Formateur>();
    private List<Periode> periodesSuivi = new ArrayList<Periode>(0);
    @JsonIgnore
    private Set<Suivie> suivies = new HashSet<Suivie>(0);

    public Entreprise() {
    }


    public Entreprise(Integer identreprise) {
        this.identreprise = identreprise;
    }
    public Entreprise(Integer identreprise, Quartier quartier, String raisonsocial, String numeroifu,String contact,String typeentreprise,Candidat candidat) {
        this.identreprise = identreprise;
        this.quartier = quartier;
        this.candidat = candidat;
        this.raisonsocial = raisonsocial;
        this.numeroifu = numeroifu;
        this.contact = contact;
        this.typeentreprise=typeentreprise;
    }

    @Id
    @GeneratedValue(generator = "SEQ_IDENTREPRISE", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "SEQ_IDENTREPRISE", sequenceName = "SEQ_IDENTREPRISE",allocationSize=1)
    @Column(name="IDENTREPRISE", unique=true, nullable=false, precision=22, scale=0)
    public Integer getIdentreprise() {
        return this.identreprise;
    }

    public void setIdentreprise(Integer identreprise) {
        this.identreprise = identreprise;
    }

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="IDQUARTIER")
    @JsonManagedReference
    public Quartier getQuartier() {
        return this.quartier;
    }

    public void setQuartier(Quartier quartier) {
        this.quartier = quartier;
    }
    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="IDCANDIDAT")
    @JsonManagedReference
    public Candidat getCandidat() {
        return candidat;
    }

    public void setCandidat(Candidat candidat) {
        this.candidat = candidat;
    }

    @Column(name="CONTACT", length=255)
    public String getContact() {
        return this.contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    @Column(name="RAISONSOCIALE", length=255)
    public String getRaisonsocial() {
        return this.raisonsocial;
    }

    public void setRaisonsocial(String raisonsocial) {
        this.raisonsocial = raisonsocial;
    }


    @Column(name="NUMEROIFU", length=256)
    public String getNumeroifu() {
        return this.numeroifu;
    }

    public void setNumeroifu(String numeroifu) {
        this.numeroifu = numeroifu;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public String getTypeentreprise() {
        return typeentreprise;
    }

    public void setTypeentreprise(String typeentreprise) {
        this.typeentreprise = typeentreprise;
    }

    @OneToMany(fetch=FetchType.LAZY, mappedBy="entreprise")
    public List<Periode> getPeriodesSuivi() {
        return this.periodesSuivi;
    }

    public void setPeriodesSuivi(List<Periode> periodesSuivi) {
        this.periodesSuivi = periodesSuivi;
    }


//    @OneToMany(fetch=FetchType.LAZY, mappedBy="entreprise")
//    public Set<EntrepriseFormateur> getEntrepriseFormateurs() {
//        return this.entrepriseFormateurs;
//    }
//
//    public void setEntrepriseFormateurs(Set<EntrepriseFormateur> entrepriseFormateurs) {
//        this.entrepriseFormateurs = entrepriseFormateurs;
//    }
    @OneToMany(fetch=FetchType.LAZY, mappedBy="entreprise")
    public Set<Suivie> getSuivies() {
        return this.suivies;
    }

    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "entreprises")
    public List<Formateur> getFormateurs() {
        return this.formateurs;
    }

    public void setFormateurs(List<Formateur> formateurs) {
        this.formateurs = formateurs;
    }


    public void setSuivies(Set<Suivie> suivies) {
        this.suivies = suivies;
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

    public String getIndication() {
        return indication;
    }

    public void setIndication(String indication) {
        this.indication = indication;
    }

    @Override
    public String toString() {
        return  raisonsocial ;
    }
}