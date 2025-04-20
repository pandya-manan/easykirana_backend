package com.seller.portal.seller.entity;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
public class InvoiceDTO {
	
	private String orderTrackingNumber;
    private String customerName;
    private String customerEmail;
    private Date orderDate;
    private BigDecimal totalValue;
    private int totalItems;
    private List<String> products;
    private String shippingAddress;
    
    // Constructors, getters, and setters
    public InvoiceDTO() {}

	public InvoiceDTO(String orderTrackingNumber, String customerName, String customerEmail, Date orderDate,
			BigDecimal totalValue, int totalItems, List<String> products, String shippingAddress) {
		super();
		this.orderTrackingNumber = orderTrackingNumber;
		this.customerName = customerName;
		this.customerEmail = customerEmail;
		this.orderDate = orderDate;
		this.totalValue = totalValue;
		this.totalItems = totalItems;
		this.products = products;
		this.shippingAddress = shippingAddress;
	}

	public String getOrderTrackingNumber() {
		return orderTrackingNumber;
	}

	public void setOrderTrackingNumber(String orderTrackingNumber) {
		this.orderTrackingNumber = orderTrackingNumber;
	}

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public String getCustomerEmail() {
		return customerEmail;
	}

	public void setCustomerEmail(String customerEmail) {
		this.customerEmail = customerEmail;
	}

	public Date getOrderDate() {
		return orderDate;
	}

	public void setOrderDate(Date orderDate) {
		this.orderDate = orderDate;
	}

	public BigDecimal getTotalValue() {
		return totalValue;
	}

	public void setTotalValue(BigDecimal totalValue) {
		this.totalValue = totalValue;
	}

	public int getTotalItems() {
		return totalItems;
	}

	public void setTotalItems(int totalItems) {
		this.totalItems = totalItems;
	}

	public List<String> getProducts() {
		return products;
	}

	public void setProducts(List<String> products) {
		this.products = products;
	}

	public String getShippingAddress() {
		return shippingAddress;
	}

	public void setShippingAddress(String shippingAddress) {
		this.shippingAddress = shippingAddress;
	}
    
    

}
