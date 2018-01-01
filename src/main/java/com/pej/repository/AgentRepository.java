/**
 * 
 */
package com.pej.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.pej.domains.Agent;
import com.pej.domains.Commune;

public interface AgentRepository extends  CrudRepository <Agent, Integer> {
	
	@Query("select c  from Agent c where c.numeroagent=?1" )
	Agent getAgent( String numeroagent	);

}
