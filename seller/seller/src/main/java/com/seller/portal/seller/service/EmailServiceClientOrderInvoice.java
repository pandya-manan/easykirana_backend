package com.seller.portal.seller.service;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

import com.seller.portal.seller.entity.OrderInvoiceDto;
import com.seller.portal.seller.entity.Seller;
import com.seller.portal.seller.entity.SellerInfo;


@Component
public class EmailServiceClientOrderInvoice {
    private final RestClient restClient;
    private static final String EMAIL_SERVICE_URL = "http://localhost:8091/send-order-invoice-mail";

    public EmailServiceClientOrderInvoice(RestClient.Builder restClientBuilder) {
        this.restClient = restClientBuilder.baseUrl(EMAIL_SERVICE_URL).build();
    }
    
    public String sendOrderInvoiceEmail(String to,OrderInvoiceDto orderInvoiceDto)
    {
    	return restClient.post()
    			.uri(uriBuilder->uriBuilder.queryParam("to", to).build())
    			.contentType(MediaType.APPLICATION_JSON)
    			.body(orderInvoiceDto)
    			.retrieve()
    			.onStatus(status->status.is4xxClientError(),(request,response)->{
    				throw new EmailServiceException("Client Error - Invalid Email Data");
    			})
    			.onStatus(status->status.is5xxServerError(), (request,response)->{
    				throw new EmailServiceException("Server Error - Email Service failed");
    			})
    			.onStatus(HttpStatusCode::is5xxServerError, (req,res)->{
    				String body=res.getBody().toString();
    				throw new EmailServiceException("Server Error: "+body);
    			})
    			.body(String.class);
    }
       

    @SuppressWarnings("serial")
	public static class EmailServiceException extends RuntimeException {
        public EmailServiceException(String message) {
            super(message);
        }
    }
}
