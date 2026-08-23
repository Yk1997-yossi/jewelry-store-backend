package com.kriger.jewelrystorebackend.controllers;

import com.kriger.jewelrystorebackend.models.ProductVariant;
import com.kriger.jewelrystorebackend.responses.BasicResponse;
import com.kriger.jewelrystorebackend.responses.ProductVariantListResponse;
import com.kriger.jewelrystorebackend.responses.ProductVariantResponse;
import com.kriger.jewelrystorebackend.services.ProductVariantService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/productVariant")
public class ProductVariantController {
    private final ProductVariantService productVariantService;

    public ProductVariantController(ProductVariantService productVariantService){
        this.productVariantService = productVariantService;
    }

    @PostMapping
    public ProductVariantResponse addVariant(@RequestBody ProductVariant variant) {
        return this.productVariantService.addVariant(variant);
    }

    @GetMapping("/product/{productId}")
    public ProductVariantListResponse getVariantsByProductId(@PathVariable Long productId){
        return this.productVariantService.getVariantsByProductId(productId);
    }

    @GetMapping("/{id}")
    public ProductVariantResponse getVariantById(@PathVariable Long id) {
        return this.productVariantService.getVariantById(id);
    }

    @PutMapping
    public ProductVariantResponse updateVariant(@RequestBody ProductVariant variant) {
        return this.productVariantService.updateVariant(variant);
    }

    @DeleteMapping("/{id}")
    public BasicResponse removeVariantById(@PathVariable Long id){
        return this.productVariantService.removeVariantById(id);
    }
}