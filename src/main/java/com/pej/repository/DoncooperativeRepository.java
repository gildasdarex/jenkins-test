package com.pej.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.pej.domains.DonCooperative;

public interface DoncooperativeRepository extends  CrudRepository <DonCooperative, Integer> {
	@Query("select do from DonCooperative do where do.cooperative.idgroupe=?1" )
	Iterable<DonCooperative> getDonByCopperative(Integer id);
}
