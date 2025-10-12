package com.example.applicationkt.repository;

import com.example.applicationkt.model.Users;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@Repository
public class UsersRepositoryImpl implements UsersRepository {

    private final JdbcTemplate jdbcTemplate;

    public UsersRepositoryImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private final RowMapper<Users> rowMapper = (ResultSet rs, int rowNum) -> {
        Users u = new Users();
        u.setId(rs.getLong("id"));
        u.setUsername(rs.getString("username"));
        u.setPassword(rs.getString("password"));
        u.setEmail(rs.getString("email"));
        u.setRole(rs.getString("role"));
        u.setActive(rs.getBoolean("active"));
        u.setCreatedAt(rs.getTimestamp("createdAt").toLocalDateTime());
        return u;
    };

    @Override
    public Optional<Users> findById(Long id) {
        String sql = "SELECT * FROM \"Users\" WHERE id = ?";
        try {
            Users user = jdbcTemplate.queryForObject(sql, rowMapper, id);
            return Optional.ofNullable(user);
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    @Override
    public List<Users> getAllUsersForAdmin() {
        String sql = "SELECT * FROM \"Users\" WHERE role <> 'ADMIN'";
        return jdbcTemplate.query(sql, rowMapper);
    }

    @Override
    public List<Users> getAllUsersForManage() {
        String sql = "SELECT * FROM \"Users\" WHERE role = 'USER'";
        return jdbcTemplate.query(sql, rowMapper);
    }

    @Override
    public Optional<Users> updateUser(Users user) {
        String sqlCheck = "SELECT role FROM \"Users\" WHERE id = ?";
        try {
            String targetRole = jdbcTemplate.queryForObject(sqlCheck, String.class, user.getId());
            if (targetRole == null) return Optional.empty();

            // Quy tắc:
            // ADMIN: không ai sửa
            if ("ADMIN".equalsIgnoreCase(targetRole)) return Optional.empty();
            // MANAGE: chỉ ADMIN mới sửa, FE đã lọc nên mặc định backend có thể check token nếu muốn
            if ("MANAGE".equalsIgnoreCase(targetRole)) return Optional.empty();
            // USER: có thể sửa (ADMIN hoặc MANAGE)
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }

        String sql = """
    UPDATE "Users"
    SET
        username = COALESCE(?, username),
        password = COALESCE(?, password),
        email = COALESCE(?, email),
        role = COALESCE(?, role),
        active = COALESCE(?, active)
    WHERE id = ?
""";

        int updated = jdbcTemplate.update(sql,
                user.getUsername(),
                user.getPassword(),
                user.getEmail(),
                user.getRole(),
                user.getActive(),
                user.getId());

        return updated > 0 ? Optional.of(user) : Optional.empty();

    }

    @Override
    public boolean deleteUser(Long id) {
        String sqlCheck = "SELECT role FROM \"Users\" WHERE id = ?";
        try {
            String targetRole = jdbcTemplate.queryForObject(sqlCheck, String.class, id);
            if (targetRole == null) return false;

            if ("ADMIN".equalsIgnoreCase(targetRole)) return false;
            if ("MANAGE".equalsIgnoreCase(targetRole)) return false;
        } catch (EmptyResultDataAccessException e) {
            return false;
        }

        String sql = "DELETE FROM \"Users\" WHERE id = ?";
        int deleted = jdbcTemplate.update(sql, id);
        return deleted > 0;
    }
}
