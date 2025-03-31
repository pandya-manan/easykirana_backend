package com.easykirana.mailservice.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import com.easykirana.mailservice.entity.Item;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.util.List;

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
}
