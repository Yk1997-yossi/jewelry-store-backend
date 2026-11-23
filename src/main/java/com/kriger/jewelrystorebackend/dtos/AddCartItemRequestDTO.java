package com.kriger.jewelrystorebackend.dtos;

import java.math.BigDecimal;

public class AddCartItemRequestDTO {
    private Long variantId;
    private int quantity;
    private String metalType;
    private BigDecimal sizeValue;
    private String sizeUnit;

    public AddCartItemRequestDTO() {
    }

    public AddCartItemRequestDTO(Long variantId,int quantity,String metalType,BigDecimal sizeValue,String sizeUnit) {
        this.variantId = variantId;
        this.quantity = quantity;
        this.metalType = metalType;
        this.sizeValue = sizeValue;
        this.sizeUnit = sizeUnit;
    }

    public Long getVariantId() {
        return variantId;
    }

    public void setVariantId(Long variantId) {
        this.variantId = variantId;
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
}