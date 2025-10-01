package com.example.applicationkt.service;

import com.example.applicationkt.model.Category;
import com.example.applicationkt.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }

    public Category getCategoryById(Long id) {
        return categoryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Category not found with id: " + id));
    }

    public Category createCategory(Category category) {
        return categoryRepository.save(category);
    }

    public Category updateCategory(Category category) {
        if (category.getId() == null) {
            throw new RuntimeException("Category id is required for update");
        }
        return categoryRepository.update(category);
    }

    public void deleteCategory(Long id) {
        boolean deleted = categoryRepository.deleteById(id);
        if (!deleted) {
            throw new RuntimeException("Failed to delete category with id: " + id);
        }
    }
}
