package com.easykirana.admin.repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.easykirana.admin.entity.Seller;

@Repository
public interface SellerRepository extends JpaRepository<Seller,Integer>{

	// Corrected method name - follows Spring Data JPA naming conventions
    Page<Seller> findAllByOrderByDateCreatedDesc(Pageable pageable);
    
    // Alternative if you need custom query:
    @Query("SELECT s FROM Seller s ORDER BY s.dateCreated DESC")
    Page<Seller> findAllSellersWithPagination(Pageable pageable);

	Optional<Seller> findById(Long sellerId);

	void deleteById(Long sellerId);

	boolean existsById(Long sellerId);

}
