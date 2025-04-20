package com.easykirana.mailservice.service;

import java.util.List;

import javax.mail.MessagingException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import com.easykirana.mailservice.entity.CustomerDTO;
import com.easykirana.mailservice.entity.Item;
import com.easykirana.mailservice.entity.OrderInvoiceDto;
import com.easykirana.mailservice.entity.SellerInfo;

import jakarta.mail.internet.MimeMessage;

@Service
public class EmailService {

    private static final Logger LOGGER = LoggerFactory.getLogger(EmailService.class);
    
    private final JavaMailSender mailSender;
    private final TemplateEngine templateEngine;

    public EmailService(JavaMailSender mailSender, TemplateEngine templateEngine) {
        this.mailSender = mailSender;
        this.templateEngine = templateEngine;
    }

    public void sendOrderEmail(String to, String name, List<Item> items, double total, String orderTrackingNumber) throws jakarta.mail.MessagingException, MessagingException {
        Context context = new Context();
		context.setVariable("name", name);
		context.setVariable("items", items);
		context.setVariable("total", total);
		context.setVariable("orderTrackNumber", orderTrackingNumber);

		String htmlContent = templateEngine.process("order-confirmation", context);

		jakarta.mail.internet.MimeMessage message = mailSender.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(message, true);
		
		helper.setTo(to);
		helper.setSubject("Your Order Confirmation " + orderTrackingNumber);
		helper.setText(htmlContent, true); // true = HTML content

		mailSender.send(message);
		LOGGER.info("Order confirmation email sent successfully to: {} (Tracking Number: {})", to, orderTrackingNumber);
    }
    
    public void sendSellerSignUpEmail(String to,SellerInfo sellerInfo) throws jakarta.mail.MessagingException
    {
    	Context context=new Context();
    	context.setVariable("shopOwner", sellerInfo.getShopOwner());
    	context.setVariable("shopName", sellerInfo.getShopName());
    	context.setVariable("shopEmail", sellerInfo.getShopEmail());
    	String fullAddress=sellerInfo.getStreet()+","+sellerInfo.getCity()+","+sellerInfo.getState()+","+sellerInfo.getCountry()+","+sellerInfo.getPinCode();
    	context.setVariable("shopAddress", fullAddress);
    	String htmlContent=templateEngine.process("seller-welcome", context);
    	jakarta.mail.internet.MimeMessage message=mailSender.createMimeMessage();
    	MimeMessageHelper helper=new MimeMessageHelper(message,true);
    	helper.setTo(to);
    	helper.setSubject("Welcome To Easy Kirana, "+sellerInfo.getShopName());
    	helper.setText(htmlContent,true);
    	mailSender.send(message);
    	LOGGER.info("Seller Sign Up Mail Sent Successfully to: {} (Shop Name: {} Shop Owner Name: {}",to,sellerInfo.getShopName(),sellerInfo.getShopOwner());
    }

    public void sendOrderInvoiceEmail(String to,OrderInvoiceDto orderInvoiceDto) throws jakarta.mail.MessagingException, MessagingException
    {
    	Context context = new Context();
        context.setVariable("order", orderInvoiceDto);

        String process = templateEngine.process("order-invoice", context);
        
        MimeMessage mimeMessage = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage);
        
        helper.setSubject("Your Order Invoice #" + orderInvoiceDto.getOrderId());
		helper.setText(process, true);
		helper.setTo(to);
		helper.setFrom("easykiranagroup9@gmail.com");
		LOGGER.info("Order Invoice Mail to be sent to: {} {}",to,orderInvoiceDto.getCustomerName());
		mailSender.send(mimeMessage);
		LOGGER.info("Order Invoice Mail SENT to: {} {}",to,orderInvoiceDto.getCustomerName());
    }
    
    public void sendCustomerSignUpEmail(String to,CustomerDTO customerInfo) throws jakarta.mail.MessagingException
    {
    	Context context=new Context();
    	context.setVariable("context",customerInfo);
    	String process=templateEngine.process("signup-acknowledgement",context);
    	MimeMessage mimeMessage=mailSender.createMimeMessage();
    	MimeMessageHelper helper=new MimeMessageHelper(mimeMessage);
    	helper.setSubject("Welcome to Easy Kirana! "+customerInfo.getFirstName()+" "+customerInfo.getLastName());
    	helper.setText(process, true);
    	helper.setTo(customerInfo.getPrimaryEmail());
    	helper.setFrom("easykiranagroup9@gmail.com");
    	mailSender.send(mimeMessage);
    	LOGGER.info("Customer Sign Up Acknowledgment Mail sent to: {} {}",customerInfo.getFirstName(),customerInfo.getLastName());
    	
    }

	public void sendCustomerConfirmMail(String to, CustomerDTO customerDTO) throws jakarta.mail.MessagingException {
		Context context=new Context();
		context.setVariable("context", customerDTO);
		String process=templateEngine.process("signup-confirmation",context);
		MimeMessage mimeMessage=mailSender.createMimeMessage();
		MimeMessageHelper helper=new MimeMessageHelper(mimeMessage);
    	helper.setSubject("Welcome to Easy Kirana! Your account is confirmed! "+customerDTO.getFirstName()+" "+customerDTO.getLastName());
    	helper.setText(process, true);
    	helper.setTo(customerDTO.getPrimaryEmail());
    	helper.setFrom("easykiranagroup9@gmail.com");
    	mailSender.send(mimeMessage);
    	LOGGER.info("Customer Sign Up Acknowledgment Mail sent to: {} {}",customerDTO.getFirstName(),customerDTO.getLastName());
		
	}
    
}
