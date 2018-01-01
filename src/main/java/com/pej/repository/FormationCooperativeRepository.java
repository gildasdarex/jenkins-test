package com.pej.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.pej.domains.Beneficiairecooperative;
import com.pej.domains.Formationcooperative;

import java.util.List;

public interface FormationCooperativeRepository extends CrudRepository<Formationcooperative, Integer> {
	@Query("select fb from Formationcooperative fb  where fb.cooperative.idgroupe=?1 AND fb.formation.idformation=?2" )
	Formationcooperative findFb(Integer idgroupe, Integer idformation);

	List<Formationcooperative> findByFormationIdformation(Integer idformation);
	List<Formationcooperative> findByCooperativeIdgroupe(Integer idgroupe);
}
