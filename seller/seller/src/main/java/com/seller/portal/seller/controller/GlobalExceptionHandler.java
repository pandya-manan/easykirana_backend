package com.seller.portal.seller.controller;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.seller.portal.seller.entity.Seller;
import com.seller.portal.seller.entity.User;
import com.seller.portal.seller.exception.SellerException;

@ControllerAdvice
public class GlobalExceptionHandler {
    
    @ExceptionHandler(SellerException.class)
    public String handleSellerException(SellerException e, Model model) {
    	if(e.getMessage().contains("The seller already exists with this email id:"))
    	{
    		model.addAttribute("errorMessage", e.getMessage());
    		model.addAttribute("seller",new Seller());
            return "sellers/signup"; // Create an error.html template
    	}
    	else if(e.getMessage().contains("There is no seller with this email id:"))
    	{
    		model.addAttribute("errorMessage", e.getMessage());
    		model.addAttribute("user", new User());
    		return "sellers/login";
    	}
    	else if(e.getMessage().contains("Invalid credentials have been entered for the email id:"))
    	{
    		model.addAttribute("errorMessage",e.getMessage());
    		model.addAttribute("user",new User());
    		return "sellers/login";
    	}
    	else
    	{
    		model.addAttribute("errorMessage",e.getMessage());
    		return "error";
    	}
        
    }
}