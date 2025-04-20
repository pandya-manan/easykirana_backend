package com.seller.portal.seller.controller;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.seller.portal.seller.service.SellerService;

@RestController
@RequestMapping("/api/internal")
public class SellerRestController {
	
	@Autowired
	private SellerService sellerService;
	
	@DeleteMapping("/products/{productId}")
	public ResponseEntity<Void> deleteProduct(@PathVariable Long productId) throws IOException {
        sellerService.deleteProduct(productId);
        return ResponseEntity.noContent().build();
    }

}
