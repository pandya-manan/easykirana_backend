package com.easykirana.ecommerce.config;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.repository.config.RepositoryConfiguration;
import org.springframework.data.rest.core.config.RepositoryRestConfiguration;
import org.springframework.data.rest.webmvc.config.RepositoryRestConfigurer;
import org.springframework.http.HttpMethod;
import org.springframework.web.servlet.config.annotation.*;

import com.easykirana.ecommerce.entity.Country;
import com.easykirana.ecommerce.entity.Product;
import com.easykirana.ecommerce.entity.ProductCategory;
import com.easykirana.ecommerce.entity.State;

import jakarta.persistence.EntityManager;
import jakarta.persistence.metamodel.EntityType;

@Configuration
public class MyDataRestConfig implements RepositoryRestConfigurer{
	
	private EntityManager entityManager;
	
	@Autowired
	public MyDataRestConfig(EntityManager theEntityManager)
	{
		entityManager=theEntityManager;
	}
	
	@Override
	public void configureRepositoryRestConfiguration(RepositoryRestConfiguration config,CorsRegistry cors)
	{
		HttpMethod[] theUnsupportedActions= {HttpMethod.PUT,HttpMethod.POST,HttpMethod.DELETE};
		
		//disable POST PUT DELETE FOR Product.class
		config.getExposureConfiguration()
		.forDomainType(Product.class)
		.withItemExposure((metdata,httpMethods)->httpMethods.disable(theUnsupportedActions))
		.withCollectionExposure((metdata,httpMethods)->httpMethods.disable(theUnsupportedActions));
		
		//disable POST PUT DELETE FOR ProductCategory.class
		config.getExposureConfiguration()
		.forDomainType(ProductCategory.class)
		.withItemExposure((metdata,httpMethods)->httpMethods.disable(theUnsupportedActions))
		.withCollectionExposure((metdata,httpMethods)->httpMethods.disable(theUnsupportedActions));
		
		config.getExposureConfiguration()
		.forDomainType(Country.class)
		.withItemExposure((metdata,httpMethods)->httpMethods.disable(theUnsupportedActions))
		.withCollectionExposure((metdata,httpMethods)->httpMethods.disable(theUnsupportedActions));
		
		config.getExposureConfiguration()
		.forDomainType(State.class)
		.withItemExposure((metdata,httpMethods)->httpMethods.disable(theUnsupportedActions))
		.withCollectionExposure((metdata,httpMethods)->httpMethods.disable(theUnsupportedActions));
		
		exposeIds(config);
		
		
	}
	//call an internal helper method
			private void exposeIds(RepositoryRestConfiguration config)
			{
				// expose entity ids
		        //

		        // - get a list of all entity classes from the entity manager
		        Set<EntityType<?>> entities = entityManager.getMetamodel().getEntities();

		        // - create an array of the entity types
		        List<Class> entityClasses = new ArrayList<>();

		        // - get the entity types for the entities
		        for (EntityType tempEntityType : entities) {
		            entityClasses.add(tempEntityType.getJavaType());
		        }

		        // - expose the entity ids for the array of entity/domain types
		        Class[] domainTypes = entityClasses.toArray(new Class[0]);
		        config.exposeIdsFor(domainTypes);
			}
}
