package com.example.applicationkt.controller;

import com.example.applicationkt.dto.*;
import com.example.applicationkt.service.PasswordService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/password")
public class PasswordController {

    private final PasswordService passwordService;

    public PasswordController(PasswordService passwordService) {
        this.passwordService = passwordService;
    }

    // Đổi mật khẩu khi đã đăng nhập
    @PostMapping("/change")
    public ResponseEntity<ApiResponse> changePassword(@Valid @RequestBody ChangePasswordRequest request) {
        passwordService.changePassword(request);
        return ResponseEntity.ok(new ApiResponse(true, "Password changed successfully"));
    }

    // Quên mật khẩu -> trả token in ra console
    @PostMapping("/forgot")
    public ResponseEntity<ApiResponse> forgotPassword(@Valid @RequestBody ForgotPasswordRequest request) {
        String token = passwordService.forgotPassword(request);
        return ResponseEntity.ok(new ApiResponse(true, "Reset token generated (check console): " + token));
    }

    // Reset mật khẩu bằng token + email + newPassword
    @PostMapping("/reset")
    public ResponseEntity<ApiResponse> resetPassword(@Valid @RequestBody ResetPasswordRequest request) {
        passwordService.resetPassword(request.getEmail(), request.getToken(), request.getNewPassword());
        return ResponseEntity.ok(new ApiResponse(true, "Password reset successfully"));
    }
}
