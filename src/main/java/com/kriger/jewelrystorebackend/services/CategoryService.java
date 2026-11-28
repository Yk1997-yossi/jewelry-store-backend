package com.kriger.jewelrystorebackend.services;

import com.kriger.jewelrystorebackend.dao.CategoryDAO;
import com.kriger.jewelrystorebackend.models.Category;
import com.kriger.jewelrystorebackend.responses.BasicResponse;
import com.kriger.jewelrystorebackend.responses.CategoryListResponse;
import com.kriger.jewelrystorebackend.responses.CategoryResponse;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Locale;

import static com.kriger.jewelrystorebackend.errors.CategoryErrors.*;

@Service
public class CategoryService {
    private final CategoryDAO categoryDAO;

    public CategoryService(CategoryDAO categoryDAO){
        this.categoryDAO = categoryDAO;
    }

    public CategoryListResponse getAllCategories(){
        List<Category> categories = this.categoryDAO.getAllCategories();
        if(categories == null)
            return new CategoryListResponse(false,CATEGORY_FETCH_FAILED,null);

        return new CategoryListResponse(true,null,categories);
    }

    public CategoryResponse getCategoryById(Long id){
        if(id == null || id <= 0)
            return new CategoryResponse(false,INVALID_CATEGORY_ID,null);

        Category category = this.categoryDAO.getCategoryById(id);
        if(category == null)
            return new CategoryResponse(false,CATEGORY_NOT_FOUND,null);

        return new CategoryResponse(true,null,category);
    }

    public CategoryResponse addCategory(Category category){
        if(category == null)
            return new CategoryResponse(false,INVALID_CATEGORY_NAME,null);

        if(!isValidCategoryName(category.getName()))
            return new CategoryResponse(false,INVALID_CATEGORY_NAME,null);

        if(!isValidCategorySlug(category.getSlug()))
            return new CategoryResponse(false,INVALID_CATEGORY_SLUG,null);

        category.setName(category.getName().trim());
        category.setSlug(category.getSlug().trim().toLowerCase(Locale.ROOT));

        Category existingCategory = this.categoryDAO.getCategoryBySlug(category.getSlug());
        if(existingCategory != null)
            return new CategoryResponse(false,CATEGORY_SLUG_ALREADY_EXISTS,null);

        int rowsAffected = this.categoryDAO.addCategory(category);
        if(rowsAffected == 0)
            return new CategoryResponse(false,CATEGORY_ADD_FAILED,null);

        return new CategoryResponse(true,null,category);
    }
    public CategoryResponse updateCategory(Long id,Category category){
        if(id == null || id <= 0)
            return new CategoryResponse(false,INVALID_CATEGORY_ID,null);

        if(category == null || !isValidCategoryName(category.getName()))
            return new CategoryResponse(false,INVALID_CATEGORY_NAME,null);

        if(!isValidCategorySlug(category.getSlug()))
            return new CategoryResponse(false,INVALID_CATEGORY_SLUG,null);

        Category existingCategory = this.categoryDAO.getCategoryById(id);
        if(existingCategory == null)
            return new CategoryResponse(false,CATEGORY_NOT_FOUND,null);

        category.setName(category.getName().trim());
        category.setSlug(category.getSlug().trim().toLowerCase(Locale.ROOT));

        Category categoryWithSameSlug = this.categoryDAO.getCategoryBySlug(category.getSlug());
        if(categoryWithSameSlug != null && !id.equals(categoryWithSameSlug.getId()))
            return new CategoryResponse(false,CATEGORY_SLUG_ALREADY_EXISTS,null);

        existingCategory.setName(category.getName());
        existingCategory.setSlug(category.getSlug());

        int rowsAffected = this.categoryDAO.updateCategory(existingCategory);
        if(rowsAffected == 0)
            return new CategoryResponse(false,CATEGORY_UPDATE_FAILED,null);

        return getCategoryById(id);
    }
    public BasicResponse removeCategoryById(Long id){
        if(id == null || id <= 0)
            return new BasicResponse(false,INVALID_CATEGORY_ID);

        Category category = this.categoryDAO.getCategoryById(id);
        if(category == null)
            return new BasicResponse(false,CATEGORY_NOT_FOUND);

        int rowsAffected = this.categoryDAO.removeCategoryById(id);
        if(rowsAffected == 0)
            return new BasicResponse(false,CATEGORY_DELETE_FAILED);

        return new BasicResponse(true,null);
    }

    private boolean isValidCategoryName(String name){
        if(name == null || name.trim().isEmpty())
            return false;

        return name.trim().length() >= 2 && name.trim().length() <= 100;
    }

    private boolean isValidCategorySlug(String slug){
        if(slug == null || slug.trim().isEmpty())
            return false;

        String normalizedSlug = slug.trim().toLowerCase(Locale.ROOT);

        if(normalizedSlug.length() > 100)
            return false;

        String slugRegex = "^[a-z0-9]+(?:-[a-z0-9]+)*$";
        return normalizedSlug.matches(slugRegex);
    }
}