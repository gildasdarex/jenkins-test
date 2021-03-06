package com.pej.domains;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

/**
 * Cabinet generated by hbm2java
 */
@Entity
@Table(name = "PARAMETRES")
public class Parametres {
	private Integer idparametres;
	private String picturepath;
	
	@Id
	@GeneratedValue(generator = "SEQ_IDCABINET", strategy = GenerationType.SEQUENCE)
	@SequenceGenerator(name = "SEQ_IDCABINET", sequenceName = "SEQ_IDCABINET",allocationSize=1)
	 @Column(name="IDPARAMETRES", unique=true, nullable=false, precision=22, scale=0)
	public Integer getIdparametres() {
		return idparametres;
	}
	
	
	public void setIdparametres(Integer idparametres) {
		this.idparametres = idparametres;
	}
	
	@Column(name = "PICTUREPATH")
	public String getPicturepath() {
		return picturepath;
	}
	public void setPicturepath(String picturepath) {
		this.picturepath = picturepath;
	}
	
	
}
