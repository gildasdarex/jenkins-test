package com.pej.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.pej.domains.Lot;

public interface LotRepository extends CrudRepository <Lot, Integer>{

	
}
