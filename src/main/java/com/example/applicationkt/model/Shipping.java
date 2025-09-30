package com.example.applicationkt.model;

public class Shipping {
    private Long id;
    private String method;
    private Double fee;
    private String status;
    private Order order;

    public Shipping() {
    }

    public Shipping(Long id, String method, Double fee, String status, Order order) {
        this.id = id;
        this.method = method;
        this.fee = fee;
        this.status = status;
        this.order = order;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public Double getFee() {
        return fee;
    }

    public void setFee(Double fee) {
        this.fee = fee;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }
}
