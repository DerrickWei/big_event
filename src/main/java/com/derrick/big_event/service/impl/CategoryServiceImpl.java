package com.derrick.big_event.service.impl;

import com.derrick.big_event.mapper.CategoryMapper;
import com.derrick.big_event.pojo.Category;
import com.derrick.big_event.service.CategoryService;
import com.derrick.big_event.utils.ThreadLocalUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryMapper categoryMapper;

    @Override
    public void addNewCategory(Category category) {
        // Get user info from ThreadLocal
        Map<String, Object> map = ThreadLocalUtil.get();
        Integer userId = (Integer) map.get("id");

        category.setCreateTime(LocalDateTime.now());
        category.setUpdateTime(LocalDateTime.now());
        category.setCreateUser(userId);

        categoryMapper.saveNewCategory(category);
    }

    @Override
    public List<Category> getCategoriesByUser() {
        // Get user info from ThreadLocal
        Map<String, Object> map = ThreadLocalUtil.get();
        Integer userId = (Integer) map.get("id");

        return categoryMapper.getCategoriesByUser(userId);
    }

    @Override
    public Category getCategoryById(Integer id) {
        return categoryMapper.getCategoryById(id);
    }

    @Override
    public void updateCategory(Category category) {
        category.setUpdateTime(LocalDateTime.now());

        categoryMapper.updateCategory(category);
    }

    @Override
    public void deleteCategoryById(Integer id) {
        categoryMapper.deleteCategoryById(id);
    }
}
