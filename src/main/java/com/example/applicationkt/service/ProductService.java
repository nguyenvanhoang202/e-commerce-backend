package com.example.applicationkt.service;

import com.example.applicationkt.model.Product;
import com.example.applicationkt.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
}
