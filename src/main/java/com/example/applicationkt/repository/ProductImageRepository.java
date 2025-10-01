package com.example.applicationkt.repository;

import com.example.applicationkt.model.ProductImage;

import java.util.List;

public interface ProductImageRepository {
    int save(ProductImage image, Long productId);
    List<ProductImage> findByProductId(Long productId);
    int deleteById(Long id);                  // xóa theo id ảnh
    int deleteByProductId(Long productId);    // xóa tất cả ảnh của product
    int updateImage(Long id, String imageUrl); // sửa ảnh theo id
}
