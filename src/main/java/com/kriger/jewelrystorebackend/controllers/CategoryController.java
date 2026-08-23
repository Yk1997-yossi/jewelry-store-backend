package com.kriger.jewelrystorebackend.controllers;

import com.kriger.jewelrystorebackend.models.Category;
import com.kriger.jewelrystorebackend.responses.BasicResponse;
import com.kriger.jewelrystorebackend.responses.CategoryListResponse;
import com.kriger.jewelrystorebackend.responses.CategoryResponse;
import com.kriger.jewelrystorebackend.services.CategoryService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/categories")
public class CategoryController {
    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping
    public CategoryListResponse getAllCategories(){
        return this.categoryService.getAllCategories();
    }

    @GetMapping("/{id}")
    public CategoryResponse getCategoryById(@PathVariable Long id){
        return this.categoryService.getCategoryById(id);
    }

    @PostMapping
    public CategoryResponse addCategory(@RequestBody Category category){
        return this.categoryService.addCategory(category);
    }

    @DeleteMapping("/{id}")
    public BasicResponse removeCategoryById(@PathVariable Long id){
        return this.categoryService.removeCategoryById(id);
    }
}