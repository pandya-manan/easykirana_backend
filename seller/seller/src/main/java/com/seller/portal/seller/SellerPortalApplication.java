package com.seller.portal.seller;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories(basePackages = "com.seller.portal.seller.repository")
@EntityScan(basePackages = "com.seller.portal.seller.entity")
public class SellerPortalApplication {

	public static void main(String[] args) {
		SpringApplication.run(SellerPortalApplication.class, args);
	}

}
