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
import jakarta.validation.constraints.Pattern;
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
    
    @NotBlank(message = "Street address is required")
    @Size(max = 255, message = "Street address must be less than 255 characters")
    @Column(name="street", length=255)
    private String street;
    
    @NotBlank(message = "City is required")
    @Size(max = 100, message = "City must be less than 100 characters")
    @Column(name="city", length=100)
    private String city;
    
    @NotBlank(message = "State is required")
    @Size(max = 100, message = "State must be less than 100 characters")
    @Column(name="state", length=100)
    private String state;
    
    @NotBlank(message = "Country is required")
    @Size(max = 100, message = "Country must be less than 100 characters")
    @Column(name="country", length=100)
    private String country;
    
    @NotBlank(message = "PIN code is required")
    @Pattern(regexp = "^[0-9]{2,6}$", message = "PIN code must contain only digits and be between 2-6 characters")
    @Column(name="pin_code", length=6)
    private String pinCode;
    
    @NotBlank(message = "Admin name is required")
    @Size(max = 100, message = "Admin name must be less than 100 characters")
    @Column(name="shop_admin_name", length=100)
    private String shopAdminName;

    @Column(name = "date_created")
    @CreationTimestamp
    private LocalDateTime dateCreated;
    
    @OneToMany(cascade=CascadeType.ALL,mappedBy="seller")
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

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getPinCode() {
        return pinCode;
    }

    public void setPinCode(String pinCode) {
        this.pinCode = pinCode;
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
                + ", password=" + password + ", street=" + street + ", city=" + city + ", state=" + state
                + ", country=" + country + ", pinCode=" + pinCode + ", shopAdminName=" + shopAdminName
                + ", dateCreated=" + dateCreated + "]";
    }
}