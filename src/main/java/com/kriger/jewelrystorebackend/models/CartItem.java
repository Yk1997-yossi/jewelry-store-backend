package com.kriger.jewelrystorebackend.models;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class CartItem {
    private Long id;
    private ProductVariant variant;
    private int quantity = 1;
    private String metalType;
    private BigDecimal sizeValue;
    private String sizeUnit;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public CartItem() {}

    public CartItem(Long id, ProductVariant variant, int quantity, String metalType, BigDecimal sizeValue, String sizeUnit, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = id;
        this.variant = variant;
        this.quantity = quantity;
        this.metalType = metalType;
        this.sizeValue = sizeValue;
        this.sizeUnit = sizeUnit;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ProductVariant getVariant() {
        return variant;
    }

    public void setVariant(ProductVariant variant) {
        this.variant = variant;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getMetalType() {
        return metalType;
    }

    public void setMetalType(String metalType) {
        this.metalType = metalType;
    }

    public BigDecimal getSizeValue() {
        return sizeValue;
    }

    public void setSizeValue(BigDecimal sizeValue) {
        this.sizeValue = sizeValue;
    }

    public String getSizeUnit() {
        return sizeUnit;
    }

    public void setSizeUnit(String sizeUnit) {
        this.sizeUnit = sizeUnit;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }
}