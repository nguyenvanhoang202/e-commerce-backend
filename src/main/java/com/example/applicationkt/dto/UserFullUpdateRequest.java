package com.example.applicationkt.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class UserFullUpdateRequest {
    // Thông tin trong bảng users (được phép sửa)
    private String username;
    private String email;
    private String role;
    private Boolean active;

    // Thông tin trong bảng usersdetail (được phép sửa)
    private String fullName;
    private String phone;
    private String avatar;
    private String address;
    private LocalDate birthday;  // dạng yyyy-MM-dd, backend convert sang LocalDate
    private String gender;    // MALE, FEMALE, OTHER
}
