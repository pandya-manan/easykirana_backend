package com.easykirana.ecommerce.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;
import org.springframework.web.bind.annotation.CrossOrigin;

import com.easykirana.ecommerce.entity.State;


@RepositoryRestResource
public interface StateRepository extends JpaRepository<State,Integer>{

	@RestResource(path = "findByCountryCode", rel = "findByCountryCode")
	List<State> findByCountryCode(@Param("code")String code);
}
