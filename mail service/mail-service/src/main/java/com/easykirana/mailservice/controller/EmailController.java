package com.easykirana.mailservice.controller;
import java.math.BigDecimal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.easykirana.mailservice.entity.EmailInfo;
import com.easykirana.mailservice.entity.Item;
import com.easykirana.mailservice.service.EmailService;

import jakarta.mail.MessagingException;

@RestController
public class EmailController {

    @Autowired
    private EmailService emailService;

    @PostMapping("/send-email")
    public String sendEmail(@RequestParam String to,@RequestBody EmailInfo info) throws MessagingException, javax.mail.MessagingException {
        
    	List<Item> items=info.getItems();
    	String fullName=info.getCustomerFirstName()+" "+info.getCustomerLastName();

    	double total=0.0;
    	for(Item item:items)
    	{
    		total+=item.getQuantity()*item.getUnitPrice();
    	}
    	String orderTrackingNumber=info.getOrderTrackingNumber();
        emailService.sendOrderEmail(to, fullName, items, total,orderTrackingNumber);
        return "Email Sent Successfully: "+fullName+" "+info.getOrderTrackingNumber();
    }
}

