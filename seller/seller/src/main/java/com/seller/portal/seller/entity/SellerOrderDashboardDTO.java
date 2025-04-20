package com.seller.portal.seller.entity;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

public class SellerOrderDashboardDTO {
	
	    private Long sellerId;
	    private String shopName;
	    private Long orderId;
	    private String orderTrackingNumber;
	    private String orderStatus;
	    private Date orderDate;
	    private BigDecimal orderTotal;
	    private Long customerId;
	    private String customerName;
	    private String customerEmail;
	    private String shippingStreet;
	    private String shippingCity;
	    private String shippingState;
	    private String shippingCountry;
	    private String shippingZip;
	    private String sellerCity;
	    private String sellerState;
	    private String sellerPinCode;
	    private String productsOrdered;
	    private int totalItemsFromSeller;
	    private BigDecimal totalValueFromSeller;
	    private String estimatedDeliveryTime;
	  

		// Constructors, getters, and setters
	    public SellerOrderDashboardDTO() {
	    }

		public Long getSellerId() {
			return sellerId;
		}

		public void setSellerId(Long sellerId) {
			this.sellerId = sellerId;
		}

		public String getShopName() {
			return shopName;
		}

		public void setShopName(String shopName) {
			this.shopName = shopName;
		}

		public Long getOrderId() {
			return orderId;
		}

		public void setOrderId(Long orderId) {
			this.orderId = orderId;
		}

		public String getOrderTrackingNumber() {
			return orderTrackingNumber;
		}

		public void setOrderTrackingNumber(String orderTrackingNumber) {
			this.orderTrackingNumber = orderTrackingNumber;
		}

		public String getOrderStatus() {
			return orderStatus;
		}

		public void setOrderStatus(String orderStatus) {
			this.orderStatus = orderStatus;
		}

		public Date getOrderDate() {
			return orderDate;
		}

		public void setOrderDate(Date result) {
			this.orderDate = result;
		}

		public BigDecimal getOrderTotal() {
			return orderTotal;
		}

		public void setOrderTotal(BigDecimal orderTotal) {
			this.orderTotal = orderTotal;
		}

		public Long getCustomerId() {
			return customerId;
		}

		public void setCustomerId(Long customerId) {
			this.customerId = customerId;
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

		public String getShippingStreet() {
			return shippingStreet;
		}

		public void setShippingStreet(String shippingStreet) {
			this.shippingStreet = shippingStreet;
		}

		public String getShippingCity() {
			return shippingCity;
		}

		public void setShippingCity(String shippingCity) {
			this.shippingCity = shippingCity;
		}

		public String getShippingState() {
			return shippingState;
		}

		public void setShippingState(String shippingState) {
			this.shippingState = shippingState;
		}

		public String getShippingCountry() {
			return shippingCountry;
		}

		public void setShippingCountry(String shippingCountry) {
			this.shippingCountry = shippingCountry;
		}

		public String getShippingZip() {
			return shippingZip;
		}

		public void setShippingZip(String shippingZip) {
			this.shippingZip = shippingZip;
		}

		public String getSellerCity() {
			return sellerCity;
		}

		public void setSellerCity(String sellerCity) {
			this.sellerCity = sellerCity;
		}

		public String getSellerState() {
			return sellerState;
		}

		public void setSellerState(String sellerState) {
			this.sellerState = sellerState;
		}

		public String getSellerPinCode() {
			return sellerPinCode;
		}

		public void setSellerPinCode(String sellerPinCode) {
			this.sellerPinCode = sellerPinCode;
		}

		public String getProductsOrdered() {
			return productsOrdered;
		}

		public void setProductsOrdered(String productsOrdered) {
			this.productsOrdered = productsOrdered;
		}

		public int getTotalItemsFromSeller() {
			return totalItemsFromSeller;
		}

		public void setTotalItemsFromSeller(int totalItemsFromSeller) {
			this.totalItemsFromSeller = totalItemsFromSeller;
		}

		public BigDecimal getTotalValueFromSeller() {
			return totalValueFromSeller;
		}

		public void setTotalValueFromSeller(BigDecimal totalValueFromSeller) {
			this.totalValueFromSeller = totalValueFromSeller;
		}

		public String getEstimatedDeliveryTime() {
			return estimatedDeliveryTime;
		}

		public void setEstimatedDeliveryTime(String estimatedDeliveryTime) {
			this.estimatedDeliveryTime = estimatedDeliveryTime;
		}

	    
	}


