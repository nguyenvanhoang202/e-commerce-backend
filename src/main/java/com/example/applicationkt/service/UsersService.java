package com.example.applicationkt.service;

import com.example.applicationkt.dto.ApiResponse;
import com.example.applicationkt.model.Users;
import com.example.applicationkt.repository.UsersRepository;
import com.example.applicationkt.util.JwtUtil;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UsersService {

    private final UsersRepository usersRepository;

    public UsersService(UsersRepository usersRepository) {
        this.usersRepository = usersRepository;
    }

    // Lấy currentUserRole từ JWT/Spring Security
    private String getCurrentUserRole() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || authentication.getAuthorities().isEmpty()) return null;
        return authentication.getAuthorities().iterator().next().getAuthority(); // "ADMIN", "MANAGE", "USER"
    }

    // Admin lấy tất cả user
    public ApiResponse getAllUsersForAdmin() {
        List<Users> users = usersRepository.getAllUsersForAdmin();
        return new ApiResponse(true, "Lấy danh sách user thành công", users);
    }

    // Manage lấy tất cả user (USER)
    public ApiResponse getAllUsersForManage() {
        List<Users> users = usersRepository.getAllUsersForManage();
        return new ApiResponse(true, "Lấy danh sách user thành công", users);
    }

    // Sửa user
    public ApiResponse updateUser(Users user) {
        String currentRole = getCurrentUserRole();
        if (currentRole == null) {
            return new ApiResponse(false, "Không xác định được role của bạn");
        }

        Optional<Users> targetOpt = usersRepository.findById(user.getId());
        if (targetOpt.isEmpty()) {
            return new ApiResponse(false, "User không tồn tại");
        }

        Users target = targetOpt.get();
        String targetRole = target.getRole().toUpperCase();

        // Quy tắc quyền
        switch (targetRole) {
            case "ADMIN":
                return new ApiResponse(false, "Không được sửa ADMIN");
            case "MANAGE":
                if (!"ADMIN".equalsIgnoreCase(currentRole)) {
                    return new ApiResponse(false, "Chỉ ADMIN mới sửa MANAGE");
                }
                break;
            case "USER":
                if (!"ADMIN".equalsIgnoreCase(currentRole) && !"MANAGE".equalsIgnoreCase(currentRole)) {
                    return new ApiResponse(false, "Không được sửa USER");
                }
                break;
        }

        Optional<Users> updated = usersRepository.updateUser(user);
        if (updated.isPresent()) {
            return new ApiResponse(true, "Cập nhật user thành công", updated.get());
        } else {
            return new ApiResponse(false, "Cập nhật thất bại");
        }
    }

    // Xóa user
    public ApiResponse deleteUser(Long id) {
        String currentRole = getCurrentUserRole();
        if (currentRole == null) {
            return new ApiResponse(false, "Không xác định được role của bạn");
        }

        Optional<Users> targetOpt = usersRepository.findById(id);
        if (targetOpt.isEmpty()) {
            return new ApiResponse(false, "User không tồn tại");
        }

        Users target = targetOpt.get();
        String targetRole = target.getRole().toUpperCase();

        // Quy tắc quyền
        switch (targetRole) {
            case "ADMIN":
                return new ApiResponse(false, "Không được xóa ADMIN");
            case "MANAGE":
                if (!"ADMIN".equalsIgnoreCase(currentRole)) {
                    return new ApiResponse(false, "Chỉ ADMIN mới xóa MANAGE");
                }
                break;
            case "USER":
                if (!"ADMIN".equalsIgnoreCase(currentRole) && !"MANAGE".equalsIgnoreCase(currentRole)) {
                    return new ApiResponse(false, "Không được xóa USER");
                }
                break;
        }

        boolean deleted = usersRepository.deleteUser(id);
        if (deleted) {
            return new ApiResponse(true, "Xóa user thành công");
        } else {
            return new ApiResponse(false, "Xóa thất bại");
        }
    }

    // Cập nhật trạng thái active
    public ApiResponse updateUserActive(Long id, boolean active) {
        Optional<Users> targetOpt = usersRepository.findById(id);
        if (targetOpt.isEmpty()) {
            return new ApiResponse(false, "User không tồn tại");
        }
        Users target = targetOpt.get();

        if ("ADMIN".equalsIgnoreCase(target.getRole())) {
            return new ApiResponse(false, "Không được khóa/unlock ADMIN");
        }

        target.setActive(active);
        Optional<Users> updated = usersRepository.updateUser(target);
        if (updated.isPresent()) {
            return new ApiResponse(true, "Cập nhật trạng thái thành công", updated.get());
        } else {
            return new ApiResponse(false, "Cập nhật thất bại");
        }
    }
}


