package com.kriger.jewelrystorebackend.services;

import com.kriger.jewelrystorebackend.dao.ProductDAO;
import com.kriger.jewelrystorebackend.dao.ProductVariantDAO;
import com.kriger.jewelrystorebackend.models.Product;
import com.kriger.jewelrystorebackend.models.ProductVariant;
import com.kriger.jewelrystorebackend.responses.BasicResponse;
import com.kriger.jewelrystorebackend.responses.ProductVariantListResponse;
import com.kriger.jewelrystorebackend.responses.ProductVariantResponse;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

import static com.kriger.jewelrystorebackend.errors.ProductVariantErrors.*;

@Service
public class ProductVariantService {
    private final ProductVariantDAO productVariantDAO;
    private final ProductDAO productDAO;

    public ProductVariantService(ProductVariantDAO productVariantDAO,ProductDAO productDAO){
        this.productVariantDAO = productVariantDAO;
        this.productDAO = productDAO;
    }

    public ProductVariantResponse addVariant(ProductVariant variant){
        if(variant == null)
            return new ProductVariantResponse(false,INVALID_VARIANT,null);

        if(variant.getProductId() == null || variant.getProductId() <= 0)
            return new ProductVariantResponse(false,INVALID_PRODUCT_ID,null);

        Product product = this.productDAO.getProductById(variant.getProductId());
        if(product == null)
            return new ProductVariantResponse(false,PRODUCT_NOT_FOUND,null);

        String validationError = getVariantValidationError(variant);
        if(validationError != null)
            return new ProductVariantResponse(false,validationError,null);

        int rowsAffected = this.productVariantDAO.addVariant(variant);
        if(rowsAffected == 0)
            return new ProductVariantResponse(false,VARIANT_ADD_FAILED,null);

        return new ProductVariantResponse(true,null,variant);
    }

    public ProductVariantListResponse getVariantsByProductId(Long productId){
        if(productId == null || productId <= 0)
            return new ProductVariantListResponse(false,INVALID_PRODUCT_ID,null);

        Product product = this.productDAO.getProductById(productId);
        if(product == null)
            return new ProductVariantListResponse(false,PRODUCT_NOT_FOUND,null);

        List<ProductVariant> variantList = this.productVariantDAO.getVariantsByProductId(productId);
        if(variantList == null)
            return new ProductVariantListResponse(false,DATABASE_ERROR,null);

        return new ProductVariantListResponse(true,null,variantList);
    }

    public ProductVariantResponse getVariantById(Long id){
        if(id == null || id <= 0)
            return new ProductVariantResponse(false,INVALID_VARIANT_ID,null);

        ProductVariant variant = this.productVariantDAO.getVariantById(id);
        if(variant == null)
            return new ProductVariantResponse(false,VARIANT_NOT_FOUND,null);

        return new ProductVariantResponse(true,null,variant);
    }

    public ProductVariantResponse updateVariant(ProductVariant variant){
        if(variant == null)
            return new ProductVariantResponse(false,INVALID_VARIANT,null);

        if(variant.getId() == null || variant.getId() <= 0)
            return new ProductVariantResponse(false,INVALID_VARIANT_ID,null);

        ProductVariant existingVariant = this.productVariantDAO.getVariantById(variant.getId());
        if(existingVariant == null)
            return new ProductVariantResponse(false,VARIANT_NOT_FOUND,null);

        String validationError = getVariantValidationError(variant);
        if(validationError != null)
            return new ProductVariantResponse(false,validationError,null);

        variant.setProductId(existingVariant.getProductId());

        int rowsAffected = this.productVariantDAO.updateVariant(variant);
        if(rowsAffected == 0)
            return new ProductVariantResponse(false,VARIANT_UPDATE_FAILED,null);

        return new ProductVariantResponse(true,null,variant);
    }

    public BasicResponse removeVariantById(Long id){
        if(id == null || id <= 0)
            return new BasicResponse(false,INVALID_VARIANT_ID);

        ProductVariant variant = this.productVariantDAO.getVariantById(id);
        if(variant == null)
            return new BasicResponse(false,VARIANT_NOT_FOUND);

        int rowsAffected = this.productVariantDAO.removeVariantById(id);
        if(rowsAffected == 0)
            return new BasicResponse(false,VARIANT_DELETE_FAILED);

        return new BasicResponse(true,null);
    }

    private String getVariantValidationError(ProductVariant variant){
        if(variant.getPrice() == null ||
                variant.getPrice().compareTo(BigDecimal.ZERO) < 0)
            return INVALID_VARIANT_PRICE;

        if(variant.getStockQuantity() == null ||
                variant.getStockQuantity() < 0)
            return INVALID_STOCK_QUANTITY;

        if(variant.getWeightPerStone() != null &&
                variant.getWeightPerStone().compareTo(BigDecimal.ZERO) < 0)
            return INVALID_WEIGHT_PER_STONE;

        if(variant.getTotalCarat() != null &&
                variant.getTotalCarat().compareTo(BigDecimal.ZERO) < 0)
            return INVALID_TOTAL_CARAT;

        return null;
    }
}
