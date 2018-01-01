package com.pej.domains;
// Generated 23 janv. 2017 12:16:16 by Hibernate Tools 5.2.0.Beta1


import java.util.Date;
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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * Formationformateur generated by hbm2java
 */
@Entity
@Table(name = "FORMATIONFORMATEUR")
public class Formationformateur implements java.io.Serializable {

	private Integer idformationformateur;
	private Formateur formateur;
	private Fformation fformation;
	private Date dateajout;
	private Integer nbjours;

	public Formationformateur() {
	}

	public Formationformateur(Integer idformationformateur) {
		this.idformationformateur = idformationformateur;
	}

	public Formationformateur(Integer idformationformateur, Formateur formateur, Fformation fformation, Date dateajout,
			Integer nbjours) {
		this.idformationformateur = idformationformateur;
		this.formateur = formateur;
		this.fformation = fformation;
		this.dateajout = dateajout;
		this.nbjours = nbjours;
	}

	@Id
	@GeneratedValue(generator = "SEQ_IDFORMATIONFORMATEUR", strategy = GenerationType.SEQUENCE)
  	@SequenceGenerator(name = "SEQ_IDFORMATIONFORMATEUR", sequenceName = "SEQ_IDFORMATIONFORMATEUR",allocationSize=1)
	@Column(name = "IDFORMATIONFORMATEUR", unique = true, nullable = false, length = 20)
	public Integer getIdformationformateur() {
		return this.idformationformateur;
	}

	public void setIdformationformateur(Integer idformationformateur) {
		this.idformationformateur = idformationformateur;
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
	@JoinColumn(name = "IDFORMATION")
	public Fformation getFformation() {
		return this.fformation;
	}

	public void setFformation(Fformation fformation) {
		this.fformation = fformation;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "DATEAJOUT", length = 7)
	public Date getDateajout() {
		return this.dateajout;
	}

	public void setDateajout(Date dateajout) {
		this.dateajout = dateajout;
	}

	@Column(name = "NBJOURS", precision = 22, scale = 0)
	public Integer getNbjours() {
		return this.nbjours;
	}

	public void setNbjours(Integer nbjours) {
		this.nbjours = nbjours;
	}

}