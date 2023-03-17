package com.sb.web.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.sb.web.entities.Subscription;

public interface SubscriptionRepository  extends JpaRepository<Subscription, Integer>  {
	
	 @Query("SELECT s FROM subscription s LEFT OUTER JOIN account a ON a.name = s.account.name WHERE s.account.name=:ownerEmail")
	 public List<Subscription> retrieveSubscriptionsForUser(@Param("ownerEmail") String ownerEmail);

}
