package com.example.applicationkt.repository;

import com.example.applicationkt.model.Users;
import java.util.List;
import java.util.Optional;

public interface UsersRepository {
    Optional<Users> findById(Long id);
    // admin gọi, lấy tất cả user (USER + MANAGE), trừ ADMIN
    List<Users> getAllUsersForAdmin();

    // manage gọi, chỉ lấy user role = USER
    List<Users> getAllUsersForManage();

    // sửa user theo role target, tự kiểm soát quyền
    Optional<Users> updateUser(Users user);

    // xóa user theo role target, tự kiểm soát quyền
    boolean deleteUser(Long id);
}
