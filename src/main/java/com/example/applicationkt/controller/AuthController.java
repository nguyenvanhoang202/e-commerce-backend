package com.example.applicationkt.controller;

import com.example.applicationkt.dto.LoginRequest;
import com.example.applicationkt.dto.RegisterRequest;
import com.example.applicationkt.dto.UpdateActiveRequest;
import com.example.applicationkt.model.Users;
import com.example.applicationkt.service.AuthService;
import com.example.applicationkt.util.JwtUtil;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class AuthController {

    private final AuthService usersService;
    private final JwtUtil jwtUtil;

    @Autowired
    public AuthController(AuthService usersService, JwtUtil jwtUtil) {
        this.usersService = usersService;
        this.jwtUtil = jwtUtil;
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@Valid @RequestBody RegisterRequest request) {
        try {
            Users user = usersService.registerUser(
                    request.getUsername(),
                    request.getPassword(),
                    request.getEmail(),
                    request.getRole()
            );
            return ResponseEntity.ok(user);
        } catch (RuntimeException e) {
            Map<String, Object> error = new HashMap<>();
            error.put("message", e.getMessage());
            return ResponseEntity.badRequest().body(error);
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody LoginRequest request,
                                   @RequestParam String appType) {
        Optional<Users> userOpt = usersService.login(request.getUsername(), request.getPassword());

        if (userOpt.isPresent()) {
            Users user = userOpt.get();

            // ✅ Check role phù hợp với app
            if (appType.equals("admin") &&
                    !(user.getRole().equals("ADMIN") || user.getRole().equals("MANAGER"))) {
                return ResponseEntity.status(403).body(Map.of("message", "Không có quyền truy cập admin"));
            }
            if (appType.equals("user") && !user.getRole().equals("USER")) {
                return ResponseEntity.status(403).body(Map.of("message", "Không có quyền truy cập user"));
            }

            // ✅ Sinh JWT
            String token = jwtUtil.generateToken(user.getUsername(), user.getRole());

            Map<String, Object> response = new HashMap<>();
            response.put("token", token);
            response.put("user", user);

            return ResponseEntity.ok(response);
        }

        return ResponseEntity.status(401).body(Map.of("message", "Invalid credentials or account inactive"));
    }

    @PutMapping("/users/{id}/active")
    public ResponseEntity<?> updateUserActive(
            @PathVariable Long id,
            @RequestParam Boolean active) {
        try {
            usersService.updateUserActive(id, active);
            return ResponseEntity.ok(Map.of(
                    "success", true,
                    "message", "Cập nhật trạng thái thành công"
            ));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of(
                    "success", false,
                    "message", "Lỗi khi cập nhật: " + e.getMessage()
            ));
        }
    }
}
