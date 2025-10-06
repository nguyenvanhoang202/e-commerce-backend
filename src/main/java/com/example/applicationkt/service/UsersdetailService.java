package com.example.applicationkt.service;

import com.example.applicationkt.model.Usersdetail;
import com.example.applicationkt.repository.UsersdetailRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
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
    public Usersdetail uploadUserAvatar(Long userId, MultipartFile file) throws Exception {
        // Lấy user detail hiện tại
        Usersdetail detail = usersdetailRepository.findByUserId(userId)
                .orElseThrow(() -> new RuntimeException("User detail not found for userId: " + userId));

        // Thư mục lưu avatar
        String uploadDir = "D:/CRUD project/uploads/images/";
        String fileName = System.currentTimeMillis() + "_" + file.getOriginalFilename();
        File dest = new File(uploadDir + fileName);

        // Tạo folder nếu chưa có
        dest.getParentFile().mkdirs();
        // Lưu file
        file.transferTo(dest);

        // Cập nhật avatar url
        detail.setAvatar("/uploads/images/" + fileName);

        // Gọi update (đã có avatar trong SQL)
        usersdetailRepository.update(detail);

        return detail;
    }

    public void deleteUsersdetailByUserId(Long userId) {
        usersdetailRepository.deleteByUserId(userId);
    }
}
