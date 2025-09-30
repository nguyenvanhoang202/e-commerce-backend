package com.example.applicationkt.model;

import java.time.LocalDate;

public class Usersdetail {
    private Long id;
    private String fullName;
    private String phone;
    private String address;
    private LocalDate birthday;
    private String gender;          // MALE, FEMALE, OTHER
    private Users users;           // 1-1: gắn với User

    public Usersdetail() {}

    public Usersdetail(Long id, String fullName, String phone, String address, LocalDate birthday, String gender, Users users) {
        this.id = id;
        this.fullName = fullName;
        this.phone = phone;
        this.address = address;
        this.birthday = birthday;
        this.gender = gender;
        this.users = users;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public LocalDate getBirthday() {
        return birthday;
    }

    public void setBirthday(LocalDate birthday) {
        this.birthday = birthday;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public Users getUsers() {
        return users;
    }

    public void setUsers(Users users) {
        this.users = users;
    }

    // Thêm method để lấy userId từ Users cho JDBC
    public Long getUserId() {
        return users != null ? users.getId() : null;
    }
}