package com.seller.portal.seller.controller;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.seller.portal.seller.entity.Country;
import com.seller.portal.seller.entity.OrderInvoiceDto;
import com.seller.portal.seller.entity.Product;
import com.seller.portal.seller.entity.ProductCategory;
import com.seller.portal.seller.entity.Seller;
import com.seller.portal.seller.entity.SellerOrderDashboardDTO;
import com.seller.portal.seller.entity.State;
import com.seller.portal.seller.entity.User;
import com.seller.portal.seller.exception.SellerException;
import com.seller.portal.seller.repository.CountryRepository;
import com.seller.portal.seller.repository.OrderRepository;
import com.seller.portal.seller.repository.StateRepository;
import com.seller.portal.seller.service.EmailServiceClient;
import com.seller.portal.seller.service.SellerService;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

@Controller

@RequestMapping("/sellers")
public class SellerController {
    
    @Autowired
    private SellerService sellerService;
    
    @Autowired
    private StateRepository stateRepository;
    
    @Autowired
    private CountryRepository countryRepository;
    
    @Autowired
    private OrderRepository orderRepository;
    
    @Autowired
    private EmailServiceClient emailServiceClient;
    
    private static final Logger logger = LoggerFactory.getLogger(SellerController.class);
    
    @GetMapping("/home")
    public String returnHomePage(Model model) {
        // Add any model attributes needed for home page
        return "sellers/home";
    }
    
    @GetMapping("/getStatesByCountry/{countryId}")
    @ResponseBody
    public List<State> getStatesByCountry(@PathVariable int countryId) {
        return stateRepository.findByCountryId(countryId);
    }

    
    @GetMapping("/signup")
    public String signUpToSellerPortal(Model model) {
        Seller newSeller = new Seller();
        model.addAttribute("seller", newSeller);
        model.addAttribute("countries", sellerService.countries());
        return "sellers/signup";
    }

    @GetMapping("/signup/states")
    @ResponseBody
    public List<State> getStatesByCountry(@RequestParam String countryName) {
        // First find the country by name
        Country country = countryRepository.findByName(countryName);
        if (country != null) {
            return stateRepository.findByCountryId(country.getId());
        }
        return Collections.emptyList();
    }

    @PostMapping("/register")
    public String registerSeller(@Valid Seller seller, 
                              BindingResult result, 
                              RedirectAttributes redirectAttributes,Model model) throws SellerException {
        
        if (result.hasErrors()) {
            model.addAttribute("countries", countryRepository.findAll());
            return "sellers/signup";
        }
        
        // Additional validation for country/state
        Country country = countryRepository.findByName(seller.getCountry());
        State state = stateRepository.findByNameAndCountryId(seller.getState(), country.getId());
        
        if (state == null) {
            result.rejectValue("state", "invalid.state", "Invalid state for selected country");
            model.addAttribute("countries", countryRepository.findAll());
            return "sellers/signup";
        }
        
        sellerService.saveSeller(seller);
        redirectAttributes.addFlashAttribute("message", 
            "Seller has successfully registered into Easy Kirana Eco System");
        return "redirect:/sellers/home";
    }
    
    @GetMapping("/login")
    public String showLoginForm(Model theModel)
    {
    	theModel.addAttribute("user", new User());
    	return "sellers/login";
    }
    
    @PostMapping("/validate-login")
    public String validateLogin(@ModelAttribute("user") User user,
                              HttpSession session,
                              RedirectAttributes redirectAttributes) throws SellerException {
        
        Seller seller = sellerService.validateSeller(user.getEmailId(), user.getPassword());
        
        if(seller != null) {
            session.setAttribute("loggedInSeller", seller);
            redirectAttributes.addFlashAttribute("successMessage", "Successfully Logged In");
            return "redirect:/sellers/dashboard";  // Changed to redirect
        } else {
            redirectAttributes.addFlashAttribute("errorMessage", "Invalid email or password");
            return "redirect:/sellers/login";
        }
    }
    
    @GetMapping("/logout")
    public String logout(HttpSession session, RedirectAttributes redirectAttributes) {
        session.removeAttribute("loggedInSeller");
        redirectAttributes.addFlashAttribute("loggedOut", "Successfully logged out");
        return "redirect:/sellers/home";
    }
    
    @GetMapping("/dashboard")
    public String showDashboard(HttpSession session, Model model) {
        // Same session check as your products controller
        Seller seller = (Seller) session.getAttribute("loggedInSeller");
        if (seller == null) {
            return "redirect:/sellers/login";
        }
        
        // Optional success message handling (keep your existing functionality)
        if (session.getAttribute("successMessage") != null) {
            model.addAttribute("successMessage", session.getAttribute("successMessage"));
            session.removeAttribute("successMessage");
        }
        
        return "sellers/dashboard";
    }
    
    @GetMapping("/products")
    public String showSellerProducts(HttpSession session, Model model) throws SellerException {
        // Get seller from session
        Seller seller = (Seller) session.getAttribute("loggedInSeller");
        
        // Fetch only this seller's products
        List<Product> products = sellerService.findProductsBySellerId(seller.getId());
        
        if(products.isEmpty())
        {
        	model.addAttribute("errorMessage", "No products are listed by you");
        }
        
        model.addAttribute("products", products);
        return "sellers/manage-products"; // Template name
    }
    
    @GetMapping("/products/new")
    public String showAddProductForm(Model model) {
        model.addAttribute("product", new Product());
        List<ProductCategory> categories=sellerService.getAllCategories();
        model.addAttribute("categories", categories);
        return "sellers/add-product";
    }
    
    @PostMapping("/products")
    public String handleAddProduct(@ModelAttribute Product product,
                                   @RequestParam("imageFile") MultipartFile imageFile,
                                   HttpSession session,RedirectAttributes redirectAttributes) throws IOException {
        Seller seller = (Seller) session.getAttribute("loggedInSeller");
        logger.info("Seller information logged in: {} {} {}",seller.getShopName(),seller.getShopOwner(),seller.getShopAdminName());
        sellerService.addProduct(product, imageFile, seller);
        redirectAttributes.addFlashAttribute("successMessage", "Product added successfully!");
        return "redirect:/sellers/products";
    }
    
    
    @GetMapping("/products/edit/{id}")
    public String showEditProductForm(@PathVariable Long id, Model model) {
        Product product = sellerService.getProductById(id); // or however you fetch the product
        if (product == null) {
            return "redirect:/sellers/products?error=ProductNotFound";
        }

        List<ProductCategory> categories = sellerService.getAllCategories();

        model.addAttribute("product", product);
        model.addAttribute("categories", categories);

        return "sellers/edit-product"; // match this with your Thymeleaf template path
    }
    
    @PostMapping("/edit/{id}")
    public String handleEditProduct(@PathVariable Long id,
                                    @ModelAttribute("product") Product updatedProduct,
                                    @RequestParam("imageFile") MultipartFile imageFile,
                                    Model model,
                                    RedirectAttributes redirectAttributes) {
        try {
            sellerService.updateProduct(id, updatedProduct, imageFile);
            redirectAttributes.addFlashAttribute("success", "Product updated successfully!");
            return "redirect:/sellers/products"; // This calls the GET /sellers/products mapping
        } catch (IOException e) {
            model.addAttribute("errorMessage", "Error updating product: " + e.getMessage());
            model.addAttribute("product", updatedProduct);
            return "sellers/edit-product";
        }
    }




    // ❌ Handle Delete Product
    @PostMapping("/products/delete")
    public String deleteProduct(@RequestParam("productId") Long productId, RedirectAttributes redirectAttributes) {
        try {
            sellerService.deleteProduct(productId); // assumes your service handles deletion
            redirectAttributes.addFlashAttribute("successMessage", "Product deleted successfully.");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Error deleting product: " + e.getMessage());
        }
        return "redirect:/sellers/products";
    }


//    @GetMapping("/orders/{sellerId}")
//    public List<SellerOrderDashboardDTO> getSellerOrders(@PathVariable Long sellerId) {
//        return sellerService.getDashboardData(sellerId);
//        
//    }
//
    @GetMapping("/orders")
    public String showOrdersPage(HttpSession session, Model model) {
        // Verify seller is logged in
        Seller loggedInSeller = (Seller) session.getAttribute("loggedInSeller");
        if (loggedInSeller == null) {
            return "redirect:/sellers/login";
        }
        
        // Add seller ID to model (for JavaScript to access)
        logger.info("Seller currently logged in: {} {} {} {} ",loggedInSeller.getId(),loggedInSeller.getShopName(),loggedInSeller.getShopOwner(),loggedInSeller.getShopAdminName());
        model.addAttribute("sellerId", loggedInSeller.getId());
        
        return "sellers/orders";
    }

    @GetMapping("/orders/{sellerId}")
    public ResponseEntity<?> getSellerOrders(@PathVariable Long sellerId, HttpSession session) {
        // Verify seller is logged in
        Seller loggedInSeller = (Seller) session.getAttribute("loggedInSeller");
        if (loggedInSeller == null || !loggedInSeller.getId().equals(sellerId)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        
        try {
            List<SellerOrderDashboardDTO> orders = sellerService.getDashboardData(sellerId);
            return ResponseEntity.ok(orders);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Error fetching orders");
        }
    } 
    
    @PostMapping("/log")
    public ResponseEntity<String> logOrder(@RequestBody OrderInvoiceDto orderInvoiceDto,HttpSession session) throws SellerException {
        // This will automatically map the JSON to your DTO
        logger.info("Received Order Statistics: {}",orderInvoiceDto);
        Seller loggedInSeller=(Seller)session.getAttribute("loggedInSeller");
        logger.info("Logged In Seller at the time of log function: {}",loggedInSeller.getShopName());
        sellerService.saveOrderInvoiceEmail(orderInvoiceDto,loggedInSeller);
        logger.info("Called Service Method to send order invoice mail for: {}",orderInvoiceDto.getCustomerEmail());
        // Process the data as needed
        return ResponseEntity.ok("Order data received successfully");
    }
    
    
}