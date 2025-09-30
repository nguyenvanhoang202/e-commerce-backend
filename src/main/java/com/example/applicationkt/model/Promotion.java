package com.example.applicationkt.model;

import java.time.LocalDateTime;

public class Promotion {
    private Long id;
    private String code;
    private Double discount;
    private LocalDateTime startdate;
    private LocalDateTime enddate;
    private Boolean active;

    public Promotion() {
    }

    public Promotion(Long id, String code, Double discount, LocalDateTime startdate, LocalDateTime enddate, Boolean active) {
        this.id = id;
        this.code = code;
        this.discount = discount;
        this.startdate = startdate;
        this.enddate = enddate;
        this.active = active;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Double getDiscount() {
        return discount;
    }

    public void setDiscount(Double discount) {
        this.discount = discount;
    }

    public LocalDateTime getStartdate() {
        return startdate;
    }

    public void setStartdate(LocalDateTime startdate) {
        this.startdate = startdate;
    }

    public LocalDateTime getEnddate() {
        return enddate;
    }

    public void setEnddate(LocalDateTime enddate) {
        this.enddate = enddate;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }
}
