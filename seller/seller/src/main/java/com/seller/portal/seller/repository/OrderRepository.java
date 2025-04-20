package com.seller.portal.seller.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.seller.portal.seller.entity.Order;

import jakarta.transaction.Transactional;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {

	
    Page<Order> findByCustomerEmailOrderByDateCreatedDesc(@Param("email") String email, Pageable pageable);
    
    @EntityGraph(
            attributePaths = {
                "customer", 
                "orderItems",
                "shippingAddress"
            }
        )
        @Query("SELECT o FROM Order o WHERE o.id = :orderId")
        Optional<Order> findOrderWithDetails(@Param("orderId") Long orderId);
    
    // Add this new method to fetch product names in bulk
    @Query("SELECT p.id, p.name FROM Product p WHERE p.id IN :productIds")
    List<Object[]> findProductNamesByIds(@Param("productIds") List<Long> productIds);
    
    @Transactional
    @Modifying
    @Query(value = "UPDATE orders SET status = :status WHERE order_tracking_number = :orderTrackingNumber", nativeQuery = true)
    Integer updateOrderStatus(@Param("status") String status, @Param("orderTrackingNumber") String orderTrackingNumber);

    Order findByOrderTrackingNumber(String orderId);

}
