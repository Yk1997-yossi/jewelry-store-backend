package com.kriger.jewelrystorebackend.responses;
import com.kriger.jewelrystorebackend.models.Product;
import java.util.List;

public class ProductListResponse extends BasicResponse{

    private List<Product> productList;

    public ProductListResponse(boolean success, String errorMessage, List<Product> productList) {
        super(success, errorMessage);
        this.productList = productList;
    }

    public List<Product> getProductList() {
        return productList;
    }

    public void setProductList(List<Product> productList) {
        this.productList = productList;
    }
}
