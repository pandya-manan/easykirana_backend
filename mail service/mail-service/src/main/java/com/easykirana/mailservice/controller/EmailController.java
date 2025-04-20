package com.easykirana.mailservice.controller;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.easykirana.mailservice.entity.CustomerDTO;
import com.easykirana.mailservice.entity.EmailInfo;
import com.easykirana.mailservice.entity.Item;
import com.easykirana.mailservice.entity.OrderInvoiceDto;
import com.easykirana.mailservice.entity.SellerInfo;
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
        return "Email Sent Successfully for Customer Order Confirmation: "+fullName+" "+info.getOrderTrackingNumber();
    }
    
    @PostMapping("/send-seller-signup-email")
    public String sendSellerSignUpEmail(@RequestParam String to,@RequestBody SellerInfo sellerInfo) throws MessagingException,javax.mail.MessagingException
    {
    	emailService.sendSellerSignUpEmail(to, sellerInfo);
    	return "Email Sent Successfully for Seller Sign Up: "+sellerInfo.getShopName()+" "+sellerInfo.getShopOwner();
    }
    
    @PostMapping("/send-order-invoice-mail")
    public String sendOrderInvoiceMail(@RequestParam String to,@RequestBody OrderInvoiceDto orderInvoiceDto) throws MessagingException, javax.mail.MessagingException
    {
    	emailService.sendOrderInvoiceEmail(to, orderInvoiceDto);
    	return "Email Sent Successfully for Order Invoice: "+orderInvoiceDto.getOrderId()+" "+orderInvoiceDto.getCustomerEmail()+" "+orderInvoiceDto.getCustomerName();
    }
    
    @PostMapping("/send-customer-signup-email")
    public String sendCustomerSignUpEmail(@RequestParam String to, @RequestBody CustomerDTO customerInfo) throws MessagingException
    {
    	emailService.sendCustomerSignUpEmail(to,customerInfo);
    	return "Email Sent Successfully for Customer Sign Up: "+customerInfo.getFirstName()+" "+customerInfo.getLastName()+" "+customerInfo.getPrimaryEmail();
    }
    
    @PostMapping("/send-customer-confirm-email")
    public String sendCustomerConfirmMail(@RequestParam String to, @RequestBody CustomerDTO customerDTO) throws MessagingException
    {
    	emailService.sendCustomerConfirmMail(to,customerDTO);
    	return "Email Sent Successfully for Customer Confirmation: "+customerDTO.getFirstName()+" "+customerDTO.getLastName()+" "+customerDTO.getPrimaryEmail();
    	
    }
    
}

