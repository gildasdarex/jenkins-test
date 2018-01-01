package com.pej.repository;



import java.util.Date;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.pej.domains.Candidat;

public interface CandidatJpaRepo extends JpaRepository<Candidat, Integer> {
	@Query("select ca from Candidat ca where ca.idcandidat  not in (select fb.candidat.idcandidat from Formationbeneficaire fb where fb.formation.idformation=?1)" )
	Iterable<Candidat> getNotInFormationCandidat(Integer id);
	
	@Query("select ca from Candidat ca where ca.idcandidat  in (select fb.candidat.idcandidat from Formationbeneficaire fb where fb.formation.idformation=?1)" )
	Iterable<Candidat> getInFormationCandidat(Integer id);
	
	@Query("select ca from Candidat ca where ca.idcandidat  not in (select fb.candidat.idcandidat from Beneficiairecooperative fb where fb.cooperative.idgroupe=?1)" )
	Iterable<Candidat> getNotInCooperativeCandidat(Integer id);
	
	@Query("select ca from Candidat ca where ca.idcandidat  in (select fb.candidat.idcandidat from Beneficiairecooperative fb where fb.cooperative.idgroupe=?1)" )
	Iterable<Candidat> getInCooperativeCandidat(Integer id);
	
	@Query("select ca from Candidat ca where ca.statutcandidat.id=2 and ca.quartier.arrondissement.commune.codecommune=?1" )
	Page<Candidat> getEligibleCandidat(Pageable page, Integer codecommune);
	
	@Query("select ca from Candidat ca where ca.quartier.arrondissement.commune.codecommune=?1 or ca.statutcandidat.id=?2 or ca.nom=?4 or ca.prenom=?3 or ca.agent.nom=?5 " )
	Iterable<Candidat> getSearchCandidat(int commune, int statut, String nom, Date datenaissance, String agent);


}
