package com.kriger.jewelrystorebackend.services;

import com.kriger.jewelrystorebackend.dao.CategoryDAO;
import com.kriger.jewelrystorebackend.dao.MediaDAO;
import com.kriger.jewelrystorebackend.dao.ProductDAO;
import com.kriger.jewelrystorebackend.models.Category;
import com.kriger.jewelrystorebackend.models.Media;
import com.kriger.jewelrystorebackend.models.Product;
import com.kriger.jewelrystorebackend.responses.BasicResponse;
import com.kriger.jewelrystorebackend.responses.ProductListResponse;
import com.kriger.jewelrystorebackend.responses.ProductResponse;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.List;
import java.util.Locale;

import static com.kriger.jewelrystorebackend.errors.ProductErrors.*;

@Service
public class ProductService {
    private final ProductDAO productDAO;
    private final CategoryDAO categoryDAO;
    private final MediaDAO mediaDAO;

    public ProductService(ProductDAO productDAO,CategoryDAO categoryDAO,MediaDAO mediaDAO){
        this.productDAO = productDAO;
        this.categoryDAO = categoryDAO;
        this.mediaDAO = mediaDAO;
    }

    public ProductListResponse getAllProducts(){
        List<Product> productList = this.productDAO.getAllProducts();
        if(productList == null)
            return new ProductListResponse(false,DATABASE_ERROR,null);

        for(int i = 0; i < productList.size(); i++){
            List<Media> mediaList = this.mediaDAO.getMediaListByProductId(productList.get(i).getId());
            if(mediaList == null)
                return new ProductListResponse(false,DATABASE_ERROR,null);

            productList.get(i).setMediaList(mediaList);
        }

        return new ProductListResponse(true,null,productList);
    }

    public ProductResponse getProductById(Long id){
        if(id == null || id <= 0)
            return new ProductResponse(false,INVALID_PRODUCT_ID,null);

        Product product = this.productDAO.getProductById(id);
        if(product == null)
            return new ProductResponse(false,PRODUCT_NOT_FOUND,null);

        List<Media> mediaList = this.mediaDAO.getMediaListByProductId(id);
        if(mediaList == null)
            return new ProductResponse(false,DATABASE_ERROR,null);

        product.setMediaList(mediaList);

        return new ProductResponse(true,null,product);
    }

    public ProductResponse addProduct(Product product){
        if(product == null)
            return new ProductResponse(false,INVALID_PRODUCT,null);

        if(product.getCategory() == null || product.getCategory().getId() == null || product.getCategory().getId() <= 0)
            return new ProductResponse(false,INVALID_CATEGORY_ID,null);

        Category category = this.categoryDAO.getCategoryById(product.getCategory().getId());
        if(category == null)
            return new ProductResponse(false,CATEGORY_NOT_FOUND,null);

        if(!isValidProductName(product.getName()))
            return new ProductResponse(false,INVALID_PRODUCT_NAME,null);

        if(!isValidBaseSku(product.getBaseSku()))
            return new ProductResponse(false,INVALID_BASE_SKU,null);

        if(!isValidProductSlug(product.getSlug()))
            return new ProductResponse(false,INVALID_PRODUCT_SLUG,null);

        product.setName(product.getName().trim());
        product.setBaseSku(product.getBaseSku().trim().toUpperCase(Locale.ROOT));
        product.setSlug(product.getSlug().trim().toLowerCase(Locale.ROOT));
        product.setCategory(category);

        if(this.productDAO.isBaseSkuExists(product.getBaseSku()))
            return new ProductResponse(false,PRODUCT_SKU_ALREADY_EXISTS,null);

        if(this.productDAO.isSlugExists(product.getSlug()))
            return new ProductResponse(false,PRODUCT_SLUG_ALREADY_EXISTS,null);

        if(product.getDynamicDiamondsCount() == null)
            product.setDynamicDiamondsCount(0);

        if(product.getFixedDiamondsCount() == null)
            product.setFixedDiamondsCount(0);

        if(product.getFixedDiamondsWeight() == null)
            product.setFixedDiamondsWeight(BigDecimal.ZERO);

        if(product.getIsActive() == null)
            product.setIsActive(true);

        if(product.getDynamicDiamondsCount() < 0 || product.getFixedDiamondsCount() < 0)
            return new ProductResponse(false,INVALID_DIAMONDS_COUNT,null);

        if(product.getFixedDiamondsWeight().compareTo(BigDecimal.ZERO) < 0)
            return new ProductResponse(false,INVALID_DIAMONDS_WEIGHT,null);

        if(product.getDescription() != null)
            product.setDescription(product.getDescription().trim());

        int rowsAffected = this.productDAO.addProduct(product);
        if(rowsAffected == 0)
            return new ProductResponse(false,PRODUCT_ADD_FAILED,null);

        return new ProductResponse(true,null,product);
    }

    public ProductResponse updateProduct(Long id,Product product){
        if(id == null || id <= 0)
            return new ProductResponse(false,INVALID_PRODUCT_ID,null);

        if(product == null)
            return new ProductResponse(false,INVALID_PRODUCT,null);

        Product existingProduct = this.productDAO.getProductById(id);
        if(existingProduct == null)
            return new ProductResponse(false,PRODUCT_NOT_FOUND,null);

        if(product.getCategory() == null || product.getCategory().getId() == null || product.getCategory().getId() <= 0)
            return new ProductResponse(false,INVALID_CATEGORY_ID,null);

        Category category = this.categoryDAO.getCategoryById(product.getCategory().getId());
        if(category == null)
            return new ProductResponse(false,CATEGORY_NOT_FOUND,null);

        if(!isValidProductName(product.getName()))
            return new ProductResponse(false,INVALID_PRODUCT_NAME,null);

        if(!isValidBaseSku(product.getBaseSku()))
            return new ProductResponse(false,INVALID_BASE_SKU,null);

        if(!isValidProductSlug(product.getSlug()))
            return new ProductResponse(false,INVALID_PRODUCT_SLUG,null);

        product.setName(product.getName().trim());
        product.setBaseSku(product.getBaseSku().trim().toUpperCase(Locale.ROOT));
        product.setSlug(product.getSlug().trim().toLowerCase(Locale.ROOT));
        product.setCategory(category);
        product.setId(id);

        try{
            if(this.productDAO.isBaseSkuUsedByAnotherProduct(product.getBaseSku(),id))
                return new ProductResponse(false,PRODUCT_SKU_ALREADY_EXISTS,null);

            if(this.productDAO.isSlugUsedByAnotherProduct(product.getSlug(),id))
                return new ProductResponse(false,PRODUCT_SLUG_ALREADY_EXISTS,null);
        }catch(SQLException e){
            System.err.println("Error checking product uniqueness:");
            e.printStackTrace();
            return new ProductResponse(false,PRODUCT_UPDATE_FAILED,null);
        }

        if(product.getDynamicDiamondsCount() == null)
            product.setDynamicDiamondsCount(existingProduct.getDynamicDiamondsCount());

        if(product.getFixedDiamondsCount() == null)
            product.setFixedDiamondsCount(existingProduct.getFixedDiamondsCount());

        if(product.getFixedDiamondsWeight() == null)
            product.setFixedDiamondsWeight(existingProduct.getFixedDiamondsWeight() == null ? BigDecimal.ZERO : existingProduct.getFixedDiamondsWeight());

        if(product.getIsActive() == null)
            product.setIsActive(existingProduct.getIsActive());

        if(product.getDynamicDiamondsCount() < 0 || product.getFixedDiamondsCount() < 0)
            return new ProductResponse(false,INVALID_DIAMONDS_COUNT,null);

        if(product.getFixedDiamondsWeight().compareTo(BigDecimal.ZERO) < 0)
            return new ProductResponse(false,INVALID_DIAMONDS_WEIGHT,null);

        if(product.getDescription() != null)
            product.setDescription(product.getDescription().trim());

        int rowsAffected = this.productDAO.updateProduct(product);
        if(rowsAffected == 0)
            return new ProductResponse(false,PRODUCT_UPDATE_FAILED,null);

        return getProductById(id);
    }

    public BasicResponse removeProductById(Long id){
        if(id == null || id <= 0)
            return new BasicResponse(false,INVALID_PRODUCT_ID);

        Product product = this.productDAO.getProductById(id);
        if(product == null)
            return new BasicResponse(false,PRODUCT_NOT_FOUND);

        int rowsAffected = this.productDAO.removeProductById(id);
        if(rowsAffected == 0)
            return new BasicResponse(false,PRODUCT_DELETE_FAILED);

        return new BasicResponse(true,null);
    }

    private boolean isValidProductName(String name){
        if(name == null || name.trim().isEmpty())
            return false;

        return name.trim().length() >= 2 && name.trim().length() <= 150;
    }

    private boolean isValidBaseSku(String baseSku){
        if(baseSku == null || baseSku.trim().isEmpty())
            return false;

        String normalizedBaseSku = baseSku.trim().toUpperCase(Locale.ROOT);

        if(normalizedBaseSku.length() > 50)
            return false;

        String baseSkuRegex = "^[A-Z0-9]+(?:[-_][A-Z0-9]+)*$";
        return normalizedBaseSku.matches(baseSkuRegex);
    }

    private boolean isValidProductSlug(String slug){
        if(slug == null || slug.trim().isEmpty())
            return false;

        String normalizedSlug = slug.trim().toLowerCase(Locale.ROOT);

        if(normalizedSlug.length() > 150)
            return false;

        String slugRegex = "^[a-z0-9]+(?:-[a-z0-9]+)*$";
        return normalizedSlug.matches(slugRegex);
    }
}