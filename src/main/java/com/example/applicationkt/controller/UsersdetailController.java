package com.example.applicationkt.controller;

import com.example.applicationkt.model.Users;
import com.example.applicationkt.model.Usersdetail;
import com.example.applicationkt.service.UsersdetailService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Optional;

@RestController
@RequestMapping("/api")
public class UsersdetailController {

    private final UsersdetailService usersdetailService;

    @Autowired
    public UsersdetailController(UsersdetailService usersdetailService) {
        this.usersdetailService = usersdetailService;
    }

    @PutMapping("/usersdetail/{userId}")
    public ResponseEntity<?> updateUsersdetail(
            @PathVariable Long userId,
            @RequestBody Usersdetail detail) {
        try {
            detail.setUsers(new Users(userId)); // set userId để update
            Usersdetail updated = usersdetailService.updateUsersdetail(detail);
            return ResponseEntity.ok(updated);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/usersdetail/{userId}")
    public ResponseEntity<?> getUsersdetail(@PathVariable Long userId) {
        return usersdetailService.getUsersdetailByUserId(userId)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Upload avatar
    @PostMapping("/usersdetail/avatar/{userId}")
    public ResponseEntity<?> uploadAvatar(
            @PathVariable Long userId,
            @RequestParam("files") MultipartFile file) {

        try {
            Usersdetail updatedDetail = usersdetailService.uploadUserAvatar(userId, file);
            return ResponseEntity.ok(updatedDetail);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error: " + e.getMessage());
        }
    }

    @DeleteMapping("/usersdetail/{userId}")
    public ResponseEntity<Void> deleteUsersdetail(@PathVariable Long userId) {
        usersdetailService.deleteUsersdetailByUserId(userId);
        return ResponseEntity.ok().build();
    }
}

