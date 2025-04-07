package com.seller.portal.seller.controller;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.seller.portal.seller.entity.Seller;
import com.seller.portal.seller.entity.User;
import com.seller.portal.seller.exception.SellerException;
import com.seller.portal.seller.service.SellerService;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import com.seller.portal.seller.entity.*;

@Controller
@RequestMapping("/sellers")
public class SellerController {
    
    @Autowired
    private SellerService sellerService;
    
    @GetMapping("/home")
    public String returnHomePage(Model model) {
        // Add any model attributes needed for home page
        return "sellers/home";
    }
    
    @GetMapping("/signup")
    public String signUpToSellerPortal(Model model) {
        Seller newSeller = new Seller();
        model.addAttribute("seller", newSeller);
        return "sellers/signup";
    }
    
    @PostMapping("/register")
    public String registerSeller(@Valid Seller seller, 
                              BindingResult result, 
                              RedirectAttributes redirectAttributes) throws SellerException {
        if (result.hasErrors()) {
            // Return to signup with the same view path pattern
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
    
    @GetMapping("/dashboard")  // Add this endpoint to handle the redirect
    public String showDashboard() {
        return "sellers/dashboard";  // No leading slash
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
        System.out.println("Seller information loggedin: "+seller.toString());
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









}