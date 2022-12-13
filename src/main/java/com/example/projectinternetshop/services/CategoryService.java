package com.example.projectinternetshop.services;

import com.example.projectinternetshop.repositories.CategoryRepository;
import com.example.projectinternetshop.models.Category;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class CategoryService {

    private final CategoryRepository categoryRepository;
    @Autowired
    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    public List<Category> findAll() {
        return categoryRepository.findAll();
    }

    public Category getCategoryById(int id) {
        return categoryRepository.findById(id).orElse(null);
    }

    @Transactional
    public void saveCategories(Category category) {
        categoryRepository.save(category);
    }
    @Transactional
    public void updateCategories(int id, Category category) {
        category.setId(id);
        categoryRepository.save(category);
    }
}
