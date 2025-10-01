package com.example.applicationkt.repository;

import com.example.applicationkt.model.Product;

import java.util.List;
import java.util.Optional;

public interface ProductRepository {
    List<Product> findAll(); // Lấy tất cả sản phẩm
    Optional<Product> findById(Long id); // Lấy sản phẩm theo id
    Product save(Product product); // Thêm sản phẩm mới
    Product update(Product product); // Cập nhật sản phẩm
    boolean deleteById(Long id); // Xóa sản phẩm theo id
    List<Product> findByCategoryId(Long categoryId); // Lọc sản phẩm theo category
}