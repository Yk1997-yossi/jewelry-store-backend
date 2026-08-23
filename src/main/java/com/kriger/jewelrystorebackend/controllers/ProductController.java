package com.kriger.jewelrystorebackend.controllers;
import com.kriger.jewelrystorebackend.models.Product;
import com.kriger.jewelrystorebackend.responses.BasicResponse;
import com.kriger.jewelrystorebackend.services.ProductService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/products")
public class ProductController {
    private final ProductService productService;

    public ProductController(ProductService productService){
        this.productService = productService;
    }
    @GetMapping
    public BasicResponse getAllProducts(){
        return this.productService.getAllProducts();
    }
    @GetMapping("/{id}")
    public BasicResponse getProductById(@PathVariable Long id) {
        return this.productService.getProductById(id);
    }
    @PostMapping
    public BasicResponse addProduct(@RequestBody Product product){
        return this.productService.addProduct(product);
    }
    @DeleteMapping("/{id}")
    public BasicResponse removeProductById(@PathVariable Long id){
        return this.productService.removeProductById(id);
    }
}
