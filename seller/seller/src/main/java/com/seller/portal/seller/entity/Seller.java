package com.seller.portal.seller.entity;

import java.time.LocalDateTime;
import java.util.Set;

import org.hibernate.annotations.CreationTimestamp;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Entity
@Table(name="seller")
public class Seller {
	
		@Id
	    @GeneratedValue(strategy = GenerationType.IDENTITY)
	    @Column(name="id")
	    private Long id;

	    @NotBlank(message = "Shop name is required")
	    @Size(max = 100, message = "Shop name must be less than 100 characters")
	    @Column(name="shop_name", length=100)
	    private String shopName;
	    
	    @NotBlank(message = "Shop owner name is required")
	    @Size(max = 100, message = "Shop owner name must be less than 100 characters")
	    @Column(name="shop_owner", length=100)
	    private String shopOwner;
	    
	    @NotBlank(message = "Email is required")
	    @Email(message = "Email should be valid")
	    @Size(max = 150, message = "Email must be less than 150 characters")
	    @Column(name="shop_email", length=150)
	    private String shopEmail;
	    
	    @NotBlank(message = "Password is required")
	    @Size(min = 8, max = 255, message = "Password must be at least 8 characters")
	    @Column(name="password", length=255)
	    private String password;
	    
	    @NotBlank(message = "Shop address is required")
	    @Size(max = 255, message = "Address must be less than 255 characters")
	    @Column(name="shop_address", length=255)
	    private String shopAddress;
	    
	    @NotBlank(message = "Admin name is required")
	    @Size(max = 100, message = "Admin name must be less than 100 characters")
	    @Column(name="shop_admin_name", length=100)
	    private String shopAdminName;

	    @Column(name = "date_created")
	    @CreationTimestamp
	    private LocalDateTime dateCreated;
	    
	    @OneToMany(cascade=CascadeType.ALL,mappedBy="")
	    private Set<Product> products;
	    

	public Set<Product> getProducts() {
			return products;
		}

		public void setProducts(Set<Product> products) {
			this.products = products;
		}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getShopName() {
		return shopName;
	}

	public void setShopName(String shopName) {
		this.shopName = shopName;
	}

	public String getShopOwner() {
		return shopOwner;
	}

	public void setShopOwner(String shopOwner) {
		this.shopOwner = shopOwner;
	}

	public String getShopEmail() {
		return shopEmail;
	}

	public void setShopEmail(String shopEmail) {
		this.shopEmail = shopEmail;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getShopAddress() {
		return shopAddress;
	}

	public void setShopAddress(String shopAddress) {
		this.shopAddress = shopAddress;
	}

	public String getShopAdminName() {
		return shopAdminName;
	}

	public void setShopAdminName(String shopAdminName) {
		this.shopAdminName = shopAdminName;
	}

	public LocalDateTime getDateCreated() {
		return dateCreated;
	}

	public void setDateCreated(LocalDateTime dateCreated) {
		this.dateCreated = dateCreated;
	}

	@Override
	public String toString() {
		return "Seller [id=" + id + ", shopName=" + shopName + ", shopOwner=" + shopOwner + ", shopEmail=" + shopEmail
				+ ", password=" + password + ", shopAddress=" + shopAddress + ", shopAdminName=" + shopAdminName
				+ ", dateCreated=" + dateCreated + "]";
	}
	
	
}
