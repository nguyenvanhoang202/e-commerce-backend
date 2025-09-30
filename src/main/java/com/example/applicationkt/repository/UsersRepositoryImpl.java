package com.example.applicationkt.repository;

import com.example.applicationkt.model.Users;
import com.example.applicationkt.model.Usersdetail;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Optional;

@Repository
public class UsersRepositoryImpl implements UsersRepository {

    private final JdbcTemplate jdbcTemplate;
    private final UsersdetailRepository usersdetailRepository;

    public UsersRepositoryImpl(JdbcTemplate jdbcTemplate, UsersdetailRepository usersdetailRepository) {
        this.jdbcTemplate = jdbcTemplate;
        this.usersdetailRepository = usersdetailRepository;
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
    public Optional<Users> findByUsername(String username) {
        String sql = "SELECT * FROM \"Users\" WHERE username = ? LIMIT 1";
        return jdbcTemplate.query(sql, rowMapper, username).stream().findFirst();
    }

    @Override
    public Optional<Users> findByEmail(String email) {
        String sql = "SELECT * FROM \"Users\" WHERE email = ? LIMIT 1";
        return jdbcTemplate.query(sql, rowMapper, email).stream().findFirst();
    }

    @Override
    public Users save(Users user) {
        String sql = "INSERT INTO \"Users\" (username, password, email, role, active, \"createdAt\") VALUES (?, ?, ?, ?, ?, ?)";
        jdbcTemplate.update(sql,
                user.getUsername(),
                user.getPassword(),
                user.getEmail(),
                user.getRole(),
                user.getActive() != null ? user.getActive() : true,
                Timestamp.valueOf(user.getCreatedAt() != null ? user.getCreatedAt() : LocalDateTime.now())
        );

        // Lấy ID vừa insert
        String queryId = "SELECT id FROM \"Users\" WHERE username = ? LIMIT 1";
        Long userId = jdbcTemplate.queryForObject(queryId, Long.class, user.getUsername());
        user.setId(userId);

        // Tạo Usersdetail null tương ứng
        Usersdetail detail = new Usersdetail();
        detail.setUsers(user);
        usersdetailRepository.save(detail);

        return user;
    }

    @Override
    public Usersdetail save(Usersdetail detail) {
        return null;
    }

    @Override
    public void updateActive(Long id, Boolean active) {
        String sql = "UPDATE \"Users\" SET active = ? WHERE id = ?";
        jdbcTemplate.update(sql, active, id);
    }
}
