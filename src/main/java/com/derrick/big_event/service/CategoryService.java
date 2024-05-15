package com.derrick.big_event.service;

import com.derrick.big_event.pojo.Category;

import java.util.List;

public interface CategoryService {
    void addNewCategory(Category category);

    List<Category> getCategoriesByUser();

    Category getCategoryById(Integer id);

    void updateCategory(Category category);

    void deleteCategoryById(Integer id);
}
