package com.kriger.jewelrystorebackend.services;
import com.kriger.jewelrystorebackend.dao.ProductVariantDAO;
import com.kriger.jewelrystorebackend.models.ProductVariant;
import com.kriger.jewelrystorebackend.responses.BasicResponse;
import com.kriger.jewelrystorebackend.responses.ProductVariantListResponse;
import com.kriger.jewelrystorebackend.responses.ProductVariantResponse;
import org.springframework.stereotype.Service;
import java.util.List;
import static com.kriger.jewelrystorebackend.errors.ProductVariantErrors.*;

@Service
public class ProductVariantService {
    private final ProductVariantDAO productVariantDAO;

    public ProductVariantService(ProductVariantDAO productVariantDAO){
        this.productVariantDAO = productVariantDAO;
    }

    public ProductVariantResponse addVariant(ProductVariant variant){
        if (variant.getProductId() == null || variant.getProductId() <= 0) {
            return new ProductVariantResponse(false, INVALID_PRODUCT_ID, null);
        }

        int rowsAffected = this.productVariantDAO.addVariant(variant);
        if (rowsAffected == 0) {
            return new ProductVariantResponse(false, VARIANT_ADD_FAILED, null);
        }

        return new ProductVariantResponse(true, null, variant);
    }

    public ProductVariantListResponse getVariantsByProductId(Long productId){
        if (productId == null || productId <= 0) {
            return new ProductVariantListResponse(false, INVALID_PRODUCT_ID, null);
        }

        List<ProductVariant> variantList = this.productVariantDAO.getVariantsByProductId(productId);
        if (variantList == null || variantList.isEmpty()) {
            return new ProductVariantListResponse(false, EMPTY_VARIANT_LIST, null);
        }

        return new ProductVariantListResponse(true, null, variantList);
    }

    public ProductVariantResponse getVariantById(Long id) {
        ProductVariant variant = this.productVariantDAO.getVariantById(id);
        if (variant == null) {
            return new ProductVariantResponse(false, VARIANT_NOT_FOUND, null);
        }

        return new ProductVariantResponse(true, null, variant);
    }

    public ProductVariantResponse updateVariant(ProductVariant variant) {
        if (variant.getId() == null) {
            return new ProductVariantResponse(false, VARIANT_NOT_FOUND, null);
        }

        int rowsAffected = this.productVariantDAO.updateVariant(variant);
        if (rowsAffected == 0) {
            return new ProductVariantResponse(false, VARIANT_UPDATE_FAILED, null);
        }

        return new ProductVariantResponse(true, null, variant);
    }

    public BasicResponse removeVariantById(Long id){
        int rowsAffected = this.productVariantDAO.removeVariantById(id);
        if (rowsAffected == 0) {
            return new BasicResponse(false, VARIANT_NOT_FOUND);
        }

        return new BasicResponse(true, null);
    }
}