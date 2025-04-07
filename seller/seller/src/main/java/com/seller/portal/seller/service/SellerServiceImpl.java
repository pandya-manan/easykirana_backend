package com.seller.portal.seller.service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.seller.portal.seller.entity.Product;
import com.seller.portal.seller.entity.ProductCategory;
import com.seller.portal.seller.entity.Seller;
import com.seller.portal.seller.exception.SellerException;
import com.seller.portal.seller.repository.ProductCategoryRepository;
import com.seller.portal.seller.repository.ProductRepository;
import com.seller.portal.seller.repository.SellerRepository;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;

@Service
@Transactional
public class SellerServiceImpl implements SellerService{
	
	@Value("${easykirana.upload.base-path}")
	private String uploadBasePath;

	
	@Autowired
	private SellerRepository sellerRepo;
	
	@Autowired
	private ProductRepository productRepo;
	
	@Autowired
	private ProductCategoryRepository categoryRepo;

	@Override
	public void saveSeller(@Valid Seller seller) throws SellerException {
		
		boolean result=sellerRepo.existsByShopEmail(seller.getShopEmail());
		if(result==true)
		{
			throw new SellerException("The seller already exists with this email id: "+seller.getShopEmail());
		}
		else
		{
			sellerRepo.save(seller);
		}
		return;
	}

	@Override
	public Seller validateSeller(String emailId, String password) throws SellerException {
		
		Seller foundSeller=sellerRepo.findByShopEmail(emailId);
		if(foundSeller==null)
		{
			throw new SellerException("There is no seller with this email id: "+emailId);
			
		}
		else if(!(foundSeller.getPassword().equalsIgnoreCase(password)))
		{
			throw new SellerException("Invalid credentials have been entered for the email id: "+emailId);
		}
		else
		{
			return foundSeller;
		}
	}

	@Override
	public List<Product> findProductsBySellerId(Long sellerId) throws SellerException {
		List<Product> productsForSeller=productRepo.findProductsBySellerId(sellerId);
		return productsForSeller;
	}

	@Override
	public List<ProductCategory> getAllCategories() {
		List<ProductCategory> categories=categoryRepo.findAll();
		return categories;
	}
	
//	@Override
//	public void addProduct(Product product, MultipartFile imageFile, Seller seller) throws IOException {
//
//	    // ✅ Step 1: Set the seller
//	    product.setSeller(seller);
//
//	    // ✅ Step 2: Load full category from database
//	    Long categoryId = product.getCategory().getId();
//	    ProductCategory fullCategory = categoryRepo.findById(categoryId)
//	            .orElseThrow(() -> new RuntimeException("Invalid category ID"));
//
//	    product.setCategory(fullCategory);
//
//	    // ✅ Step 3: Generate next serial number from database
//	    String categoryName = fullCategory.getCategoryName();
//	    List<Product> latestProducts = productRepo.findByCategory_CategoryNameOrderByIdDesc(categoryName);
//
//	    int serial = 1000; // default starting serial
//	    if (!latestProducts.isEmpty()) {
//	        Product latest = latestProducts.get(0);
//	        String latestImageUrl = latest.getImageUrl(); // e.g., assets/images/products/rices/rice-easykirana-1002.png
//
//	        String[] parts = latestImageUrl.split("-");
//	        String numberPart = parts[parts.length - 1].replace(".png", "");
//
//	        try {
//	            serial = Integer.parseInt(numberPart) + 1;
//	        } catch (NumberFormatException e) {
//	            serial++; // fallback
//	        }
//	    }
//
//	    // Optional safety check
//	    if (serial > 40000) {
//	        throw new IllegalStateException("Maximum serial number limit reached for category: " + categoryName);
//	    }
//
//	    // ✅ Step 4: Prepare and save the image file in both source & target folders
//	    String imageName = product.getName().toLowerCase().replaceAll(" ", "-") + "-easykirana-" + serial + ".png";
//
//	    // Source folder (for IDE persistence)
//	    Path sourcePath = Paths.get("src", "main", "resources", "static", "assets", "images", "products", categoryName.toLowerCase());
//	    Files.createDirectories(sourcePath);
//	    Files.write(sourcePath.resolve(imageName), imageFile.getBytes());
//
//	    // Target folder (for live app serving)
//	    Path targetPath = Paths.get("target", "classes", "static", "assets", "images", "products", categoryName.toLowerCase());
//	    Files.createDirectories(targetPath);
//	    Files.write(targetPath.resolve(imageName), imageFile.getBytes());
//
//	    // ✅ Step 5: Set product fields
//	    product.setImageUrl("assets/images/products/" + categoryName.toLowerCase() + "/" + imageName);
//	    product.setDateCreated(new Date());
//	    product.setLastUpdated(new Date());
//
//	    // ✅ Step 6: Save to DB
//	    productRepo.save(product);
//	}
	@Override
	public void addProduct(Product product, MultipartFile imageFile, Seller seller) throws IOException {

	    // ✅ Step 1: Set the seller
	    product.setSeller(seller);

	    // ✅ Step 2: Load full category from database
	    Long categoryId = product.getCategory().getId();
	    ProductCategory fullCategory = categoryRepo.findById(categoryId)
	            .orElseThrow(() -> new RuntimeException("Invalid category ID"));

	    product.setCategory(fullCategory);
	    String categoryName = fullCategory.getCategoryName().toLowerCase();

	    // ✅ Step 3: Generate next serial number from DB
	    List<Product> latestProducts = productRepo.findByCategory_CategoryNameOrderByIdDesc(categoryName);

	    int serial = 1000;
	    if (!latestProducts.isEmpty()) {
	        Product latest = latestProducts.get(0);
	        String latestImageUrl = latest.getImageUrl(); // e.g., /images/products/rices/rice-easykirana-1002.png

	        String[] parts = latestImageUrl.split("-");
	        String numberPart = parts[parts.length - 1].replace(".png", "");

	        try {
	            serial = Integer.parseInt(numberPart) + 1;
	        } catch (NumberFormatException e) {
	            serial++; // fallback
	        }
	    }

	    if (serial > 40000) {
	        throw new IllegalStateException("Maximum serial number limit reached for category: " + categoryName);
	    }

	    // ✅ Step 4: Save image to external uploads/ folder
	    String imageName = product.getName().toLowerCase().replaceAll(" ", "-")
	            + "-easykirana-" + serial + ".png";

	    Path imageFolder = Paths.get("uploads", "assets", "images", "products", categoryName);
	    Files.createDirectories(imageFolder);

	    Path imagePath = imageFolder.resolve(imageName);
	    Files.write(imagePath, imageFile.getBytes());

	    // ✅ Step 5: Save the image URL for browser (relative to /images/** mapping)
	    product.setImageUrl("assets/images/products/" + categoryName + "/" + imageName);
	    product.setDateCreated(new Date());
	    product.setLastUpdated(new Date());

	    // ✅ Step 6: Save to DB
	    productRepo.save(product);
	}

	


	@Override
	public void updateProduct(Long productId, Product updatedProduct, MultipartFile newImageFile) throws IOException {
	    Product existingProduct = productRepo.findById(productId)
	            .orElseThrow(() -> new RuntimeException("Product not found"));

	    // ✅ Step 1: Update basic fields
	    existingProduct.setName(updatedProduct.getName());
	    existingProduct.setDescription(updatedProduct.getDescription());
	    existingProduct.setUnitPrice(updatedProduct.getUnitPrice());
	    existingProduct.setSku(updatedProduct.getSku());
	    existingProduct.setActive(updatedProduct.isActive());
	    existingProduct.setUnitsInStock(updatedProduct.getUnitsInStock());

	    // ✅ Step 2: Update category if changed
	    if (updatedProduct.getCategory() != null &&
	            !updatedProduct.getCategory().getId().equals(existingProduct.getCategory().getId())) {
	        ProductCategory category = categoryRepo.findById(updatedProduct.getCategory().getId())
	                .orElseThrow(() -> new RuntimeException("Category not found"));
	        existingProduct.setCategory(category);
	    }

	    // ✅ Step 3: Handle image replacement
	    if (newImageFile != null && !newImageFile.isEmpty()) {
	        // Step 3.1: Extract serial number from old image URL
	        String oldImageUrl = existingProduct.getImageUrl(); // e.g., assets/images/products/rices/daawat-tibar-basmati-rice-easykirana-1004.png
	        String serial = "1000"; // fallback

	        if (oldImageUrl != null && oldImageUrl.contains("-easykirana-")) {
	            String[] parts = oldImageUrl.split("-easykirana-");
	            if (parts.length == 2) {
	                String numberPart = parts[1].replace(".png", "");
	                if (numberPart.matches("\\d+")) {
	                    serial = numberPart;
	                }
	            }

	            // Step 3.2: Delete old image file
	            String oldImagePathStr = oldImageUrl.replace("assets/", "uploads/assets/");
	            Path oldImagePath = Paths.get(oldImagePathStr).normalize();
	            try {
	                Files.deleteIfExists(oldImagePath);
	            } catch (IOException e) {
	                System.err.println("Failed to delete old image: " + e.getMessage());
	            }
	        }

	        // Step 3.3: Save new image with same serial number
	        String categoryName = existingProduct.getCategory().getCategoryName().toLowerCase();
	        String newImageName = updatedProduct.getName().toLowerCase().replaceAll(" ", "-")
	                + "-easykirana-" + serial + ".png";

	        Path imageFolder = Paths.get("uploads", "assets", "images", "products", categoryName);
	        Files.createDirectories(imageFolder);

	        Path newImagePath = imageFolder.resolve(newImageName);
	        Files.write(newImagePath, newImageFile.getBytes());

	        // Step 3.4: Update image URL to match Angular expectations
	        existingProduct.setImageUrl("assets/images/products/" + categoryName + "/" + newImageName);
	    }

	    // ✅ Step 4: Save updated product
	    existingProduct.setLastUpdated(new Date());
	    productRepo.save(existingProduct);
	}



	@Override
	public void deleteProduct(Long id) {
	    Product product = productRepo.findById(id)
	            .orElseThrow(() -> new RuntimeException("Product not found"));

	    String imageUrl = product.getImageUrl(); // e.g., assets/images/products/category/image.png

	    if (imageUrl != null && !imageUrl.isEmpty()) {
	        // Construct the absolute image path
	        Path imagePath = Paths.get(uploadBasePath, imageUrl.replace("/", File.separator));

	        try {
	            Files.deleteIfExists(imagePath);
	        } catch (IOException e) {
	            throw new RuntimeException("Failed to delete image: " + e.getMessage());
	        }
	    }

	    // Delete product from DB
	    productRepo.deleteById(id);
	}

	@Override
	public Product getProductById(Long id) {
		Product found=productRepo.findProductById(id);
		return found;
	}
	
}
