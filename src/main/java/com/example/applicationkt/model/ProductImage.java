package com.example.applicationkt.model;

public class ProductImage {
    private Long id;
    private String imageUrl;   // đường dẫn ảnh
    private Product product;   // tham chiếu đến Product

    public ProductImage() {}

    public ProductImage(Long id, String imageUrl, Product product) {
        this.id = id;
        this.imageUrl = imageUrl;
        this.product = product;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }
}
