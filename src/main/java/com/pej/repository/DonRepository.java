package com.pej.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.pej.domains.Candidat;
import com.pej.domains.Don;

public interface DonRepository  extends  CrudRepository <Don, Integer> {
	@Query("select do from Don do where do.candidat.idcandidat=?1" )
	Iterable<Don> getDonByCandidat(Integer id);
}
