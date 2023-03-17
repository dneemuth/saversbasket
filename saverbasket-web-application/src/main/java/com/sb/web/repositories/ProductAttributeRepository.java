package com.sb.web.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.sb.web.entities.ProductAttribute;

public interface ProductAttributeRepository  extends JpaRepository<ProductAttribute, Integer> {
	
	
	 /**
     * Finds a person by using the last name as a search criteria.
     * @param lastName
     * @return  A list of persons whose last name is an exact match with the given last name.
     *          If no persons is found, this method returns an empty list.
     */
    @Query("SELECT pa FROM productattribute pa join fetch pa.idProduct p WHERE p.idProduct = :idProduct and pa.productMapKey = :productMapKey")
    public ProductAttribute retrieveProductAttributes(@Param("idProduct") Integer idProduct,@Param("productMapKey") String productMapKey );

}
