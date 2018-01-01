package com.pej.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.pej.domains.Formationbeneficaire;
import com.pej.domains.Candidat;

import java.util.List;

public interface CandidatRepository extends CrudRepository<Candidat, Integer>{
	
	@Query("select ca from Candidat ca where ca.idcandidat  not in (select fb.candidat.idcandidat from Formationbeneficaire fb where fb.formation.idformation=?1)" )
	Iterable<Candidat> getNotInFormationCandidat(Integer id);
	
	@Query("select ca from Candidat ca where ca.idcandidat  in (select fb.candidat.idcandidat from Formationbeneficaire fb where fb.formation.idformation=?1)" )
	Iterable<Candidat> getInFormationCandidat(Integer id);
	
	@Query("select ca from Candidat ca where ca.idcandidat  not in (select fb.candidat.idcandidat from Beneficiairecooperative fb where fb.cooperative.idgroupe=?1)" )
	Iterable<Candidat> getNotInCooperativeCandidat(Integer id);
	
	@Query("select ca from Candidat ca where ca.idcandidat  in (select fb.candidat.idcandidat from Beneficiairecooperative fb where fb.cooperative.idgroupe=?1)" )
	Iterable<Candidat> getInCooperativeCandidat(Integer id);

	@Query("select COUNT(ca) from Candidat ca where ca.idcandidat  in (select fb.candidat.idcandidat from Beneficiairecooperative fb where fb.cooperative.idgroupe=?1)" )
	long getCountInCooperativeCandidat(Integer id);

	@Query("select COUNT(ca) from Candidat ca " )
	long getTotal();


//	@Query("select ca from Candidat ca where ca.idcandidat  in (select fb.candidat.idcandidat from Beneficiairecooperative fb where fb.cooperative.idgroupe=?1)" )
//	Iterable<Candidat> getInCooperativeCandidatWithPage(Integer id, Pageable pageable);


	@Query("select ca from Candidat ca where ca.statutcandidat.id= ?1 and ca.age >= 18 and ca.age <=35  ")
	Iterable<Candidat> getEligibleCandidat(Integer statut);
	
	@Query("select ca from Candidat ca where ca.statutcandidat.id < 2 and ca.age >= 18 and ca.age <= 35  ")
	Iterable<Candidat> getEligibleCandidat();
	
	
	@Query("select ca from Candidat ca where ca.idcandidat  in (select pr.candidat.idcandidat from Presence pr where pr.formation.idformation=?1)" )
	//@Query("select ca from Candidat ca where ca.idcandidat  in (select fb.candidat.idcandidat from beneficiairecooperative fb where fb.cooperative.idgroupe=?1) and ca.idcandidat  not in (select pr.candidat.idcandidat from Presence pr where pr.formation.idformation=?2)" )
	Iterable<Candidat> getInPresenceCandidat(Integer id);
	
	//@Query("select ca from Candidat ca where ca.idcandidat  in (select fb.candidat.idcandidat from Formationbeneficaire fb where fb.formation.idformation=?1) and ca.idcandidat  not in (select pr.candidat.idcandidat from Presence pr where pr.formation.idformation=?1)" )
	@Query("select ca from Candidat ca where ca.idcandidat  in (select fb.candidat.idcandidat from Beneficiairecooperative fb"
        +" where fb.cooperative.idgroupe in (SELECT gr.idgroupe FROM Cooperative gr where gr.idgroupe in(select fc.cooperative.idgroupe from Formationcooperative fc WHERE fc.formation.idformation=?1)))" 
        +" and ca.idcandidat  not in (select pr.candidat.idcandidat from Presence pr where pr.formation.idformation=?1)" )
	Iterable<Candidat> getInAbsenceCandidat(Integer id);

	@Query("select ca from Candidat ca where ca.idcandidat  not in (select fb.candidat.idcandidat from Beneficiairecooperative fb)" )
	List<Candidat> getFreeCandidats();

	@Query("SELECT ca FROM Candidat ca")
	List<Candidat> findAllWithPAgination(Pageable pageable);

	@Query("SELECT distinct ca.arrondissement FROM Candidat ca")
	List<String> selectArrondissement();


	@Query("SELECT COUNT(ca) FROM Candidat ca")
	long getNumberOfData();
	


}
