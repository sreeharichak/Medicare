package com.simplilearn.springbootecommerce.config;

import com.simplilearn.springbootecommerce.entity.Product;
import com.simplilearn.springbootecommerce.entity.ProductCategory;

import java.util.Set;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.metamodel.EntityType;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.rest.core.config.RepositoryRestConfiguration;
import org.springframework.data.rest.webmvc.config.RepositoryRestConfigurer;
import org.springframework.http.HttpMethod;





@Configuration
public class MyDataRestConfig implements RepositoryRestConfigurer {
	
	private EntityManager entityManager;
	
	@Autowired
	public MyDataRestConfig(EntityManager theEntityManager) {
		entityManager=theEntityManager;
	}


    @Override
    public void configureRepositoryRestConfiguration(RepositoryRestConfiguration config) {
        HttpMethod[] theUnSupportedActions={HttpMethod.PUT, HttpMethod.POST,HttpMethod.DELETE};

        //Disable HttpMethods for Product: PUT,POST, DELETE
        config.getExposureConfiguration()
                .forDomainType(ProductCategory.class)
                .withItemExposure((metdata, httpMethods) -> httpMethods.disable(theUnSupportedActions))
                .withCollectionExposure((metdata, httpMethods) -> httpMethods.disable(theUnSupportedActions));

        //Disable HttpMethods for ProductCategory:PUT,POST, DELETE
        config.getExposureConfiguration()
                .forDomainType(ProductCategory.class)
                .withItemExposure((metdata, httpMethods) -> httpMethods.disable(theUnSupportedActions))
                .withCollectionExposure((metdata, httpMethods) -> httpMethods.disable(theUnSupportedActions));

        //Call an internal helper Method
        exposeIds(config);
    }


	private void exposeIds(RepositoryRestConfiguration config) {
		//expose entity Ids
		
		Set<EntityType<?>>entities=entityManager.getMetamodel().getEntities();
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
