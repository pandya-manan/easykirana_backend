package com.seller.portal.seller.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.seller.portal.seller.entity.State;

@Repository
public interface StateRepository extends JpaRepository<State,Integer>{
	@Query("SELECT s FROM State s WHERE s.country.id = :countryId")
    List<State> findByCountryId(@Param("countryId") int countryId);
//    
//    @Query("SELECT s FROM State s WHERE s.country.name = :countryName")
//    List<State> findByCountryName(@Param("countryName") String countryName);
//    
    @Query("SELECT s FROM State s WHERE s.name = :stateName AND s.country.id = :countryId")
    State findByNameAndCountryId(@Param("stateName") String stateName, 
                               @Param("countryId") int countryId);

}
