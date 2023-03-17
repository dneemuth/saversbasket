package com.sb.web.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.sb.web.entities.ProductType;

public interface ProductTypeRepository extends JpaRepository<ProductType, Integer> {
	
	@Query("SELECT pt.code FROM producttype pt where pt.idProductType = :idProductType") 
    String findCodeById(@Param("idProductType") Integer idProductType);
	
	@Query("SELECT pt.code FROM producttype pt where pt.code = :code") 
    String findProductTypeByCode(@Param("code") String code);

	public List<ProductType> findAllByOrderByCodeAsc();
}
