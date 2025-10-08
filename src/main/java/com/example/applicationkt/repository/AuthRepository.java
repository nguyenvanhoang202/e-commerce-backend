package com.example.applicationkt.repository;

import com.example.applicationkt.model.Users;
import com.example.applicationkt.model.Usersdetail;

import java.util.Optional;

public interface AuthRepository {
    // Tìm người dùng theo username
    Optional<Users> findByUsername(String username);

    // Tìm người dùng theo email
    Optional<Users> findByEmail(String email);

    // Lưu thông tin người dùng (đăng ký)
    Users save(Users user);

    Usersdetail save(Usersdetail detail);

    // Cập nhật trạng thái active (nếu cần khóa/mở tài khoản)
    void updateActive(Long id, Boolean active);
}