package com.example.applicationkt.dto;

import org.springframework.web.multipart.MultipartFile;

public class ProductCreateRequest {

    private String name;
    private String slug;
    private Double price;
    private Double discountprice;
    private String brand;
    private String description;
    private Integer stockquantity;
    private Boolean isNew;
    private Boolean isHot;
    private Long category;       // id của category từ client
    private MultipartFile files;  // ảnh upload (backend tự set imageUrl)

    // ======== Getter / Setter ========
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getSlug() { return slug; }
    public void setSlug(String slug) { this.slug = slug; }

    public Double getPrice() { return price; }
    public void setPrice(Double price) { this.price = price; }

    public Double getDiscountprice() { return discountprice; }
    public void setDiscountprice(Double discountprice) { this.discountprice = discountprice; }

    public String getBrand() { return brand; }
    public void setBrand(String brand) { this.brand = brand; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public Integer getStockquantity() { return stockquantity; }
    public void setStockquantity(Integer stockquantity) { this.stockquantity = stockquantity; }

    public Boolean getIsNew() { return isNew; }
    public void setIsNew(Boolean isNew) { this.isNew = isNew; }

    public Boolean getIsHot() { return isHot; }
    public void setIsHot(Boolean isHot) { this.isHot = isHot; }

    public Long getCategory() { return category; }
    public void setCategory(Long category) { this.category = category; }

    public MultipartFile getFiles() { return files; }
    public void setFiles(MultipartFile file) { this.files = file; }
}
