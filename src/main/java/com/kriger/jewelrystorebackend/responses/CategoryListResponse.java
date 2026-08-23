package com.kriger.jewelrystorebackend.responses;

import com.kriger.jewelrystorebackend.models.Category;

import java.util.List;

public class CategoryListResponse extends BasicResponse{
    private List<Category> categories;

    public CategoryListResponse(boolean success, String errorMessage, List<Category> categories) {
        super(success, errorMessage);
        this.categories = categories;
    }

    public List<Category> getCategories() {
        return categories;
    }

    public void setCategories(List<Category> categories) {
        this.categories = categories;
    }
}
