package com.easykirana.admin.controller;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;


import com.easykirana.admin.entity.Customer;
import com.easykirana.admin.entity.CustomerDTO;
import com.easykirana.admin.entity.LoginCredentials;
import com.easykirana.admin.service.CustomerService;
import com.easykirana.admin.service.EmailServiceClient;
import com.easykirana.admin.service.SellerProductClient;

import jakarta.servlet.http.HttpSession;
import jakarta.transaction.Transactional;

@Controller
@RequestMapping("/admin")
public class AdminLoginController {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(AdminLoginController.class);
    
	@Autowired
	private CustomerService customerService;
	
	@Autowired
	private EmailServiceClient emailClient;
	
	private final ModelMapper modelMapper;
	
	
    private final SellerProductClient sellerProductClient;
	
	public AdminLoginController(ModelMapper modelMapper,
			SellerProductClient sellerProductClient) {
		super();
		this.modelMapper = modelMapper;
		this.sellerProductClient = sellerProductClient;
	}

	@Value("${admin.username}")
    private String adminUsername;
    
    @Value("${admin.password}")
    private String adminPassword;

    @GetMapping("/login")
    public String showLoginPage(Model model, 
                              @RequestParam(required = false) String error) {
        if (error != null) {
            model.addAttribute("error", "Invalid credentials");
        }
        model.addAttribute("credentials", new LoginCredentials());
        return "admin/login";
    }
    
    @PostMapping("/login")
    public String handleLogin(@ModelAttribute("credentials") LoginCredentials loginInfo,
                            HttpSession session,
                            RedirectAttributes redirectAttributes) {
        
        if (!adminUsername.equals(loginInfo.getUserName()) || 
            !adminPassword.equals(loginInfo.getUserPassword())) {
            redirectAttributes.addAttribute("error", true);
            return "redirect:/admin/login";
        }
        
        // Set authentication flag in session
        session.setAttribute("authenticated", true);
        return "redirect:/admin/dashboard";
    }

    @GetMapping("/dashboard")
    public String showDashboard(HttpSession session) {
        if (session.getAttribute("authenticated") == null) {
            return "redirect:/admin/login?error=auth";
        }
        return "admin/dashboard";
    }
    
    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/admin/login?logout";
    }
    
    @GetMapping("/customers")
    public String showPendingCustomers(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            Model model) {
            
            Page<Customer> customers = customerService.getPendingCustomers(page, size);
            model.addAttribute("customers", customers);
            model.addAttribute("currentPage", page);
            return "admin/customers";
    }
    
    @PostMapping("/confirm/{customerId}")
    public ResponseEntity<String> confirmCustomer(@PathVariable Integer customerId)
    {
    	 System.out.println("Confirming customer with ID: " + customerId);
         // In future you'll add:
         // 1. Update status to "confirmed"
         // 2. Send email
    	 Customer found=customerService.foundCustomer(customerId);
    	 CustomerDTO foundDTO=modelMapper.map(found, CustomerDTO.class);
    	 found.setStatus("CONFIRMED");
    	 foundDTO.setStatus("CONFIRMED");
    	 LOGGER.info("Confirmation mail sent to: "+foundDTO.getFirstName()+" "+found.getLastName());
    	 emailClient.sendEmail(found.getPrimaryEmail(), foundDTO);
    	 LOGGER.info("Customer "+found.getFirstName()+" "+found.getLastName()+" is updated to confirmed");
    	 customerService.saveCustomer(found);
    	 
         return ResponseEntity.ok("Customer with " + customerId + " confirmed");
    }
    
    @GetMapping("/sellers")
    public String showSellers(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            Model model) {
            
            model.addAttribute("sellers", customerService.getAllSellers(page, size));
            return "admin/sellers";
     }
    
//    @PostMapping("/{sellerId}/delete")
//    public String deleteSeller(
//            @PathVariable Long sellerId,
//            RedirectAttributes redirectAttributes) {
//        
//        try {
//            // Get all product IDs for this seller
//        	System.out.println("DELETE endpoint hit for seller: " + sellerId);
//            List<Long> productIds = customerService.getProductIdsBySeller(sellerId);
//            
//            // Delete all products via REST calls
//            productIds.forEach(sellerProductClient::deleteProduct);
//            
//            // Delete the seller
//            customerService.deleteSeller(sellerId);
//            
//            redirectAttributes.addFlashAttribute("success", 
//                "Seller and " + productIds.size() + " products deleted successfully");
//        } catch (Exception e) {
//            redirectAttributes.addFlashAttribute("error", 
//                "Failed to delete seller: " + e.getMessage());
//        }
//        
//        return "redirect:/admin/sellers";
//    }
    
    @Transactional
    @PostMapping("/{sellerId}/delete")
    public String deleteSeller(
            @PathVariable Long sellerId,
            RedirectAttributes redirectAttributes) {
        
        try {
            // 1. Log the deletion attempt
            System.out.println("Starting deletion for seller: " + sellerId);
            
            // 2. Get all product IDs for this seller
            List<Long> productIds = customerService.getProductIdsBySeller(sellerId);
            System.out.println("Found " + productIds.size() + " products to delete");
            
            if (!(productIds.isEmpty()))
            {
            	// 3. Delete all products via REST calls
            	productIds.forEach(productId -> {
                    System.out.println("Deleting product: " + productId);
                    sellerProductClient.deleteProduct(productId);
                });
            }
            
            
            
            
            // 4. Delete the seller
            System.out.println("Deleting seller: " + sellerId);
            customerService.deleteSeller(sellerId);
            
            // 5. Verify deletion
            boolean sellerExists = customerService.sellerExists(sellerId);
            System.out.println("Seller exists after deletion attempt: " + sellerExists);
            
            if (sellerExists) {
                throw new RuntimeException("Seller deletion failed");
            }
            
            redirectAttributes.addFlashAttribute("success", 
                "Seller and " + productIds.size() + " products deleted successfully");
        } catch (Exception e) {
            System.err.println("Error deleting seller: " + e.getMessage());
            redirectAttributes.addFlashAttribute("error", 
                "Failed to delete seller: " + e.getMessage());
        }
        
        return "redirect:/admin/sellers";
    }
}
