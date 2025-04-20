package com.seller.portal.seller.service;

import com.seller.portal.seller.exception.SellerException;

import java.io.IOException;
import java.util.List;

import java.util.Set;

import org.springframework.web.multipart.MultipartFile;

import com.seller.portal.seller.entity.*;

import jakarta.validation.Valid;

public interface SellerService {

	void saveSeller(@Valid Seller seller) throws SellerException;

	Seller validateSeller(String emailId, String password) throws SellerException;
	
	List<Product> findProductsBySellerId(Long sellerId) throws SellerException;
	
	List<ProductCategory> getAllCategories();

	void addProduct(Product product, MultipartFile imageFile, Seller seller) throws IOException;

	void updateProduct(Long productId, Product updatedProduct, MultipartFile newImageFile) throws IOException;

	void deleteProduct(Long productId) throws IOException;
	
	Product getProductById(Long id);
	
	List<Country> countries();

	public List<SellerOrderDashboardDTO> getDashboardData(Long sellerId);
	
	void saveOrderInvoiceEmail(OrderInvoiceDto orderInvoiceDto,Seller seller) throws SellerException;
	
	
	
}
