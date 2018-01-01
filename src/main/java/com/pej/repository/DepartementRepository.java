package com.pej.repository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import com.pej.domains.Departement;

@Repository
public interface DepartementRepository  extends CrudRepository<Departement, Integer> {

}

