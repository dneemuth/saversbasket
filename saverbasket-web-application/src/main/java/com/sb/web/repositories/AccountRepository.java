package com.sb.web.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.sb.web.entities.Account;

public interface AccountRepository extends JpaRepository<Account, Integer>{
	
	@Query("SELECT a FROM account a WHERE  a.name = :email")
    public Account retrieveAccountForUser(@Param("email") String email);

}
