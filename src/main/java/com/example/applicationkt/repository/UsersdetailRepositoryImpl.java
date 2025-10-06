package com.example.applicationkt.repository;

import com.example.applicationkt.model.Users;
import com.example.applicationkt.model.Usersdetail;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.Date;
import java.util.List;
import java.util.Optional;

@Repository
public class UsersdetailRepositoryImpl implements UsersdetailRepository {

    private final JdbcTemplate jdbcTemplate;

    public UsersdetailRepositoryImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private final RowMapper<Usersdetail> rowMapper = (rs, rowNum) -> {
        Usersdetail ud = new Usersdetail();
        ud.setId(rs.getLong("id"));
        ud.setFullName(rs.getString("fullName"));
        ud.setPhone(rs.getString("phone"));
        ud.setAddress(rs.getString("address"));
        ud.setAvatar(rs.getString("avatar"));
        if (rs.getDate("birthday") != null) ud.setBirthday(rs.getDate("birthday").toLocalDate());
        ud.setGender(rs.getString("gender"));
        ud.setUsers(new Users(rs.getLong("users")));
        return ud;
    };

    @Override
    public Optional<Usersdetail> findById(Long id) {
        return Optional.empty();
    }

    @Override
    public Optional<Usersdetail> findByUserId(Long userId) {
        String sql = "SELECT * FROM \"Usersdetail\" WHERE users = ? LIMIT 1";
        List<Usersdetail> list = jdbcTemplate.query(sql, rowMapper, userId);
        return list.stream().findFirst();
    }

    @Override
    public Usersdetail save(Usersdetail detail) {
        if (detail.getUsers() == null || detail.getUsers().getId() == null) {
            throw new RuntimeException("Users in Usersdetail cannot be null");
        }

        // Chỉ insert user_id
        String sql = "INSERT INTO \"Usersdetail\" (users) VALUES (?)";
        jdbcTemplate.update(sql, detail.getUsers().getId());

        // Lấy ID vừa insert
        String queryId = "SELECT id FROM \"Usersdetail\" WHERE users = ? LIMIT 1";
        Long detailId = jdbcTemplate.queryForObject(queryId, Long.class, detail.getUsers().getId());
        detail.setId(detailId);

        return detail;
    }

    @Override
    public void update(Usersdetail detail) {
        if (detail.getUsers() == null || detail.getUsers().getId() == null) {
            throw new RuntimeException("UserId không hợp lệ");
        }
        String sql = "UPDATE \"Usersdetail\" SET \"fullName\" = ?, phone = ?, address = ?, avatar = ?, birthday = ?, gender = ? WHERE users = ?";
        jdbcTemplate.update(sql,
                detail.getFullName(),
                detail.getPhone(),
                detail.getAddress(),
                detail.getAvatar(),
                detail.getBirthday() != null ? Date.valueOf(detail.getBirthday()) : null,
                detail.getGender(),
                detail.getUsers().getId());
    }

    @Override
    public void deleteByUserId(Long userId) {
        String sql = "DELETE FROM \"Usersdetail\" WHERE users = ?";
        jdbcTemplate.update(sql, userId);
    }

    @Override
    public Integer countUsersById(Long userId) {
        return 0;
    }
}
