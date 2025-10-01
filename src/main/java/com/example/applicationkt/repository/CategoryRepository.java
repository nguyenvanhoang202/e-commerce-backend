package com.example.applicationkt.repository;

import com.example.applicationkt.model.Category;
import java.util.List;
import java.util.Optional;

public interface CategoryRepository {
    List<Category> findAll();
    Optional<Category> findById(Long id);
    Category save(Category category);
    Category update(Category category);
    boolean deleteById(Long id);
}
