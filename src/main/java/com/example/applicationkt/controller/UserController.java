package com.example.applicationkt.controller;

import com.example.applicationkt.dto.LoginRequest;
import com.example.applicationkt.dto.RegisterRequest;
import com.example.applicationkt.model.Users;
import com.example.applicationkt.service.UsersService;
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
public class UserController {

    private final UsersService usersService;
    private final JwtUtil jwtUtil;

    @Autowired
    public UserController(UsersService usersService, JwtUtil jwtUtil) {
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
    public ResponseEntity<?> login(@Valid @RequestBody LoginRequest request) {
        Optional<Users> userOpt = usersService.login(request.getUsername(), request.getPassword());

        if (userOpt.isPresent()) {
            Users user = userOpt.get();

            // ✅ Sinh JWT thật
            String token = jwtUtil.generateToken(user.getUsername(), user.getRole());

            Map<String, Object> response = new HashMap<>();
            response.put("token", token);
            response.put("user", user);

            return ResponseEntity.ok(response);
        }

        Map<String, Object> error = new HashMap<>();
        error.put("message", "Invalid credentials or account inactive");
        return ResponseEntity.status(401).body(error);
    }

    @PutMapping("/users/{id}/active")
    public ResponseEntity<Void> updateUserActive(
            @PathVariable Long id,
            @RequestParam Boolean active) {
        usersService.updateUserActive(id, active);
        return ResponseEntity.ok().build();
    }
}
