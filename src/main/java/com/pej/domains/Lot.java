package com.pej.domains;
// Generated 28 d�c. 2016 15:20:33 by Hibernate Tools 5.2.0.Beta1


import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

/**
 * Lot generated by hbm2java
 */
@Entity
@Table(name = "LOT")
public class Lot implements java.io.Serializable {

	private Integer idlot;
	private Cabinet cabinet;
	private String intitule;
	private String description;
	private String commune;
	private Set<Cooperative> cooperatives = new HashSet<Cooperative>(0);
	private Set<Formationlot> formationlots = new HashSet<Formationlot>(0);
	public Lot() {
	}

	public Lot(Integer idlot) {
		this.idlot = idlot;
	}

	public Lot(Integer idlot, Cabinet cabinet, String intitule, String description) {
		this.idlot = idlot;
		this.cabinet = cabinet;
		this.intitule = intitule;
		this.description = description;
	}

	@Id
 	@GeneratedValue(generator = "SEQ_IDLOT", strategy = GenerationType.SEQUENCE)
 	@SequenceGenerator(name = "SEQ_IDLOT", sequenceName = "SEQ_IDLOT",allocationSize=1)
	@Column(name = "IDLOT", unique = true, nullable = false, precision = 22, scale = 0)
	public Integer getIdlot() {
		return this.idlot;
	}

	public void setIdlot(Integer idlot) {
		this.idlot = idlot;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "IDCABINET")
	public Cabinet getCabinet() {
		return this.cabinet;
	}

	public void setCabinet(Cabinet cabinet) {
		this.cabinet = cabinet;
	}

	@Column(name = "INTITULE", length = 100)
	public String getIntitule() {
		return this.intitule;
	}

	public void setIntitule(String intitule) {
		this.intitule = intitule;
	}

	@Column(name = "DESCRIPTION")
	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
	@OneToMany(fetch=FetchType.LAZY, mappedBy="lot")
	public Set<Cooperative> getCooperatives() {
		return cooperatives;
	}

	public void setCooperatives(Set<Cooperative> cooperatives) {
		this.cooperatives = cooperatives;
	}
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "lot")
	public Set<Formationlot> getFormationlots() {
		return this.formationlots;
	}

	public void setFormationlots(Set<Formationlot> formationlots) {
		this.formationlots = formationlots;
	}
	
	@Column(name = "COMMUNE")
	public String getCommune() {
		return commune;
	}

	public void setCommune(String commune) {
		this.commune = commune;
	}

	@Override
	public String toString() {
		return "Lot [idlot=" + idlot + ", cabinet=" + cabinet + ", intitule=" + intitule + ", description="
				+ description + ", cooperatives=" + cooperatives + "]";
	}
	
	
}
