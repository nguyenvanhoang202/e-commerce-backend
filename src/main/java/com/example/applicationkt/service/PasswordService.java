package com.example.applicationkt.service;

import com.example.applicationkt.dto.ChangePasswordRequest;
import com.example.applicationkt.dto.ForgotPasswordRequest;
import com.example.applicationkt.dto.ResetPasswordRequest;
import com.example.applicationkt.model.Users;
import com.example.applicationkt.repository.PasswordRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class PasswordService {

    private final PasswordRepository passwordRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    // Lưu token tạm thời trong bộ nhớ, kèm thời gian hết hạn
    private final Map<String, TokenInfo> resetTokens = new HashMap<>();

    private static class TokenInfo {
        Long userId;
        long expireAt; // timestamp hết hạn
        TokenInfo(Long userId, long expireAt) {
            this.userId = userId;
            this.expireAt = expireAt;
        }
    }

    public PasswordService(PasswordRepository passwordRepository, BCryptPasswordEncoder passwordEncoder) {
        this.passwordRepository = passwordRepository;
        this.passwordEncoder = passwordEncoder;
    }

    // Thay đổi mật khẩu với oldPassword
    public void changePassword(ChangePasswordRequest request) {
        Users user = passwordRepository.findByUsername(request.getUsername())
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (!passwordEncoder.matches(request.getOldPassword(), user.getPassword())) {
            throw new RuntimeException("Old password is incorrect");
        }

        String encodedNew = passwordEncoder.encode(request.getNewPassword());
        passwordRepository.updatePassword(user.getId(), encodedNew);
    }

    // Quên mật khẩu, trả về token
    public String forgotPassword(ForgotPasswordRequest request) {
        Users user = passwordRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new RuntimeException("Email not found"));

        // tạo token reset ngẫu nhiên
        String token = UUID.randomUUID().toString();
        long expireAt = System.currentTimeMillis() + 60_000; // 60 giây
        resetTokens.put(token, new TokenInfo(user.getId(), expireAt));

        // In ra console token (thay vì gửi email)
        System.out.println("===== RESET PASSWORD TOKEN =====");
        System.out.println("User: " + user.getUsername());
        System.out.println("Email: " + user.getEmail());
        System.out.println("Token: " + token);
        System.out.println("================================");

        return token;
    }

    // Reset mật khẩu, bắt buộc kèm email + token + newPassword
    public void resetPassword(String email, String token, String newPassword) {
        Users user = passwordRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Email not found"));

        TokenInfo info = resetTokens.get(token);
        if (info == null || !info.userId.equals(user.getId())) {
            throw new RuntimeException("Invalid token for this email");
        }

        if (System.currentTimeMillis() > info.expireAt) {
            resetTokens.remove(token);
            throw new RuntimeException("Token expired");
        }

        String encodedNew = passwordEncoder.encode(newPassword);
        passwordRepository.updatePassword(user.getId(), encodedNew);

        // Xóa token sau khi dùng
        resetTokens.remove(token);
    }
}
