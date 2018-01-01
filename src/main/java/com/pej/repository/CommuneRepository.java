package com.pej.repository;
import java.util.ArrayList;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import com.pej.domains.Commune;

@Repository
public interface CommuneRepository  extends CrudRepository<Commune, Integer>{
	// liste des rôles d'un utilisateur identifié par son id
	@Query("select c  from Commune c where c.departement.codedepartement=?1" )
	 Iterable<Commune> getCommune( Integer id);
	 @Query("select c  from Commune c where c.departement.codedepartement=?1" )
	    ArrayList<Commune> getCommunes(Integer id);
	 Commune findOneByLibcommune(String commune);
	
}
