package com.seller.portal.seller.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.seller.portal.seller.entity.*;
@Repository
public interface OrderItemRepository extends JpaRepository<OrderItem,Long>{

	 @Query("SELECT CASE WHEN COUNT(oi) > 0 THEN true ELSE false END FROM OrderItem oi WHERE oi.productId = :productId")
	 boolean existsByProductId(@Param("productId") Long productId);
	    
	 @Modifying
	 @Query("DELETE FROM OrderItem oi WHERE oi.productId = ?1")
	 void deleteByProductId(Long productId);
}
