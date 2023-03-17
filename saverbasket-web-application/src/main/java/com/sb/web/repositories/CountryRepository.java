package com.sb.web.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.sb.web.entities.Country;

public interface CountryRepository extends JpaRepository<Country, Integer>{
	
	@Query("SELECT c FROM country c WHERE c.countryName = :countryName")
    public Country retrieveSpecificCountry(@Param("countryName") String countryName);

}
