package com.seller.portal.seller.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import com.seller.portal.seller.entity.*;

@Repository
public interface ProductRepository extends JpaRepository<Product,Long>{

	@Query(value="SELECT * FROM product WHERE seller_id=?1",nativeQuery=true)
	List<Product> findProductsBySellerId(Long sellerId);
	
	List<Product> findByCategory_CategoryNameOrderByIdDesc(String categoryName);
	
	Product findProductById(Long id);

}
