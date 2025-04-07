package com.seller.portal.seller.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.filter.HiddenHttpMethodFilter;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {
	@Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // Map /assets/** to the uploads/assets folder on your file system
        registry.addResourceHandler("/assets/**")
                .addResourceLocations("file:uploads/assets/");
        
        registry.addResourceHandler("/images/**")
        .addResourceLocations("file:uploads/assets/images/");
    }
	
	 @Bean
	    public HiddenHttpMethodFilter hiddenHttpMethodFilter() {
	        return new HiddenHttpMethodFilter();
	    }
}
