package com.pej.repository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import com.pej.domains.Cooperative;
import com.pej.domains.Lot;

public interface CooperativeRepository extends CrudRepository<Cooperative, Integer> {
	@Query("SELECT co FROM Cooperative co WHERE co.lot.idlot is not null)" )
	Iterable<Cooperative> getNotInLotCooperative();

	@Query("SELECT co FROM Cooperative co WHERE co.lot.idlot is null)" )
	Iterable<Cooperative> getCooperativeNotInLot();
	
	@Query("SELECT co FROM Cooperative co WHERE co.lot.idlot =?1)" )
	Iterable<Cooperative> getInLotCooperative(Integer id);

	@Query("select co from Cooperative co where co.idgroupe  not in (select fc.cooperative.idgroupe from Formationcooperative fc where fc.formation.idformation=?1)" )
	Iterable<Cooperative> getNotInFormationCooperative(Integer id);
	
	@Query("select co from Cooperative co where co.idgroupe  in (select fc.cooperative.idgroupe from Formationcooperative fc where fc.formation.idformation=?1)" )
	Iterable<Cooperative> getInFormationCooperative(Integer id);
}
