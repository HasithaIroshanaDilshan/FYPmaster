package com.server.master.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.server.master.models.Keystrokedata;


public interface KeystrokedataRepository extends CrudRepository<Keystrokedata, Integer> {
	
	
	@Query("SELECT u FROM Keystrokedata u WHERE u.id = ?1")
	Keystrokedata getallData(Integer id);
}
