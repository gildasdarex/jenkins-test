package com.pej.domains;
// Generated 30 oct. 2016 22:13:12 by Hibernate Tools 5.2.0.Beta1



import java.util.HashSet;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

/**
 * Departement generated by hbm2java
 */
@Entity
@Table(name="DEPARTEMENT"
)
public class Departement  implements java.io.Serializable {


     private Integer codedepartement;
     private String libdeparteement;
     private String description;
     private Set<Antenne> antennes = new HashSet<Antenne>(0);
     private Set<Commune> communes = new HashSet<Commune>(0);

    public Departement() {
    }

	
    public Departement(Integer codedepartement) {
        this.codedepartement = codedepartement;
    }
    public Departement(Integer codedepartement, String libdeparteement, String description, Set<Antenne> antennes, Set<Commune> communes) {
       this.codedepartement = codedepartement;
       this.libdeparteement = libdeparteement;
       this.description = description;
       this.antennes = antennes;
       this.communes = communes;
    }
   
    @Id 
    @GeneratedValue(generator = "SEC_DEPARTEMENT", strategy = GenerationType.SEQUENCE)
 	@SequenceGenerator(name = "SEC_DEPARTEMENT", sequenceName = "SEQ_IDDEPARTEMENT",allocationSize=1)
    @Column(name="CODEDEPARTEMENT", unique=true, nullable=false, precision=22, scale=0)
    public Integer getCodedepartement() {
        return this.codedepartement;
    }
    
    public void setCodedepartement(Integer codedepartement) {
        this.codedepartement = codedepartement;
    }

    
    @Column(name="LIBDEPARTEEMENT", length=256)
    public String getLibdeparteement() {
        return this.libdeparteement;
    }
    
    public void setLibdeparteement(String libdeparteement) {
        this.libdeparteement = libdeparteement;
    }

    
    @Column(name="DESCRIPTION", length=256)
    public String getDescription() {
        return this.description;
    }
    
    public void setDescription(String description) {
        this.description = description;
    }

    @OneToMany(fetch=FetchType.LAZY, mappedBy="departement")
    public Set<Antenne> getAntennes() {
        return this.antennes;
    }
    
    public void setAntennes(Set<Antenne> antennes) {
        this.antennes = antennes;
    }

    @OneToMany(fetch=FetchType.LAZY, mappedBy="departement")
    public Set<Commune> getCommunes() {
        return this.communes;
    }
    
    public void setCommunes(Set<Commune> communes) {
        this.communes = communes;
    }

}


