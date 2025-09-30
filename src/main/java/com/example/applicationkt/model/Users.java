package com.example.applicationkt.model;

import java.time.LocalDateTime;

public class Users {
    private Long id;
    private String username;        // tên đăng nhập
    private String password;        // mật khẩu (hash)
    private String email;
    private String role;            // ADMIN, STAFF, CUSTOMER
    private Boolean active;         // tài khoản có bị khóa không
    private LocalDateTime createdAt;

    public Users(Long userId) {
        this.id = userId;
    }

    public Users() {}

    public Users(Long id, String password, String username, String email, String role, Boolean active, LocalDateTime createdAt) {
        this.id = id;
        this.password = password;
        this.username = username;
        this.email = email;
        this.role = role;
        this.active = active;
        this.createdAt = createdAt;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }
}
