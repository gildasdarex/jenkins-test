package com.pej.repository;

import org.springframework.data.repository.CrudRepository;

import com.pej.domains.Statutcandidat;

public interface StatutcandidatRepository extends  CrudRepository <Statutcandidat, Integer>{
  Statutcandidat findOneByIntitule(String intitule);
}
