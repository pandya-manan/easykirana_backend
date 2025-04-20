package com.easykirana.mailservice.entity;
import jakarta.validation.constraints.*;
public class CustomerDTO {
    @NotBlank(message = "First name is required")
    @Size(min = 2, max = 50, message = "First name must be between 2 and 50 characters")
    private String firstName;

    @NotBlank(message = "Last name is required")
    @Size(min = 2, max = 50, message = "Last name must be between 2 and 50 characters")
    private String lastName;

    @NotBlank(message = "Username is required")
    @Size(min = 4, max = 20, message = "Username must be between 4 and 20 characters")
    @Pattern(regexp = "^[a-zA-Z0-9]+$", message = "Username can only contain letters and numbers")
    private String userName;

    @NotBlank(message = "Email is required")
    @Email(message = "Please provide a valid email address")
    private String primaryEmail;

    @NotBlank(message = "Password is required")
    @Size(min = 12, message = "Password must be at least 12 characters long")
    @Pattern(
    	    regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#$%^&*+=])(?=\\S+$).{12,}$",
    	    message = "Password must contain: 1 digit, 1 lowercase, 1 uppercase, 1 special character (!@#$%^&*+=), and no spaces"
    	)
    private String password;

    @NotBlank(message = "Status is required")
    private String status;

    // Getters and Setters
    public String getFirstName() { return firstName; }
    public void setFirstName(String firstName) { this.firstName = firstName; }
    
    public String getLastName() { return lastName; }
    public void setLastName(String lastName) { this.lastName = lastName; }
    
    public String getUserName() { return userName; }
    public void setUserName(String userName) { this.userName = userName; }
    
    public String getPrimaryEmail() { return primaryEmail; }
    public void setPrimaryEmail(String primaryEmail) { this.primaryEmail = primaryEmail; }
    
    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
    
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
}