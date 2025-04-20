package com.customer.signup.controller;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.customer.signup.entity.Customer;
import com.customer.signup.entity.CustomerDTO;
import com.customer.signup.repository.CustomerRepository;
import com.customer.signup.service.EmailServiceClient;


import jakarta.validation.Valid;

@Controller
public class CustomerController {

    private final CustomerRepository customerRepository;
    private final ModelMapper modelMapper;
    
    private static final Logger LOGGER = LoggerFactory.getLogger(CustomerController.class);
    
    @Autowired
    private EmailServiceClient emailClient;

    public CustomerController(CustomerRepository customerRepository, ModelMapper modelMapper) {
        this.customerRepository = customerRepository;
        this.modelMapper = modelMapper;
    }

    @GetMapping("/signup")
    public String showSignUpForm(Model model) {
        model.addAttribute("customerDTO", new CustomerDTO());
        return "signup-form";
    }

    @PostMapping("/signup")
    public String processSignUp(@Valid CustomerDTO customerDTO,
                              BindingResult result,
                              RedirectAttributes redirectAttributes) throws Exception {
        
        if (result.hasErrors()) {
            return "signup-form";
        }
        
        try {
            // Check for duplicate email first
            if (customerRepository.existsByPrimaryEmail(customerDTO.getPrimaryEmail())) {
                redirectAttributes.addFlashAttribute("errorMessage", "Email already registered");
                return "redirect:/signup";
            }
            
            // Map DTO to Entity
            Customer customer = modelMapper.map(customerDTO, Customer.class);
            
            // Save to database
            customerRepository.save(customer);
            
            // Send email
            emailClient.sendEmail(customerDTO.getPrimaryEmail(), customerDTO);
            
            // Success message
            redirectAttributes.addFlashAttribute("successMessage", 
                "Registration successful! Welcome " + customerDTO.getFirstName());
            return "redirect:/signup-success";
            
        } catch (DataIntegrityViolationException e) {
            redirectAttributes.addFlashAttribute("errorMessage", 
                "Registration failed: Email or username already exists");
            redirectAttributes.addFlashAttribute("customerDTO", customerDTO);
            return "redirect:/signup";
            
        } catch (Exception e) {
            // Log the error but continue since registration succeeded
            LOGGER.error("Failed to send confirmation email to " + customerDTO.getPrimaryEmail(), e);
            redirectAttributes.addFlashAttribute("warningMessage", 
                "Registration successful, but we couldn't send confirmation email");
            redirectAttributes.addFlashAttribute("customerDTO", customerDTO);
            return "redirect:/signup";
            
        }
    }

    @GetMapping("/signup-success")
    public String showSuccessPage() {
        return "signup-success";
    }
}