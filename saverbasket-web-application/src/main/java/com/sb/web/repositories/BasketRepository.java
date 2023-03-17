package com.sb.web.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.sb.web.entities.Basket;

public interface BasketRepository  extends JpaRepository<Basket, Integer>{
	
	 @Query("SELECT b FROM basket b WHERE b.user.idUser=:idUser")
	 public List<Basket> retrieveBasketForUser(@Param("idUser") Integer idUser);
	 
	
}
