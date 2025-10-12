package com.example.applicationkt.dto;

import com.example.applicationkt.model.Users;
import com.example.applicationkt.model.Usersdetail;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDetailDTO {
    private Long id;
    private String username;
    private String email;
    private String role;
    private Boolean active;
    private LocalDateTime createdAt;

    private String fullName;
    private String phone;
    private String avatar;
    private String address;
    private LocalDate birthday;
    private String gender;

    // Factory method gộp dữ liệu từ 2 bảng
    public static UserDetailDTO from(Users user, Usersdetail detail) {
        UserDetailDTO dto = new UserDetailDTO();
        dto.setId(user.getId());
        dto.setUsername(user.getUsername());
        dto.setEmail(user.getEmail());
        dto.setRole(user.getRole());
        dto.setActive(user.getActive());
        dto.setCreatedAt(user.getCreatedAt());

        if (detail != null) {
            dto.setFullName(detail.getFullName());
            dto.setPhone(detail.getPhone());
            dto.setAvatar(detail.getAvatar());
            dto.setAddress(detail.getAddress());
            dto.setBirthday(detail.getBirthday());
            dto.setGender(detail.getGender());
        }
        return dto;
    }
}