package com.pej.utils;
import java.util.List;

import com.pej.domains.Candidat;
public class Candidats {
	
	
	public Candidats(List<Candidat> postulant) {
		super();
		this.postulant = postulant;
	}
	public Candidats() {
		super();
	}

	private List<Candidat> postulant;

	public List<Candidat> getPostulant() {
		return postulant;
	}

	public void setPostulant(List<Candidat> postulant) {
		this.postulant = postulant;
	}
	
	
}
