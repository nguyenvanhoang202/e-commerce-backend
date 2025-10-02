package com.example.applicationkt.controller;

import com.example.applicationkt.dto.ApiResponse;
import com.example.applicationkt.dto.ProductCreateRequest;
import com.example.applicationkt.model.Category;
import com.example.applicationkt.model.Product;
import com.example.applicationkt.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.List;

@RestController
@RequestMapping("/api/product")
public class ProductController {

    @Autowired
    private ProductService productService;

    // Lấy tất cả sản phẩm
    @GetMapping
    public ResponseEntity<List<Product>> getAllProducts() {
        return ResponseEntity.ok(productService.getAllProducts());
    }

    // Lấy sản phẩm theo id
    @GetMapping("/{id}")
    public ResponseEntity<?> getProductById(@PathVariable Long id) {
        try {
            Product product = productService.getProductById(id);
            return ResponseEntity.ok(product);
        } catch (RuntimeException ex) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(new ApiResponse(false, ex.getMessage()));
        }
    }

    // Tạo mới sản phẩm
    @PostMapping
    public ResponseEntity<ApiResponse> createProduct(@RequestBody Product product) {
        Product saved = productService.createProduct(product);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(new ApiResponse(true, "Thêm sản phẩm thành công, id = " + saved.getId()));
    }

    // Cập nhật sản phẩm
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse> updateProduct(@PathVariable Long id, @RequestBody Product product) {
        try {
            product.setId(id);
            productService.updateProduct(product);
            return ResponseEntity.ok(new ApiResponse(true, "Cập nhật sản phẩm thành công"));
        } catch (RuntimeException ex) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(new ApiResponse(false, ex.getMessage()));
        }
    }

    // Xóa sản phẩm
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse> deleteProduct(@PathVariable Long id) {
        try {
            productService.deleteProduct(id);
            return ResponseEntity.ok(new ApiResponse(true, "Xóa sản phẩm thành công"));
        } catch (RuntimeException ex) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(new ApiResponse(false, ex.getMessage()));
        }
    }

    // Lấy sản phẩm theo category
    @GetMapping("/category/{categoryId}")
    public ResponseEntity<?> getProductsByCategory(@PathVariable Long categoryId) {
        List<Product> products = productService.getProductsByCategoryId(categoryId);
        if (products.isEmpty()) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(new ApiResponse(false, "Không có sản phẩm nào trong category id = " + categoryId));
        }
        return ResponseEntity.ok(products);
    }

    // -------------------------
    // Upload 1 ảnh cho sản phẩm
    // -------------------------
    @PostMapping("/{id}/upload-image")
    public ResponseEntity<ApiResponse> uploadProductImage(
            @PathVariable Long id,
            @RequestParam("files") MultipartFile file) {
        try {
            // Lấy product
            Product product = productService.getProductById(id);

            // Tạo đường dẫn lưu file
            String uploadDir = "D:/CRUD project/uploads/images/";
            String fileName = System.currentTimeMillis() + "_" + file.getOriginalFilename();
            File dest = new File(uploadDir + fileName);

            // Tạo folder nếu chưa tồn tại
            dest.getParentFile().mkdirs();

            // Lưu file vào ổ đĩa
            file.transferTo(dest);

            // Cập nhật đường dẫn imageUrl trong product
            product.setImageUrl("/uploads/images/" + fileName);
            productService.updateProduct(product);

            return ResponseEntity.ok(new ApiResponse(true, "Upload ảnh thành công"));
        } catch (Exception ex) {
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse(false, "Upload ảnh thất bại: " + ex.getMessage()));
        }
    }
    // Tạo product + upload ảnh
    // -------------------------
    @PostMapping("/create-with-image")
    public ResponseEntity<ApiResponse> createProductWithImage(@ModelAttribute ProductCreateRequest request) {
        try {
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

            // Upload file trước
            MultipartFile file = request.getFiles();  // đổi từ getFile() → getFiles()
            if (file != null && !file.isEmpty()) {
                String uploadDir = "D:/CRUD project/uploads/images/";
                String fileName = System.currentTimeMillis() + "_" + file.getOriginalFilename();
                File dest = new File(uploadDir + fileName);
                dest.getParentFile().mkdirs();
                file.transferTo(dest);

                product.setImageUrl("/uploads/images/" + fileName);
            }

            Product saved = productService.createProduct(product);

            return ResponseEntity
                    .status(HttpStatus.CREATED)
                    .body(new ApiResponse(true, "Thêm sản phẩm + upload ảnh thành công, id = " + saved.getId()));
        } catch (Exception ex) {
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse(false, "Thêm sản phẩm thất bại: " + ex.getMessage()));
        }
    }
    // Xử lý RuntimeException chung
    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ApiResponse> handleRuntimeException(RuntimeException ex) {
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new ApiResponse(false, ex.getMessage()));
    }
}
