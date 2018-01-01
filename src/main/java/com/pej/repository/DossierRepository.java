package com.pej.repository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.pej.domains.Dossiers;
public interface DossierRepository extends CrudRepository<Dossiers, Integer> {
	@Query("select do from Dossiers do where do.candidat.idcandidat= ?1)" )
	Iterable<Dossiers> getDossierCandidat(Integer id);
}
