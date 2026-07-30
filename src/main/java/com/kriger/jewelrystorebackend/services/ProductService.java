package com.kriger.jewelrystorebackend.services;
import com.kriger.jewelrystorebackend.dao.CategoryDAO;
import com.kriger.jewelrystorebackend.dao.ProductDAO;
import com.kriger.jewelrystorebackend.models.Category;
import com.kriger.jewelrystorebackend.models.Product;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class ProductServices {
    private final ProductDAO productDAO;
    private final CategoryDAO categoryDAO;

    public ProductServices(ProductDAO productDAO, CategoryDAO categoryDAO) {
        this.productDAO = productDAO;
        this.categoryDAO = categoryDAO;
    }

    public List<Product> getAllProducts() {
        return this.productDAO.getAllProducts();
    }

    public Product getProductById(Long id) {
        return this.productDAO.getProductById(id);
    }

    public void removeProductById(Long id) {
        this.productDAO.removeProductById(id);
    }

    public void addProduct(Product product) {
        if (product.getCategory() == null || product.getCategory().getId() == null)
            throw new IllegalArgumentException("Cannot add a product without a valid Category ID.");
        Long categoryId = product.getCategory().getId();
        Category existingCategory = this.categoryDAO.getCategoryById(categoryId);
        if (existingCategory == null)
            throw new IllegalArgumentException("Category with ID " + categoryId + " does not exist!");
        this.productDAO.addProduct(product);
    }
}
