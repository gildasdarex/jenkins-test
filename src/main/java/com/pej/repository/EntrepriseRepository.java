package com.pej.repository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.pej.domains.Entreprise;

import java.util.List;

@Repository
public interface EntrepriseRepository extends CrudRepository<Entreprise, Integer>{

    List<Entreprise> findByCandidatIdcandidat(Integer idCandidat);
}
