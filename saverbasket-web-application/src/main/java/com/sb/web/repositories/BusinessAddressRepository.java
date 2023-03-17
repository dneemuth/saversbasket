package com.sb.web.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sb.web.entities.BusinessAddress;

public interface BusinessAddressRepository extends JpaRepository<BusinessAddress, Integer> {

}
