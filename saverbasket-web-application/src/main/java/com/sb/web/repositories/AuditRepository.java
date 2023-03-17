package com.sb.web.repositories;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.sb.web.entities.Audit;

public interface AuditRepository extends CrudRepository<Audit, Integer> {	
	

}


