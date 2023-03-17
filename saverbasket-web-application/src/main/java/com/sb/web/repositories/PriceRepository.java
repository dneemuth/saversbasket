package com.sb.web.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.sb.web.entities.Price;

public interface PriceRepository extends JpaRepository<Price, Integer> {
	
	@Query("SELECT p FROM price p WHERE p.idProduct.idProduct=:productId")
	public List<Price> retrievePricesForProduct(@Param("productId") Integer productId);

}
