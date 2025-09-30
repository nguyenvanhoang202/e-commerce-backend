package com.example.applicationkt.service;

import com.example.applicationkt.model.Usersdetail;
import com.example.applicationkt.repository.UsersdetailRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UsersdetailService {

    private final UsersdetailRepository usersdetailRepository;

    @Autowired
    public UsersdetailService(UsersdetailRepository usersdetailRepository) {
        this.usersdetailRepository = usersdetailRepository;
    }

    public Usersdetail updateUsersdetail(Usersdetail detail) {
        if (detail.getUsers() == null || detail.getUsers().getId() == null) {
            throw new RuntimeException("UserId không hợp lệ");
        }

        // Nếu chưa có thì throw, vì insert chỉ tạo null khi register
        Optional<Usersdetail> existing = usersdetailRepository.findByUserId(detail.getUsers().getId());
        if (existing.isEmpty()) {
            throw new RuntimeException("Usersdetail chưa tồn tại cho user này");
        }

        usersdetailRepository.update(detail);
        return detail;
    }

    public Optional<Usersdetail> getUsersdetailByUserId(Long userId) {
        return usersdetailRepository.findByUserId(userId);
    }

    public void deleteUsersdetailByUserId(Long userId) {
        usersdetailRepository.deleteByUserId(userId);
    }
}
