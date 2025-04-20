package com.seller.portal.seller.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.filter.HiddenHttpMethodFilter;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    // ✅ Static Resource Mapping for Product Images
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/assets/**")
                .addResourceLocations("file:uploads/assets/");

        registry.addResourceHandler("/images/**")
                .addResourceLocations("file:uploads/assets/images/");
    }

    // ✅ CORS Config for Angular HTTPS Frontend
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/assets/**")
                .allowedOrigins("https://localhost:4200")
                .allowedMethods("GET")
                .allowedHeaders("*");

        registry.addMapping("/images/**")
                .allowedOrigins("https://localhost:4200")
                .allowedMethods("GET")
                .allowedHeaders("*");
    }

    // ✅ Optional: Support PUT/DELETE via Hidden Method Fields
    @Bean
    public HiddenHttpMethodFilter hiddenHttpMethodFilter() {
        return new HiddenHttpMethodFilter();
    }
}
