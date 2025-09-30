package com.example.applicationkt.repository;

import com.example.applicationkt.model.Users;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class PasswordRepositoryImpl implements PasswordRepository {

    private final JdbcTemplate jdbcTemplate;

    public PasswordRepositoryImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private final RowMapper<Users> rowMapper = (rs, rowNum) -> new Users(
            rs.getLong("id"),
            rs.getString("password"),
            rs.getString("username"),
            rs.getString("email"),
            rs.getString("role"),
            rs.getBoolean("active"),
            rs.getTimestamp("createdAt").toLocalDateTime()
    );

    @Override
    public Optional<Users> findByEmail(String email) {
        String sql = "SELECT * FROM \"Users\" WHERE email = ? LIMIT 1";
        return jdbcTemplate.query(sql, rowMapper, email).stream().findFirst();
    }

    @Override
    public Optional<Users> findByUsername(String username) {
        String sql = "SELECT * FROM \"Users\" WHERE username = ? LIMIT 1";
        return jdbcTemplate.query(sql, rowMapper, username).stream().findFirst();
    }

    @Override
    public void updatePassword(Long userId, String encodedPassword) {
        String sql = "UPDATE \"Users\" SET password = ? WHERE id = ?";
        jdbcTemplate.update(sql, encodedPassword, userId);
    }
}
