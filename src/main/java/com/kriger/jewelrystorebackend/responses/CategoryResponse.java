package com.kriger.jewelrystorebackend.responses;

import com.kriger.jewelrystorebackend.models.Category;

public class CategoryResponse extends BasicResponse{
    private Category category;

    public CategoryResponse(boolean success, String errorMessage, Category category) {
        super(success, errorMessage);
        this.category = category;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

}
