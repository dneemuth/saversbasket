package com.sb.web.repositories;

import java.util.Collection;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.sb.web.entities.Business;
import com.sb.web.projections.BusinessProjection;

public interface BusinessRepository extends JpaRepository<Business, Integer>{
	
	@Query(value = "SELECT 	idBusiness, ( 6371 * acos( cos( radians(37) ) * cos( radians(:latitude) ) * cos( radians(:longitude) - radians(-122) ) + sin( radians(37) ) * sin( radians(:latitude) ) ) ) AS distance\n" + 
			"FROM business b HAVING distance < 10000000 ORDER BY distance LIMIT 0,20", nativeQuery = true)
	Collection<Marker> findNearestBusinesses(@Param("latitude") String latitude, @Param("longitude") String longitude);

	   public static interface Marker {
		   
	     Integer getIdBusiness();
	     
	     String getDistance();
	     
	  }
	   
	   
	 @Query("SELECT b FROM business b")
	 public List<BusinessProjection> retrieveAllValidBusinesses();

}
