package com.pej.repository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.pej.domains.Annees;
@Repository
public interface AnneesRepository extends CrudRepository<Annees, Integer>{

}
