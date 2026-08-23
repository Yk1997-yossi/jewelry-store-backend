package com.kriger.jewelrystorebackend.services;

import com.kriger.jewelrystorebackend.dao.CategoryDAO;
import com.kriger.jewelrystorebackend.models.Category;
import com.kriger.jewelrystorebackend.responses.BasicResponse;
import com.kriger.jewelrystorebackend.responses.CategoryListResponse;
import com.kriger.jewelrystorebackend.responses.CategoryResponse;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.kriger.jewelrystorebackend.errors.CategoryErrors.*;

@Service
public class CategoryService {
    private final CategoryDAO categoryDAO;

    public CategoryService(CategoryDAO categoryDAO){
        this.categoryDAO = categoryDAO;
    }

    public CategoryListResponse getAllCategories(){
        List<Category> categories = this.categoryDAO.getAllCategories();
        if(categories == null || categories.isEmpty())
            return new CategoryListResponse(false, CATEGORIES_NOT_FOUND, null);
        return new CategoryListResponse(true, null, categories);
    }

    public CategoryResponse getCategoryById(Long id) {
        Category category = this.categoryDAO.getCategoryById(id);

        if (category == null)
            return new CategoryResponse(false, CATEGORY_NOT_FOUND, null);
        return new CategoryResponse(true, null, category);
    }

    public CategoryResponse addCategory(Category categoryToAdd) {
        int rowsAffected = this.categoryDAO.addCategory(categoryToAdd);

        if (rowsAffected == 0)
            return new CategoryResponse(false, CATEGORY_ADD_FAILED, null);
        return new CategoryResponse(true, null, categoryToAdd);
    }

    public BasicResponse removeCategoryById(Long id) {
        int rowsAffected = this.categoryDAO.removeCategoryById(id);

        if (rowsAffected == 0)
            return new BasicResponse(false, CATEGORY_NOT_FOUND);
        return new BasicResponse(true, null);
    }
}