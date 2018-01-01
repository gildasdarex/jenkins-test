package com.pej.domains;
// Generated 7 janv. 2017 00:38:44 by Hibernate Tools 5.2.0.Beta1


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
 * Formationlot generated by hbm2java
 */
@Entity
@Table(name = "FORMATIONLOT")
public class Formationlot implements java.io.Serializable {

	private Integer idformationlot;
	private Formation formation;
	private Lot lot;
	private Date dateajout;
	private String userlogin;

	public Formationlot() {
	}

	public Formationlot(Integer idformationlot) {
		this.idformationlot = idformationlot;
	}

	public Formationlot(Integer idformationlot, Formation formation, Lot lot, Date dateajout, String userlogin) {
		this.idformationlot = idformationlot;
		this.formation = formation;
		this.lot = lot;
		this.dateajout = dateajout;
		this.userlogin = userlogin;
	}

	@Id
	@GeneratedValue(generator = "SEQ_IDLOT", strategy = GenerationType.SEQUENCE)
  	@SequenceGenerator(name = "SEQ_IDLOT", sequenceName = "SEQ_IDLOT",allocationSize=1)
	@Column(name = "IDFORMATIONLOT", unique = true, nullable = false, precision = 22, scale = 0)
	public Integer getIdformationlot() {
		return this.idformationlot;
	}

	public void setIdformationlot(Integer idformationlot) {
		this.idformationlot = idformationlot;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "IDFORMATION")
	public Formation getFormation() {
		return this.formation;
	}

	public void setFormation(Formation formation) {
		this.formation = formation;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "IDLOT")
	public Lot getLot() {
		return this.lot;
	}

	public void setLot(Lot lot) {
		this.lot = lot;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "DATEAJOUT", length = 7)
	public Date getDateajout() {
		return this.dateajout;
	}

	public void setDateajout(Date dateajout) {
		this.dateajout = dateajout;
	}

	@Column(name = "USERLOGIN", length = 20)
	public String getUserlogin() {
		return this.userlogin;
	}

	public void setUserlogin(String userlogin) {
		this.userlogin = userlogin;
	}

}