package com.easykirana.ecommerce.service;

import java.util.ArrayList;
import java.util.Set;
import java.util.UUID;
import java.lang.System.Logger;
import java.util.*;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.easykirana.ecommerce.dao.CustomerRepository;
import com.easykirana.ecommerce.dao.ProductRepositoryBackUp;
import com.easykirana.ecommerce.dto.EmailInfo;
import com.easykirana.ecommerce.dto.Purchase;
import com.easykirana.ecommerce.dto.PurchaseResponse;
import com.easykirana.ecommerce.entity.Customer;
import com.easykirana.ecommerce.entity.OrderItem;

import jakarta.transaction.Transactional;

import com.easykirana.ecommerce.entity.*;
import com.easykirana.ecommerce.dto.*;

@Service
public class CheckoutServiceImpl implements CheckoutService {

	private static final org.slf4j.Logger LOGGER = LoggerFactory.getLogger(CheckoutServiceImpl.class);
    private CustomerRepository customerRepository;
    
    private ProductRepositoryBackUp productRepositoryBp;
    
    @Autowired
    private EmailServiceClient emailClientSender;

    public CheckoutServiceImpl(CustomerRepository customerRepository,ProductRepositoryBackUp productRepositoryBp) {
        this.customerRepository = customerRepository;
        this.productRepositoryBp=productRepositoryBp;
    }

    @Override
    @Transactional
    public PurchaseResponse placeOrder(Purchase purchase) {

        // retrieve the order info from dto
        Order order = purchase.getOrder();

        // generate tracking number
        String orderTrackingNumber = generateOrderTrackingNumber();
        order.setOrderTrackingNumber(orderTrackingNumber);

        // populate order with orderItems
        Set<OrderItem> orderItems = purchase.getOrderItems();
        orderItems.forEach(item -> order.add(item));

        // populate order with billingAddress and shippingAddress
        order.setBillingAddress(purchase.getBillingAddress());
        order.setShippingAddress(purchase.getShippingAddress());

        // populate customer with order
        Customer customer = purchase.getCustomer();
        //check if this customer is an existing customer
        String theEmail=customer.getEmail();
        Customer customerFromDB=customerRepository.findByEmail(theEmail);
        if(customerFromDB!=null)
        {
        	customer=customerFromDB;
        }
        customer.add(order);

        // save to the database
        customerRepository.save(customer);   
        
        EmailInfo toSendInfo=new EmailInfo();
        toSendInfo.setCustomerFirstName(customer.getFirstName());
        toSendInfo.setCustomerLastName(customer.getLastName());
        toSendInfo.setOrderTrackingNumber(orderTrackingNumber);
        
        List<Long> productIds = orderItems.stream()
                .map(OrderItem::getProductId)
                .toList(); 

        //Fetch all product names in one query
        List<String> productNames = productRepositoryBp.findProductNamesByIds(productIds);

        List<Item> emailItems = new ArrayList<>();
        int index = 0;

        for (OrderItem item : orderItems) {
        	Item toAdd = new Item();
        	toAdd.setProductId(item.getProductId());
        	toAdd.setProductName(productNames.get(index++)); // Use pre-fetched product names
        	toAdd.setQuantity(item.getQuantity());
        	toAdd.setUnitPrice(item.getUnitPrice().doubleValue());
        	emailItems.add(toAdd);
        }

        toSendInfo.setItems(emailItems);
        LOGGER.info(toSendInfo.toString());
        emailClientSender.sendEmail(theEmail, toSendInfo);

        // return a response
        return new PurchaseResponse(orderTrackingNumber);
    }

    private String generateOrderTrackingNumber() {

        // generate a random UUID number (UUID version-4)
        // For details see: https://en.wikipedia.org/wiki/Universally_unique_identifier
        //
        return UUID.randomUUID().toString();
    }
}









