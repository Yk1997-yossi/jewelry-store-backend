package com.kriger.jewelrystorebackend.responses;

import com.kriger.jewelrystorebackend.models.ProductVariant;

public class ProductVariantResponse extends BasicResponse {
    private ProductVariant variant;

    public ProductVariantResponse(boolean success, String errorMessage, ProductVariant variant) {
        super(success, errorMessage);
        this.variant = variant;
    }

    public ProductVariant getVariant() {
        return variant;
    }

    public void setVariant(ProductVariant variant) {
        this.variant = variant;
    }
}