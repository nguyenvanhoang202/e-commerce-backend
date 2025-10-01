package com.example.applicationkt.repository;

import com.example.applicationkt.model.Category;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@Repository
public class CategoryRepositoryImpl implements CategoryRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private final RowMapper<Category> categoryRowMapper = new RowMapper<Category>() {
        @Override
        public Category mapRow(ResultSet rs, int rowNum) throws SQLException {
            Category c = new Category();
            c.setId(rs.getLong("id"));
            c.setName(rs.getString("name"));
            c.setSlug(rs.getString("slug"));
            c.setDescription(rs.getString("description"));
            return c;
        }
    };

    @Override
    public List<Category> findAll() {
        String sql = "SELECT * FROM \"Category\"";
        return jdbcTemplate.query(sql, categoryRowMapper);
    }

    @Override
    public Optional<Category> findById(Long id) {
        String sql = "SELECT * FROM \"Category\" WHERE id=?";
        List<Category> list = jdbcTemplate.query(sql, categoryRowMapper, id);
        return list.isEmpty() ? Optional.empty() : Optional.of(list.get(0));
    }

    @Override
    public Category save(Category category) {
        String sql = "INSERT INTO \"Category\"(name, slug, description) VALUES(?, ?, ?)";
        jdbcTemplate.update(sql, category.getName(), category.getSlug(), category.getDescription());
        return category;
    }

    @Override
    public Category update(Category category) {
        String sql = "UPDATE \"Category\" SET name=?, slug=?, description=? WHERE id=?";
        jdbcTemplate.update(sql, category.getName(), category.getSlug(), category.getDescription(), category.getId());
        return category;
    }

    @Override
    public boolean deleteById(Long id) {
        String sql = "DELETE FROM \"Category\" WHERE id=?";
        return jdbcTemplate.update(sql, id) > 0;
    }
}
