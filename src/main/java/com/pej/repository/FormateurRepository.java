package com.pej.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.pej.domains.Formateur;
import java.util.List;

public interface FormateurRepository extends  CrudRepository <Formateur, Integer> {
	@Query("select fo from Formateur fo where fo.idformateur  not in (select ff.formateur.idformateur from Formationformateur ff where ff.fformation.idformation=?1)" )
	Iterable<Formateur> getNotInFormationformateur(Integer id);
	@Query("select fo from Formateur fo  where fo.idformateur  in (select ff.formateur.idformateur from Formationformateur ff where ff.fformation.idformation=?1)" )
	Iterable<Formateur> getInFormationformateur(Integer id);
	@Query("select f from Formateur f  where f.nom=?1 and f.prenom=?2" )
	Formateur findByFormateur(String nom,String prenom);
	Formateur findOneByUsername(String username);
	List<Formateur> findByCabinetIdcabinet(Integer idcabinet);
	 
}
