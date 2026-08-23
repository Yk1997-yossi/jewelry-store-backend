package com.kriger.jewelrystorebackend.responses;

import com.kriger.jewelrystorebackend.models.ProductVariant;
import java.util.List;

public class ProductVariantListResponse extends BasicResponse {
    private List<ProductVariant> variantList;

    public ProductVariantListResponse(boolean success, String errorMessage, List<ProductVariant> variantList) {
        super(success, errorMessage);
        this.variantList = variantList;
    }

    public List<ProductVariant> getVariantList() {
        return variantList;
    }

    public void setVariantList(List<ProductVariant> variantList) {
        this.variantList = variantList;
    }
}