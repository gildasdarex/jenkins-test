package com.pej.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.pej.domains.Beneficiairecooperative;
import com.pej.domains.Formationbeneficaire;

import java.util.List;

public interface BeneficiaireCooperativeRepository extends  CrudRepository <Beneficiairecooperative, Integer>{
	@Query("select bc from Beneficiairecooperative bc  where bc.candidat.idcandidat=?1 AND bc.cooperative.idgroupe=?2" )
	Beneficiairecooperative findBc(Integer idcandidat, Integer idgroupe);
	List<Beneficiairecooperative> findByCooperativeIdgroupe(Integer idgroupe);
	List<Beneficiairecooperative> findByCandidatIdcandidat(Integer idcandidat);
	@Query("select bc.candidat.id from Beneficiairecooperative bc" )
	List<Integer> getIdOfNotFreeCandidat();
}
