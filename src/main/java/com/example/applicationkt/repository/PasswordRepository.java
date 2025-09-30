package com.example.applicationkt.repository;

import com.example.applicationkt.model.Users;
import java.util.Optional;

public interface PasswordRepository {
    Optional<Users> findByEmail(String email);
    Optional<Users> findByUsername(String username);
    void updatePassword(Long userId, String encodedPassword);
}
