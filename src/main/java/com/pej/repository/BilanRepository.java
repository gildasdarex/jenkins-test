package com.pej.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.pej.domains.*;

import java.util.List;

public interface BilanRepository extends CrudRepository<Bilan, Integer>{
//	@Query("SELECT bi FROM Bilan bi WHERE bi.cooperative.idgroupe=?1)" )
//	Iterable<Bilan> getCooperativeBilan(Integer id);

	Iterable<Bilan> findByFormationIdformation(Integer idFormation);
}
