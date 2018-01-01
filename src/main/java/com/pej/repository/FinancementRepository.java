package com.pej.repository;

import com.pej.domains.Financement;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface FinancementRepository extends CrudRepository<Financement, Integer>{
//	@Query("SELECT bi FROM Bilan bi WHERE bi.cooperative.idgroupe=?1)" )
	List<Financement> findByIdCandidat(Integer idCandidat);
}
