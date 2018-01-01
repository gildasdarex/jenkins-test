package com.pej.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.pej.domains.Formationbeneficaire;

import java.util.List;

public interface FormationBeneficiareRepository extends  CrudRepository <Formationbeneficaire, Integer>{
	
	@Query("select fb from Formationbeneficaire fb  where fb.candidat.idcandidat=?1 AND fb.formation.idformation=?2" )
	Formationbeneficaire findFb(Integer idcandidat, Integer idformation);
	List<Formationbeneficaire> findByFormationIdformation(Integer idformation);
}
