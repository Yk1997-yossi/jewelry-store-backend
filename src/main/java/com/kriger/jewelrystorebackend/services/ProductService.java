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
import java.util.List;

import static com.kriger.jewelrystorebackend.errors.ProductErrors.*;

@Service
public class ProductService {
    private final ProductDAO productDAO;
    private final CategoryDAO categoryDAO;
    private final MediaDAO mediaDAO;

    public ProductService(ProductDAO productDAO, CategoryDAO categoryDAO, MediaDAO mediaDAO) {
        this.productDAO = productDAO;
        this.categoryDAO = categoryDAO;
        this.mediaDAO = mediaDAO;
    }

    public ProductListResponse getAllProducts() {
        List<Product> productList = this.productDAO.getAllProducts();
        if(productList == null || productList.isEmpty())
            return new ProductListResponse(false, EMPTY_PRODUCT_LIST, null);

        for (int i = 0; i < productList.size(); i++){
            List<Media> mediaList = this.mediaDAO.getMediaListByProductId(productList.get(i).getId());
            productList.get(i).setMediaList(mediaList);
        }
        return new ProductListResponse(true, null, productList);
    }

    public ProductResponse getProductById(Long id) {
        if (id == null || id <= 0) {
            return new ProductResponse(false, INVALID_PRODUCT_ID, null);
        }

        Product product = productDAO.getProductById(id);
        if(product == null)
            return new ProductResponse(false, PRODUCT_NOT_FOUND, null);

        List<Media> mediaList = this.mediaDAO.getMediaListByProductId(id);
        product.setMediaList(mediaList);

        return new ProductResponse(true, null, product);
    }

    public BasicResponse removeProductById(Long id) {
        if (id == null || id <= 0)
            return new BasicResponse(false, INVALID_PRODUCT_ID);

        Product existingProduct = this.productDAO.getProductById(id);
        if (existingProduct == null)
            return new BasicResponse(false, PRODUCT_NOT_FOUND);

        int rowsAffected = this.productDAO.removeProductById(id);
        if (rowsAffected == 0)
            return new BasicResponse(false, DATABASE_ERROR); // הוספתי פה את השגיאה, תוודא שהיא קיימת בקובץ

        return new BasicResponse(true, null);
    }

    public ProductResponse addProduct(Product product) {
        if (product.getCategory() == null || product.getCategory().getId() == null)
            return new ProductResponse(false, INVALID_CATEGORY_ID, null);

        Long categoryId = product.getCategory().getId();
        Category existingCategory = this.categoryDAO.getCategoryById(categoryId);
        if (existingCategory == null)
            return new ProductResponse(false, CATEGORY_NOT_FOUND, null);

        int rowsAffected = this.productDAO.addProduct(product);
        if (rowsAffected == 0)
            return new ProductResponse(false, PRODUCT_ADD_FAILED, null); // הוספתי פה את השגיאה, תוודא שהיא קיימת בקובץ

        return new ProductResponse(true, null, product);
    }
}