package com.example.applicationkt.controller;

import com.example.applicationkt.dto.ApiResponse;
import com.example.applicationkt.model.Users;
import com.example.applicationkt.service.UsersService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
public class UsersController {

    private final UsersService usersService;

    public UsersController(UsersService usersService) {
        this.usersService = usersService;
    }

    // Admin lấy tất cả user
    @GetMapping("/admin")
    public ApiResponse getAllUsersForAdmin() {
        return usersService.getAllUsersForAdmin();
    }

    // Manage lấy tất cả user
    @GetMapping("/manage")
    public ApiResponse getAllUsersForManage() {
        return usersService.getAllUsersForManage();
    }

    // Sửa user
    @PutMapping("/{id}")
    public ApiResponse updateUser(@PathVariable Long id, @RequestBody Users user) {
        user.setId(id);
        return usersService.updateUser(user);
    }

    // Xóa user
    @DeleteMapping("/{id}")
    public ApiResponse deleteUser(@PathVariable Long id) {
        return usersService.deleteUser(id);
    }
}
