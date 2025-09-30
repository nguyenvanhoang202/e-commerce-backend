package com.example.applicationkt.service;

import com.example.applicationkt.model.Users;
import com.example.applicationkt.repository.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class UsersService {

    private final UsersRepository usersRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    @Autowired
    public UsersService(UsersRepository usersRepository, BCryptPasswordEncoder passwordEncoder) {
        this.usersRepository = usersRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public Users registerUser(String username, String password, String email, String role) {
        if (usersRepository.findByUsername(username).isPresent()) {
            throw new RuntimeException("Username already exists");
        }
        if (usersRepository.findByEmail(email).isPresent()) {
            throw new RuntimeException("Email already exists");
        }

        String encodedPassword = passwordEncoder.encode(password);
        Users user = new Users(null, encodedPassword, username, email, role, true, LocalDateTime.now());

        // Lưu Users và tạo Usersdetail null
        return usersRepository.save(user);
    }

    public Optional<Users> login(String username, String password) {
        Optional<Users> userOpt = usersRepository.findByUsername(username);
        if (userOpt.isPresent()) {
            Users user = userOpt.get();
            if (passwordEncoder.matches(password, user.getPassword()) && user.getActive()) {
                return Optional.of(user);
            }
        }
        return Optional.empty();
    }

    public void updateUserActive(Long id, Boolean active) {
        usersRepository.updateActive(id, active);
    }
}
