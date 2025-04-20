package com.easykirana.admin.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.easykirana.admin.entity.*;
import com.easykirana.admin.repository.CustomerRepository;
import com.easykirana.admin.repository.SellerRepository;

import jakarta.transaction.Transactional;

@Service
public class CustomerService {

	 @Autowired
	  private CustomerRepository customerRepository;
	 
	 @Autowired
	 private SellerRepository sellerRepository;

	    public Page<Customer> getPendingCustomers(int page, int size) {
	        return customerRepository.findByStatus("pending", PageRequest.of(page, size));
	    }

	    public Customer foundCustomer(Integer customerId)
	    {
	    	return customerRepository.findByCustomerId(customerId);
	    }
	    
	    public void saveCustomer(Customer customer)
	    {
	    	customerRepository.save(customer);
	    	System.out.println(customer.getFirstName()+" "+customer.getLastName()+" is updated to CONFIRMED");
	    }

	    public Page<Seller> getAllSellers(int page, int size) {
	    	return sellerRepository.findAllByOrderByDateCreatedDesc(PageRequest.of(page, size));
	    }
	    

	    public List<Long> getProductIdsBySeller(Long sellerId) {
	        return sellerRepository.findById(sellerId)
	            .orElseThrow()
	            .getProducts()
	            .stream()
	            .map(Product::getId)
	            .collect(Collectors.toList());
	    }
	    
//	    public void deleteSeller(Long sellerId) {
//	        sellerRepository.deleteById(sellerId);
//	    }
	    
	    @Transactional
	    public void deleteSeller(Long sellerId) {
	        // First clear any relationships
	        Seller seller = sellerRepository.findById(sellerId)
	            .orElseThrow(() -> new RuntimeException("Seller not found"));
	        
	        // Clear products to avoid constraint violations
	        seller.getProducts().clear();
	        sellerRepository.save(seller);
	        
	        // Then delete the seller
	        sellerRepository.delete(seller);
	    }

	    public boolean sellerExists(Long sellerId) {
	        return sellerRepository.existsById(sellerId);
	    }
}
