package com.sb.web.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.sb.web.entities.BasketItem;
import com.sb.web.projections.BasketItemProjection;

public interface BasketItemRepository extends JpaRepository<BasketItem, Integer> {	
	
    @Query("SELECT bi FROM basketitem bi WHERE  bi.idBasketItem = :idBasketItem")
    public List<BasketItem> retrieveBasketItemsForSpecificBasket(@Param("idBasketItem") Integer idBasketItem);
    
    
    @Query("SELECT bi FROM basketitem bi WHERE  bi.basket.user.idUser = :idUser")
    public List<BasketItemProjection> retrieveBasketItemsForUser(@Param("idUser") Integer idUser);
    
    @Modifying
    @Query("delete from basketitem bi where bi.idBasketItem=:idBasketItem")
    void deleteBasketItemById(@Param("idBasketItem") Integer idBasketItem);
    
    
    @Query("SELECT bi.idProduct FROM basketitem bi LEFT OUTER JOIN basket b ON b.idBasket = bi.basket.idBasket WHERE b.user.idUser = :idUser")
    public List<Integer> retrieveBasketItemIdsForUser(@Param("idUser") Integer idUser);
    
    @Query("SELECT bi.quantity FROM basketitem bi LEFT OUTER JOIN basket b ON b.idBasket = bi.basket.idBasket WHERE bi.idProduct=:idProduct and b.user.idUser = :idUser")
    public Integer getQuantityForBasketItem(@Param("idProduct") Integer idProduct, @Param("idUser") Integer idUser);
    
    /**
    @Modifying
    @Query("Update BasketItem bt SET bt.isActive=:status WHERE bt.idBasketItem=:idBasketItem")
    public void updateBasketItemStatus(@Param("id") Integer idBasketItem, @Param("title") boolean status);
	*/

}
