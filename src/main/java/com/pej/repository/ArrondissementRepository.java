package com.pej.repository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import com.pej.domains.Arrondissement;

@Repository
public interface ArrondissementRepository extends CrudRepository<Arrondissement, Integer>{

}
