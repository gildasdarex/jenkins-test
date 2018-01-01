package com.pej.domains;
// Generated 30 oct. 2016 22:13:12 by Hibernate Tools 5.2.0.Beta1



import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * Antenne generated by hbm2java
 */
@Entity
@Table(name="ANTENNE"
)
public class Antenne  implements java.io.Serializable {


     private Integer idantenne;
     @JsonIgnore
     private Departement departement;
     private String libantenne;
     private String responsable;
     private String numresponsable;
    public Antenne() {
    }

	
    public Antenne(Integer idantenne) {
        this.idantenne = idantenne;
    }
    public Antenne(Integer idantenne, Departement departement, String libantenne, String responsable) {
       this.idantenne = idantenne;
       this.departement = departement;
       this.libantenne = libantenne;
       this.responsable = responsable;
    }
   
     @Id 

 	@GeneratedValue(generator = "SEQ_IDANTENNE", strategy = GenerationType.SEQUENCE)
  	@SequenceGenerator(name = "SEQ_IDANTENNE", sequenceName = "SEQ_IDANTENNE",allocationSize=1)
    @Column(name="IDANTENNE", unique=true, nullable=false, precision=22, scale=0)
    public Integer getIdantenne() {
        return this.idantenne;
    }
    
    public void setIdantenne(Integer idantenne) {
        this.idantenne = idantenne;
    }

@ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="CODEDEPARTEMENT")
    public Departement getDepartement() {
        return this.departement;
    }
    
    public void setDepartement(Departement departement) {
        this.departement = departement;
    }

    
    @Column(name="LIBANTENNE")
    public String getLibantenne() {
        return this.libantenne;
    }
    
    public void setLibantenne(String libantenne) {
        this.libantenne = libantenne;
    }

    
    @Column(name="RESPONSABLE")
    public String getResponsable() {
        return this.responsable;
    }
    
    public void setResponsable(String responsable) {
        this.responsable = responsable;
    }

    @Column(name="numresponsable")
	public String getNumresponsable() {
		return numresponsable;
	}


	public void setNumresponsable(String numresponsable) {
		this.numresponsable = numresponsable;
	}




}

