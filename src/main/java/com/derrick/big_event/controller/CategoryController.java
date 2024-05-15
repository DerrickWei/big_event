package com.derrick.big_event.controller;

import com.derrick.big_event.pojo.Category;
import com.derrick.big_event.pojo.Result;
import com.derrick.big_event.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/category")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @PostMapping
    public Result addNewCategory(@RequestBody @Validated(Category.Add.class) Category category) {
        categoryService.addNewCategory(category);

        return Result.success();
    }

    @GetMapping
    public Result<List<Category>> getCategoriesByUser() {
        List<Category> categories = categoryService.getCategoriesByUser();

        return Result.success(categories);
    }

    @GetMapping("/detail")
    public Result<Category> getCategoryById(@RequestParam("id") Integer id) {
        Category category = categoryService.getCategoryById(id);

        return Result.success(category);
    }

    @PutMapping
    public Result updateCategory(@RequestBody @Validated(Category.Update.class) Category category) {
        categoryService.updateCategory(category);

        return Result.success();
    }

    @DeleteMapping
    public Result deleteCategoryById(@RequestParam("id") Integer id) {
        // Check if category exist
        if (categoryService.getCategoryById(id) == null) {
            return Result.error("Category with id " + id + " not found");
        }

        categoryService.deleteCategoryById(id);

        return Result.success();
    }
}
