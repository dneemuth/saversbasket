package com.sb.web.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.sb.web.entities.Product;
import com.sb.web.projections.ProductProjection;

public interface ProductRepository extends JpaRepository<Product, Integer> {
		
	@Query("SELECT p FROM product p WHERE p.productBarCode = :productBarCode and p.idBusiness.idBusiness = :idBusiness")
	public Product getProductInformationByBarcodeAndBusiness(@Param("productBarCode") String productBarCode, @Param("idBusiness") Integer idBusiness);	
	
	@Query("SELECT p FROM product p WHERE p.productBarCode = :productBarCode")
	public List<Product> getProductInformationByBarcode(@Param("productBarCode") String productBarCode);
	
	@Query("SELECT p FROM product p inner join fetch p.productAttributeList pa WHERE pa.productMapKey = 'NAME' and lower(pa.productMapValue) like lower(concat('%', :searchValue,'%'))")
    public List<ProductProjection> searchProductsWithoutFilter(@Param("searchValue") String searchValue);

	@Query("SELECT p FROM product p inner join fetch p.productAttributeList pa WHERE pa.productMapKey = 'NAME' and lower(pa.productMapValue) like lower(concat('%', :searchValue,'%')) and p.idBusiness.idBusiness=:idBusiness")
    public List<Product> suggestProductsForBusiness(@Param("searchValue") String searchValue, @Param("idBusiness") Integer idBusiness);

	@Query(   value = "SELECT p.idProduct, b.idBusiness,MATCH(pa.productMapValue) AGAINST (?2 IN NATURAL LANGUAGE MODE) AS relevance FROM product p LEFT OUTER JOIN business b ON p.idBusiness = b.idBusiness LEFT OUTER JOIN productattribute pa ON p.idProduct = pa.idProduct WHERE b.idBusiness=?1 and (pa.productMapKey='NAME' and MATCH(pa.productMapValue) AGAINST (?2 IN NATURAL LANGUAGE MODE)) and (COALESCE(?3, null) is null or p.idProduct not in (?3)) order by 3 desc", 
			  nativeQuery = true)
	public List<Object[]> lookupSimilarProductsForBusiness(@Param("idBusiness") Integer idBusiness, @Param("searchValue") String searchValue,@Param("productKeys") List<Integer> productKeys);
	
	
	@Query(   value = "SELECT p.idProduct, b.idBusiness , bi.quantity FROM product p LEFT OUTER JOIN basketitem bi ON p.idProduct = bi.idProduct LEFT OUTER JOIN basket ba ON ba.idBasket = bi.idBasket LEFT OUTER JOIN business b ON p.idBusiness = b.idBusiness WHERE b.idBusiness=?1 and p.idProduct IN (?2) and ba.idUser=?3", 
			  nativeQuery = true)
	public List<Object[]> matchExactProductsForBusiness(@Param("idBusiness") Integer idBusiness, @Param("productKeys") List<Integer> productKeys,@Param("idUser") Integer idUser);


	@Query(   value = "SELECT p.idProduct, b.idBusiness, bi.quantity FROM product p LEFT OUTER JOIN basketitem bi ON p.idProduct = bi.idProduct LEFT OUTER JOIN business b ON p.idBusiness = b.idBusiness WHERE b.idBusiness=?1  and p.productBarCode IN (?2)", 
			  nativeQuery = true)
	public List<Object[]> matchExactProductsByBarcode(@Param("idBusiness") Integer idBusiness, @Param("productBarcodes") List<String> productBarcodes);

}
