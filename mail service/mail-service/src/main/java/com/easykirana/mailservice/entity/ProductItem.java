package com.easykirana.mailservice.entity;

import java.math.BigDecimal;

public class ProductItem {
	
	private String name;
    private int quantity;
    private BigDecimal price;
	public ProductItem() {
		super();
		// TODO Auto-generated constructor stub
	}
	public ProductItem(String name, int quantity, BigDecimal price) {
		super();
		this.name = name;
		this.quantity = quantity;
		this.price = price;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getQuantity() {
		return quantity;
	}
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	public BigDecimal getPrice() {
		return price;
	}
	public void setPrice(BigDecimal price) {
		this.price = price;
	}
    
    

}
