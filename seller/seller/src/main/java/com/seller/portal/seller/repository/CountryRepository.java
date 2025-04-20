package com.seller.portal.seller.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.seller.portal.seller.entity.*;

@Repository
public interface CountryRepository extends JpaRepository<Country,Integer>{

	Country findByName(String name);
}
