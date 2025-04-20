package com.easykirana.admin.service;

import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

@Component
public class SellerProductClient {

    private final RestClient restClient;

    public SellerProductClient(RestClient restClient) {
        this.restClient = restClient;
    }

    public void deleteProduct(Long productId) {
        restClient.delete()
                .uri("/api/internal/products/{productId}", productId)
                .retrieve()
                .toBodilessEntity();
    }
}