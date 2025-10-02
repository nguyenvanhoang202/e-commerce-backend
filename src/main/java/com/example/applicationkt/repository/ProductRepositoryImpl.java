package com.example.applicationkt.repository;

import com.example.applicationkt.model.Product;
import com.example.applicationkt.model.Category;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@Repository
public class ProductRepositoryImpl implements ProductRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private final RowMapper<Product> productRowMapper = (rs, rowNum) -> {
        Product p = new Product();
        p.setId(rs.getLong("id"));
        p.setName(rs.getString("name"));
        p.setSlug(rs.getString("slug"));
        p.setPrice(rs.getDouble("price"));
        p.setDiscountprice(rs.getDouble("discountprice"));
        p.setBrand(rs.getString("brand"));
        p.setImageUrl(rs.getString("imageUrl"));
        p.setDescription(rs.getString("description"));
        p.setStockquantity(rs.getInt("stockquantity"));
        p.setIsNew((Boolean) rs.getObject("isNew"));
        p.setIsHot((Boolean) rs.getObject("isHot"));
        p.setCreatedAt(rs.getTimestamp("createdAt").toLocalDateTime());

        Category category = new Category();
        category.setId(rs.getLong("category"));
        p.setCategory(category);

        return p;
    };

    @Override
    public List<Product> findAll() {
        String sql = "SELECT * FROM \"Product\"";
        return jdbcTemplate.query(sql, productRowMapper);
    }

    @Override
    public Optional<Product> findById(Long id) {
        String sql = "SELECT * FROM \"Product\" WHERE id = ?";
        List<Product> products = jdbcTemplate.query(sql, productRowMapper, id);
        return products.isEmpty() ? Optional.empty() : Optional.of(products.get(0));
    }

    @Override
    public Product save(Product product) {
        String sql = "INSERT INTO \"Product\"(name, slug, price, discountprice, brand, \"imageUrl\", description, stockquantity, \"isNew\", \"isHot\", category) " +
                "VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        jdbcTemplate.update(sql,
                product.getName(),
                product.getSlug(),
                product.getPrice(),
                product.getDiscountprice(),
                product.getBrand(),
                product.getImageUrl(),
                product.getDescription(),
                product.getStockquantity(),
                product.getIsNew(),
                product.getIsHot(),
                product.getCategory().getId()
        );
        return product;
    }


    @Override
    public Product update(Product product) {
        String sql = "UPDATE \"Product\" SET name=?, slug=?, price=?, discountprice=?, brand=?, \"imageUrl\"=?, description=?, stockquantity=?, \"isNew\"=?, \"isHot\"=?, category=? WHERE id=?";
        jdbcTemplate.update(sql,
                product.getName(), product.getSlug(), product.getPrice(), product.getDiscountprice(),
                product.getBrand(), product.getImageUrl(), product.getDescription(), product.getStockquantity(),
                product.getIsNew(), product.getIsHot(), product.getCategory().getId(), product.getId());
        return product;
    }

    @Override
    public boolean deleteById(Long id) {
        String sql = "DELETE FROM \"Product\" WHERE id=?";
        return jdbcTemplate.update(sql, id) > 0;
    }

    @Override
    public List<Product> findByCategoryId(Long categoryId) {
        String sql = "SELECT * FROM \"Product\" WHERE category=?";
        return jdbcTemplate.query(sql, productRowMapper, categoryId);
    }

    @Override
    public List<String> findAllDistinctBrands() {
        String sql = "SELECT DISTINCT brand FROM \"Product\" WHERE brand IS NOT NULL";
        return jdbcTemplate.queryForList(sql, String.class);
    }

    @Override
    public List<Product> findByBrand(String brand) {
        String sql = "SELECT * FROM \"Product\" WHERE brand = ?";
        return jdbcTemplate.query(sql, new Object[]{brand}, (rs, rowNum) -> {
            Product p = new Product();
            p.setId(rs.getLong("id"));
            p.setName(rs.getString("name"));
            p.setSlug(rs.getString("slug"));
            p.setPrice(rs.getDouble("price"));
            p.setDiscountprice(rs.getDouble("discountprice"));
            p.setBrand(rs.getString("brand"));
            p.setImageUrl(rs.getString("imageUrl"));
            p.setDescription(rs.getString("description"));
            p.setStockquantity(rs.getInt("stockquantity"));
            p.setIsNew(rs.getBoolean("isNew"));
            p.setIsHot(rs.getBoolean("isHot"));

            Category c = new Category();
            c.setId(rs.getLong("category"));  // map id vào category object
            p.setCategory(c);

            return p;
        });
    }
}

