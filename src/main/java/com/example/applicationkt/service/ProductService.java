package com.example.applicationkt.service;

import com.example.applicationkt.dto.ProductCreateRequest;
import com.example.applicationkt.model.Category;
import com.example.applicationkt.model.Product;
import com.example.applicationkt.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.List;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    public Product getProductById(Long id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found with id: " + id));
    }

    public Product createProduct(Product product) {
        return productRepository.save(product);
    }

    public Product updateProduct(Product product) {
        // Kiểm tra tồn tại trước khi update
        if (product.getId() == null || productRepository.findById(product.getId()).isEmpty()) {
            throw new RuntimeException("Cannot update. Product not found with id: " + product.getId());
        }
        return productRepository.update(product);
    }

    public void deleteProduct(Long id) {
        boolean deleted = productRepository.deleteById(id);
        if (!deleted) {
            throw new RuntimeException("Cannot delete. Product not found with id: " + id);
        }
    }

    public List<Product> getProductsByCategoryId(Long categoryId) {
        return productRepository.findByCategoryId(categoryId);
    }

    public List<String> getAllBrands() {
        return productRepository.findAllDistinctBrands();
    }

    public List<Product> getProductsByBrand(String brand) {
        return productRepository.findByBrand(brand);
    }

    // --------------------------
    // Upload ảnh cho product
    // --------------------------
    public Product uploadProductImage(Long id, MultipartFile file) throws Exception {
        Product product = getProductById(id);

        String uploadDir = "D:/CRUD project/uploads/images/";
        String fileName = System.currentTimeMillis() + "_" + file.getOriginalFilename();
        File dest = new File(uploadDir + fileName);

        dest.getParentFile().mkdirs();
        file.transferTo(dest);

        product.setImageUrl("/uploads/images/" + fileName);
        return updateProduct(product);
    }

    // --------------------------
    // Tạo product kèm upload ảnh
    // --------------------------
    public Product createProductWithImage(ProductCreateRequest request) throws Exception {
        Product product = new Product();
        product.setName(request.getName());
        product.setSlug(request.getSlug());
        product.setPrice(request.getPrice());
        product.setDiscountprice(request.getDiscountprice());
        product.setBrand(request.getBrand());
        product.setDescription(request.getDescription());
        product.setStockquantity(request.getStockquantity());
        product.setIsNew(request.getIsNew());
        product.setIsHot(request.getIsHot());

        Category category = new Category();
        category.setId(request.getCategory());
        product.setCategory(category);

        MultipartFile file = request.getFiles();
        if (file != null && !file.isEmpty()) {
            String uploadDir = "D:/CRUD project/uploads/images/";
            String fileName = System.currentTimeMillis() + "_" + file.getOriginalFilename();
            File dest = new File(uploadDir + fileName);
            dest.getParentFile().mkdirs();
            file.transferTo(dest);

            product.setImageUrl("/uploads/images/" + fileName);
        }

        return createProduct(product);
    }
}
