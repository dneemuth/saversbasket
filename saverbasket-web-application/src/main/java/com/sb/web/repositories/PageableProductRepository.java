package com.sb.web.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import com.sb.web.entities.Product;
import com.sb.web.projections.ProductProjection;

public interface PageableProductRepository extends PagingAndSortingRepository<Product, Integer>{
	
	@Query(value="SELECT p FROM product p inner join fetch p.productAttributeList pa WHERE pa.productMapKey = 'NAME' and lower(pa.productMapValue) like lower(concat('%', :searchValue,'%')) OR pa.productMapKey='TYPE' and pa.productMapValue = :globalProductCategory and p.productStatus=2",
			countQuery = "SELECT count(p) FROM product p inner join p.productAttributeList pa WHERE pa.productMapKey = 'NAME' and lower(pa.productMapValue) like lower(concat('%', :searchValue,'%')) OR pa.productMapKey='TYPE' and pa.productMapValue = :globalProductCategory and p.productStatus=2")
    public Page<ProductProjection> searchProductsOnFilter(@Param("searchValue") String searchValue,@Param("globalProductCategory") String globalProductCategory, Pageable pageable);
	
	
	@Query(value="SELECT p FROM product p inner join fetch p.productAttributeList pa WHERE pa.productMapKey='TYPE' and pa.productMapValue = :globalProductCategory and p.productStatus=2",
			countQuery = "SELECT count(p) FROM product p inner join p.productAttributeList pa WHERE pa.productMapKey='TYPE' and pa.productMapValue = :globalProductCategory and p.productStatus=2")
    public Page<ProductProjection> searchProductsByCategoryOnFilter(@Param("globalProductCategory") String globalProductCategory, Pageable pageable);
	
		
	@Query(value="SELECT p FROM product p inner join p.productAttributeList pa WHERE pa.productMapKey = 'NAME' and lower(pa.productMapValue) like lower(concat('%', :searchValue,'%')) and p.productStatus=2",
			countQuery ="SELECT count(p) FROM product p inner join p.productAttributeList pa WHERE pa.productMapKey = 'NAME' and lower(pa.productMapValue) like lower(concat('%', :searchValue,'%')) and p.productStatus=2")
    public Page<ProductProjection> searchProductsWithoutFilter(@Param("searchValue") String searchValue, Pageable pageable);
	
	
	@Query(value="SELECT p FROM product p inner join p.priceList pl WHERE year(pl.datePriceTimeNoted) = year(current_date) and  month(pl.datePriceTimeNoted) = month(current_date) and p.productStatus=2",
			countQuery ="SELECT count(p) FROM product p inner join p.priceList pl WHERE year(pl.datePriceTimeNoted) = year(current_date) and  month(pl.datePriceTimeNoted) = month(current_date) and p.productStatus=2")
    public Page<ProductProjection> searchProductsCurrentMonth(Pageable pageable);


}
