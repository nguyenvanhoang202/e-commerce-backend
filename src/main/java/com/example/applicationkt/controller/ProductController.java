package com.example.applicationkt.controller;

import com.example.applicationkt.dto.ApiResponse;
import com.example.applicationkt.dto.ProductCreateRequest;
import com.example.applicationkt.model.Product;
import com.example.applicationkt.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/product")
public class ProductController {

    @Autowired
    private ProductService productService;

    // Lấy tất cả sản phẩm
    @GetMapping
    public ResponseEntity<ApiResponse> getAllProducts() {
        List<Product> products = productService.getAllProducts();
        return ResponseEntity.ok(new ApiResponse(true, "Lấy tất cả sản phẩm thành công", products));
    }

    // Lấy sản phẩm theo id
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse> getProductById(@PathVariable Long id) {
        try {
            Product product = productService.getProductById(id);
            return ResponseEntity.ok(new ApiResponse(true, "Lấy sản phẩm thành công", product));
        } catch (RuntimeException ex) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(new ApiResponse(false, ex.getMessage(), null));
        }
    }

    // Tạo mới sản phẩm
    @PostMapping
    public ResponseEntity<ApiResponse> createProduct(@RequestBody Product product) {
        Product saved = productService.createProduct(product);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(new ApiResponse(true, "Thêm sản phẩm thành công", saved));
    }

    // Cập nhật sản phẩm
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse> updateProduct(@PathVariable Long id, @RequestBody Product product) {
        try {
            product.setId(id);
            Product updated = productService.updateProduct(product);
            return ResponseEntity.ok(new ApiResponse(true, "Cập nhật sản phẩm thành công", updated));
        } catch (RuntimeException ex) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(new ApiResponse(false, ex.getMessage(), null));
        }
    }

    // Xóa sản phẩm
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse> deleteProduct(@PathVariable Long id) {
        try {
            productService.deleteProduct(id);
            return ResponseEntity.ok(new ApiResponse(true, "Xóa sản phẩm thành công", null));
        } catch (RuntimeException ex) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(new ApiResponse(false, ex.getMessage(), null));
        }
    }

    // Lấy sản phẩm theo category
    @GetMapping("/category/{categoryId}")
    public ResponseEntity<ApiResponse> getProductsByCategory(@PathVariable Long categoryId) {
        List<Product> products = productService.getProductsByCategoryId(categoryId);
        if (products.isEmpty()) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(new ApiResponse(false, "Không có sản phẩm nào trong category id = " + categoryId, null));
        }
        return ResponseEntity.ok(new ApiResponse(true, "Lấy sản phẩm theo category thành công", products));
    }

    // Upload 1 ảnh cho sản phẩm
    @PostMapping("/{id}/upload-image")
    public ResponseEntity<ApiResponse> uploadProductImage(
            @PathVariable Long id,
            @RequestParam("files") MultipartFile file) {
        try {
            Product updated = productService.uploadProductImage(id, file);
            return ResponseEntity.ok(new ApiResponse(true, "Upload ảnh thành công", updated));
        } catch (Exception ex) {
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse(false, "Upload ảnh thất bại: " + ex.getMessage(), null));
        }
    }

    // Tạo product + upload ảnh
    @PostMapping("/create-with-image")
    public ResponseEntity<ApiResponse> createProductWithImage(@ModelAttribute ProductCreateRequest request) {
        try {
            Product saved = productService.createProductWithImage(request);
            return ResponseEntity
                    .status(HttpStatus.CREATED)
                    .body(new ApiResponse(true, "Thêm sản phẩm + upload ảnh thành công", saved));
        } catch (Exception ex) {
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse(false, "Thêm sản phẩm thất bại: " + ex.getMessage(), null));
        }
    }
    // Cập nhật sản phẩm + upload ảnh (nếu có)
    @PutMapping("/{id}/update-with-image")
    public ResponseEntity<ApiResponse> updateProductWithImage(
            @PathVariable Long id,
            @ModelAttribute ProductCreateRequest request) {
        try {
            Product updated = productService.updateProductWithImage(id, request);
            return ResponseEntity.ok(new ApiResponse(true, "Cập nhật sản phẩm + ảnh thành công", updated));
        } catch (Exception ex) {
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse(false, "Cập nhật sản phẩm thất bại: " + ex.getMessage(), null));
        }
    }

    // Lấy tất cả brand
    @GetMapping("/brand")
    public ResponseEntity<ApiResponse> getAllBrands() {
        List<String> brands = productService.getAllBrands();
        if (brands.isEmpty()) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(new ApiResponse(false, "Không có brand nào", null));
        }
        return ResponseEntity.ok(new ApiResponse(true, "Lấy danh sách brand thành công", brands));
    }

    // Lọc sản phẩm theo brand
    @GetMapping("/brand/{brand}")
    public ResponseEntity<ApiResponse> getProductsByBrand(@PathVariable String brand) {
        List<Product> products = productService.getProductsByBrand(brand);
        if (products.isEmpty()) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(new ApiResponse(false, "Không tìm thấy sản phẩm nào thuộc brand = " + brand, null));
        }
        return ResponseEntity.ok(new ApiResponse(true, "Lấy sản phẩm theo brand thành công", products));
    }

}
