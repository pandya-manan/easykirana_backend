package com.easykirana.ecommerce.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.easykirana.ecommerce.entity.Product;

@Repository
public interface ProductRepositoryBackUp extends JpaRepository<Product, Long> {
	
	@Query("SELECT p.name FROM Product p WHERE p.id IN:ids")
	List<String> findProductNamesByIds(List<Long>ids);
	
	@Query("SELECT p.name FROM Product p WHERE p.id=?1")
	String findProductNameById(Long productId);

	List<Product> findByIdIn(List<Long> productIds);

}
