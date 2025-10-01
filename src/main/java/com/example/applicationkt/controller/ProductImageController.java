package com.example.applicationkt.controller;

import com.example.applicationkt.model.ProductImage;
import com.example.applicationkt.service.ProductImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/product-images")
public class ProductImageController {

    private final ProductImageService productImageService;

    @Autowired
    public ProductImageController(ProductImageService productImageService) {
        this.productImageService = productImageService;
    }

    // Thêm nhiều ảnh cho 1 product
    @PostMapping("/{productId}/upload")
    public ResponseEntity<List<ProductImage>> uploadImages(
            @PathVariable Long productId,
            @RequestParam("files") List<MultipartFile> files) throws IOException {
        return ResponseEntity.ok(productImageService.addImages(productId, files));
    }

    // Lấy tất cả ảnh theo product
    @GetMapping("/{productId}")
    public ResponseEntity<List<ProductImage>> getImagesByProductId(@PathVariable Long productId) {
        return ResponseEntity.ok(productImageService.getImagesByProductId(productId));
    }

    // Xóa nhiều ảnh theo danh sách id
    @DeleteMapping("/delete")
    public ResponseEntity<String> deleteImages(@RequestBody List<Long> imageIds) {
        productImageService.deleteImages(imageIds);
        return ResponseEntity.ok("Deleted selected images successfully");
    }

    // Xóa tất cả ảnh theo productId
    @DeleteMapping("/{productId}/delete-all")
    public ResponseEntity<String> deleteAllByProduct(@PathVariable Long productId) {
        productImageService.deleteAllByProduct(productId);
        return ResponseEntity.ok("Deleted all images of product successfully");
    }

    // Cập nhật nhiều ảnh theo danh sách id
    @PutMapping("/update")
    public ResponseEntity<String> updateImages(
            @RequestParam("imageIds") List<Long> imageIds,
            @RequestParam("files") List<MultipartFile> files) throws IOException {
        productImageService.updateImages(imageIds, files);
        return ResponseEntity.ok("Updated selected images successfully");
    }
}
