package com.easykirana.ecommerce.service;

import com.easykirana.ecommerce.dto.Purchase;
import com.easykirana.ecommerce.dto.PurchaseResponse;

public interface CheckoutService{
	
	PurchaseResponse placeOrder(Purchase purchase);

}
