package com.kriger.jewelrystorebackend.responses;
import com.kriger.jewelrystorebackend.models.Product;

public class ProductResponse extends BasicResponse{
    private Product product;

    public ProductResponse(boolean success,String errorMessage ,Product product){
        super(success,errorMessage);
        this.product = product;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }
}
