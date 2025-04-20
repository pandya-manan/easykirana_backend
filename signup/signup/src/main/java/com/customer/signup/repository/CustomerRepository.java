package com.customer.signup.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.customer.signup.entity.*;
@Repository
public interface CustomerRepository extends JpaRepository<Customer,Integer>{

	boolean existsByPrimaryEmail(String primaryEmail);

}
