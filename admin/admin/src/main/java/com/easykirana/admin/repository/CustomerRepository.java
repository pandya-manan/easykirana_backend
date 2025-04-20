package com.easykirana.admin.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.easykirana.admin.entity.Customer;

public interface CustomerRepository extends JpaRepository<Customer, Integer> {
    @Query("SELECT c FROM Customer c ORDER BY c.customerId")
    Page<Customer> findAllCustomers(Pageable pageable);

	Page<Customer> findByStatus(String string, PageRequest of);
	
	Customer findByCustomerId(Integer customerId);
}
