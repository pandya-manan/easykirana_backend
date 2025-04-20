package com.easykirana.mailservice.entity;

import java.util.List;

public class OrderInvoiceDto {
	
	private String orderId;
    private String customerName;
    private String customerEmail;
    private String orderDate;
    private double totalValue;
    private int totalItems;
    private List<String> products;
    private String shopName;
    private String shopOwnerName;
    private String shopAddress;
    private String estimatedDeliveryTime;
        
    public String getEstimatedDeliveryTime() {
		return estimatedDeliveryTime;
	}
	public void setEstimatedDeliveryTime(String estimatedDeliveryTime) {
		this.estimatedDeliveryTime = estimatedDeliveryTime;
	}
	public String getShopName() {
		return shopName;
	}
	public void setShopName(String shopName) {
		this.shopName = shopName;
	}
	public String getShopOwnerName() {
		return shopOwnerName;
	}
	public void setShopOwnerName(String shopOwnerName) {
		this.shopOwnerName = shopOwnerName;
	}
	public String getShopAddress() {
		return shopAddress;
	}
	public void setShopAddress(String shopAddress) {
		this.shopAddress = shopAddress;
	}
    
	public String getOrderId() {
		return orderId;
	}
	public void setOrderId(String orderId) {
		this.orderId = orderId;
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
	public String getOrderDate() {
		return orderDate;
	}
	public void setOrderDate(String orderDate) {
		this.orderDate = orderDate;
	}
	public double getTotalValue() {
		return totalValue;
	}
	public void setTotalValue(double totalValue) {
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
	@Override
	public String toString() {
		return "OrderInvoiceDto [orderId=" + orderId + ", customerName=" + customerName + ", customerEmail="
				+ customerEmail + ", orderDate=" + orderDate + ", totalValue=" + totalValue + ", totalItems="
				+ totalItems + ", products=" + products + "]";
	}
    
    
	
	

}
