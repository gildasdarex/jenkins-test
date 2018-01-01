package com.pej.repository;

import com.pej.domains.PeriodeSuivi;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PeriodeSuiviRepository extends CrudRepository<PeriodeSuivi, Integer>{

}
