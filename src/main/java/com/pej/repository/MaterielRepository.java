package com.pej.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.pej.domains.Materiel;

/**
 * Created by D O N A T I E N on 26/12/2016.
 */
public interface MaterielRepository extends CrudRepository<Materiel, Integer>{

}