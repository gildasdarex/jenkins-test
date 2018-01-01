package com.pej.domains;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Proxy;

import javax.persistence.*;
import java.util.*;

@Entity
@Table(name = "periode")
@Proxy(lazy=false)
public class Periode implements java.io.Serializable {

    private Integer idperiode;
    private Date dateDebut;
    private Date datefin;
    private Entreprise entreprise;
    private Formateur formateur;
    private Set<Suivie> suivies = new HashSet<Suivie>(0);

    public Periode() {
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

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="IDENTREPRISE")
    public Entreprise getEntreprise() {
        return entreprise;
    }

    public void setEntreprise(Entreprise entreprise) {
        this.entreprise = entreprise;
    }

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="IDFORMATEUR")
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

    @Transient
    public List<Date> getSuiviDates(){
        Calendar start = Calendar.getInstance();
        List<Date> dates = new ArrayList<>();
        start.setTime(this.dateDebut);

        dates.add(start.getTime());

        while (start.getTime().before(this.datefin)) {
            start.add(Calendar.DATE, 1);
            dates.add(start.getTime());
        }

        return dates;
    }

    @OneToMany(fetch=FetchType.LAZY, mappedBy="periode")
    public Set<Suivie> getSuivies() {
        return this.suivies;
    }

    public void setSuivies(Set<Suivie> suivies) {
        this.suivies = suivies;
    }



//    private Integer idperiode;
//    private String nomperiode;
//    private String type;
//    private Integer nombre;
//    @JsonIgnore
//    private Set<Suivie> suivies = new HashSet<Suivie>(0);
//
//    public Periode() {
//    }
//
//    public Periode(String nomperiode,String type, Integer nombre ) {
//        this.nomperiode = nomperiode;
//        this.type = type;
//        this.nombre = nombre;
//    }
//
//    @Id
//    @GeneratedValue(generator = "SEQ_IDPERIODE", strategy = GenerationType.SEQUENCE)
//    @SequenceGenerator(name = "SEQ_IDPERIODE", sequenceName = "SEQ_IDPERIODE",allocationSize=1)
//
//    @Column(name="idperiode", unique=true, nullable=false, length=20)
//    public Integer getIdperiode() {
//        return this.idperiode;
//    }
//
//    public void setIdperiode(Integer idperiode) {
//        this.idperiode = idperiode;
//    }
//
//    @Column(name = "nomperiode", length = 255)
//    public String getNomperiode() {
//        return this.nomperiode;
//    }
//
//    public void setNomperiode(String nomperiode) {
//        this.nomperiode = nomperiode;
//    }
//
//    public String getType() {
//        return type;
//    }
//
//    public void setType(String type) {
//        this.type = type;
//    }
//
//    public Integer getNombre() {
//        return nombre;
//    }
//
//    public void setNombre(Integer nombre) {
//        this.nombre = nombre;
//    }
//    @OneToMany(fetch=FetchType.LAZY, mappedBy="periode")
//    public Set<Suivie> getSuivies() {
//        return this.suivies;
//    }
//
//    public void setSuivies(Set<Suivie> suivies) {
//        this.suivies = suivies;
//    }
//
//    @Override
//    public String toString() {
//        return "" +idperiode;
//    }
}