package com.example.applicationkt.service;

import com.example.applicationkt.model.ProductImage;
import com.example.applicationkt.repository.ProductImageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class ProductImageService {

    private final ProductImageRepository productImageRepository;

    @Autowired
    public ProductImageService(ProductImageRepository productImageRepository) {
        this.productImageRepository = productImageRepository;
    }

    private final String uploadDir = "D:\\CRUD project\\uploads\\images\\";

    // Thêm nhiều ảnh cho product
    public List<ProductImage> addImages(Long productId, List<MultipartFile> files) throws IOException {
        List<ProductImage> savedImages = new ArrayList<>();
        for (MultipartFile file : files) {
            String fileName = System.currentTimeMillis() + "_" + file.getOriginalFilename();
            File dest = new File(uploadDir + fileName);
            dest.getParentFile().mkdirs(); // tạo folder nếu chưa tồn tại
            file.transferTo(dest);

            ProductImage img = new ProductImage();
            img.setImageUrl("/uploads/images/" + fileName);
            // save và lấy id ngay
            productImageRepository.save(img, productId);
            savedImages.add(img);
        }
        return savedImages;
    }

    // Lấy tất cả ảnh theo productId
    public List<ProductImage> getImagesByProductId(Long productId) {
        return productImageRepository.findByProductId(productId);
    }

    // Xóa nhiều ảnh theo danh sách id
    public void deleteImages(List<Long> imageIds) {
        for (Long id : imageIds) {
            productImageRepository.deleteById(id);
        }
    }

    // Xóa tất cả ảnh của product
    public void deleteAllByProduct(Long productId) {
        productImageRepository.deleteByProductId(productId);
    }

    // Cập nhật nhiều ảnh theo danh sách id
    public void updateImages(List<Long> imageIds, List<MultipartFile> newFiles) throws IOException {
        for (int i = 0; i < imageIds.size(); i++) {
            Long id = imageIds.get(i);
            MultipartFile file = newFiles.get(i);

            String fileName = System.currentTimeMillis() + "_" + file.getOriginalFilename();
            File dest = new File(uploadDir + fileName);
            dest.getParentFile().mkdirs();
            file.transferTo(dest);

            // sửa imageUrl thành đường dẫn tương đối
            productImageRepository.updateImage(id, "/uploads/images/" + fileName);
        }
    }
}
