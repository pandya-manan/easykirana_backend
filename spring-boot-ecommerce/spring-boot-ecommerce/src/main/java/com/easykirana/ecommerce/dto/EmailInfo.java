package com.easykirana.ecommerce.dto;
import java.util.*;
public class EmailInfo {
	
	String orderTrackingNumber;
	
	String customerFirstName;
	
	String customerLastName;
	
	List<Item> items=new ArrayList<>();

	public String getOrderTrackingNumber() {
		return orderTrackingNumber;
	}

	public void setOrderTrackingNumber(String orderTrackingNumber) {
		this.orderTrackingNumber = orderTrackingNumber;
	}

	public String getCustomerFirstName() {
		return customerFirstName;
	}

	public void setCustomerFirstName(String customerFirstName) {
		this.customerFirstName = customerFirstName;
	}

	public String getCustomerLastName() {
		return customerLastName;
	}

	public void setCustomerLastName(String customerLastName) {
		this.customerLastName = customerLastName;
	}

	public List<Item> getItems() {
		return items;
	}

	public void setItems(List<Item> items) {
		this.items = items;
	}

	@Override
	public String toString() {
		return "EmailInfo [orderTrackingNumber=" + orderTrackingNumber + ", customerFirstName=" + customerFirstName
				+ ", customerLastName=" + customerLastName + ", items=" + items + "]";
	}
	
	
	

}
