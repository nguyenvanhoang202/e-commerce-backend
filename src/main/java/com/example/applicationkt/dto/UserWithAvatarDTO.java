package com.example.applicationkt.dto;
import com.example.applicationkt.model.Users;
import com.example.applicationkt.model.Usersdetail;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserWithAvatarDTO {
    private Long id;
    private String username;
    private String email;
    private String role;
    private Boolean active;
    private LocalDateTime createdAt;
    private String avatar; // từ bảng Usersdetail

    public static UserWithAvatarDTO from(Users user, Usersdetail detail) {
        UserWithAvatarDTO dto = new UserWithAvatarDTO();
        dto.setId(user.getId());
        dto.setUsername(user.getUsername());
        dto.setEmail(user.getEmail());
        dto.setRole(user.getRole());
        dto.setActive(user.getActive());
        dto.setCreatedAt(user.getCreatedAt());
        dto.setAvatar(detail != null ? detail.getAvatar() : null);
        return dto;
    }
}
