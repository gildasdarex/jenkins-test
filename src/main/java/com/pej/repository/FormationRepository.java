package com.pej.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

import com.pej.domains.Formation;

import java.util.List;

public interface FormationRepository extends JpaRepository<Formation, Integer> {

}
