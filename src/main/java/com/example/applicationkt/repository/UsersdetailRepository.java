package com.example.applicationkt.repository;

import com.example.applicationkt.model.Usersdetail;
import java.util.Optional;

public interface UsersdetailRepository {
    // Tìm thông tin chi tiết theo id
    Optional<Usersdetail> findById(Long id);

    // Tìm thông tin chi tiết theo user id
    Optional<Usersdetail> findByUserId(Long userId);

    // Lưu thông tin chi tiết
    Usersdetail save(Usersdetail detail);

    // Cập nhật thông tin chi tiết
    void update(Usersdetail detail);

    // Xóa thông tin chi tiết theo user id
    void deleteByUserId(Long userId);
    Integer countUsersById(Long userId);
}