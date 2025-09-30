package com.example.applicationkt.model;

import java.time.LocalDateTime;

public class Product {
    private Long id;
    private String name;
    private String slug;
    private Double price;
    private Double discountprice;
    private String brand;
    private String imageUrl;
    private String description;
    private Integer stockquantity;
    private Boolean isNew;
    private Boolean isHot;
    private LocalDateTime createdAt;
    private Category category;

    public Product() {}

    public Product(Long id, String name, String slug, Double price, Double discountprice, String brand, String imageUrl, String description, Integer stockquantity, Boolean isNew, Boolean isHot, LocalDateTime createdAt, Category category) {
        this.id = id;
        this.name = name;
        this.slug = slug;
        this.price = price;
        this.discountprice = discountprice;
        this.brand = brand;
        this.imageUrl = imageUrl;
        this.description = description;
        this.stockquantity = stockquantity;
        this.isNew = isNew;
        this.isHot = isHot;
        this.createdAt = createdAt;
        this.category = category;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSlug() {
        return slug;
    }

    public void setSlug(String slug) {
        this.slug = slug;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Double getDiscountprice() {
        return discountprice;
    }

    public void setDiscountprice(Double discountprice) {
        this.discountprice = discountprice;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getStockquantity() {
        return stockquantity;
    }

    public void setStockquantity(Integer stockquantity) {
        this.stockquantity = stockquantity;
    }

    public Boolean getNew() {
        return isNew;
    }

    public void setNew(Boolean aNew) {
        isNew = aNew;
    }

    public Boolean getHot() {
        return isHot;
    }

    public void setHot(Boolean hot) {
        isHot = hot;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }
}
