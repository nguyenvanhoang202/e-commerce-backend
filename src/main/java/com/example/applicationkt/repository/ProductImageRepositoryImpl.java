package com.example.applicationkt.repository;

import com.example.applicationkt.model.ProductImage;
import com.example.applicationkt.repository.ProductImageRepository;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;

@Repository
public class ProductImageRepositoryImpl implements ProductImageRepository {

    private final JdbcTemplate jdbcTemplate;

    public ProductImageRepositoryImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private final RowMapper<ProductImage> rowMapper = (rs, rowNum) -> {
        ProductImage image = new ProductImage();
        image.setId(rs.getLong("id"));
        image.setImageUrl(rs.getString("imageUrl"));
        return image;
    };

    @Override
    public int save(ProductImage image, Long productId) {
        String sql = "INSERT INTO \"ProductImage\"(\"imageUrl\", product) VALUES (?, ?) RETURNING id";
        return jdbcTemplate.queryForObject(sql, new Object[]{image.getImageUrl(), productId}, Integer.class);
    }

    @Override
    public List<ProductImage> findByProductId(Long productId) {
        String sql = "SELECT * FROM \"ProductImage\" WHERE product = ?";
        return jdbcTemplate.query(sql, rowMapper, productId);
    }

    @Override
    public int deleteById(Long id) {
        String sql = "DELETE FROM \"ProductImage\" WHERE id = ?";
        return jdbcTemplate.update(sql, id);
    }

    @Override
    public int deleteByProductId(Long productId) {
        String sql = "DELETE FROM \"ProductImage\" WHERE product = ?";
        return jdbcTemplate.update(sql, productId);
    }

    @Override
    public int updateImage(Long id, String imageUrl) {
        String sql = "UPDATE \"ProductImage\" SET \"imageUrl\"=? WHERE id=?";
        return jdbcTemplate.update(sql, imageUrl, id);
    }
}
