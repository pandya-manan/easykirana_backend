package com.easykirana.mailservice.entity;

import java.math.BigDecimal;
import java.util.List;

public class InvoiceEmailRequest {
	
	private String customerName;
    private String orderTrackingNumber;
    private String orderDate;
    private BigDecimal totalAmount;
    private String deliveryAddress;
    private List<ProductItem> products;
	public String getCustomerName() {
		return customerName;
	}
	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}
	public String getOrderTrackingNumber() {
		return orderTrackingNumber;
	}
	public void setOrderTrackingNumber(String orderTrackingNumber) {
		this.orderTrackingNumber = orderTrackingNumber;
	}
	public String getOrderDate() {
		return orderDate;
	}
	public void setOrderDate(String orderDate) {
		this.orderDate = orderDate;
	}
	public BigDecimal getTotalAmount() {
		return totalAmount;
	}
	public void setTotalAmount(BigDecimal totalAmount) {
		this.totalAmount = totalAmount;
	}
	public String getDeliveryAddress() {
		return deliveryAddress;
	}
	public void setDeliveryAddress(String deliveryAddress) {
		this.deliveryAddress = deliveryAddress;
	}
	public List<ProductItem> getProducts() {
		return products;
	}
	public void setProducts(List<ProductItem> products) {
		this.products = products;
	}
	public InvoiceEmailRequest(String customerName, String orderTrackingNumber, String orderDate,
			BigDecimal totalAmount, String deliveryAddress, List<ProductItem> products) {
		super();
		this.customerName = customerName;
		this.orderTrackingNumber = orderTrackingNumber;
		this.orderDate = orderDate;
		this.totalAmount = totalAmount;
		this.deliveryAddress = deliveryAddress;
		this.products = products;
	}
	public InvoiceEmailRequest() {
		super();
		// TODO Auto-generated constructor stub
	}
    
    

}
