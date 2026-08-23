package com.kriger.jewelrystorebackend.models;
import java.math.BigDecimal;
import java.time.LocalDateTime;

public class ProductVariant {

    private Long id;
    private Long productId;
    private BigDecimal weightPerStone;
    private BigDecimal totalCarat;
    private BigDecimal price;
    private Integer stockQuantity;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public ProductVariant() {
    }

    public ProductVariant(Long id, Long productId, BigDecimal weightPerStone, BigDecimal totalCarat, BigDecimal price, Integer stockQuantity, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = id;
        this.productId = productId;
        this.weightPerStone = weightPerStone;
        this.totalCarat = totalCarat;
        this.price = price;
        this.stockQuantity = stockQuantity;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public BigDecimal getWeightPerStone() {
        return weightPerStone;
    }

    public void setWeightPerStone(BigDecimal weightPerStone) {
        this.weightPerStone = weightPerStone;
    }

    public BigDecimal getTotalCarat() {
        return totalCarat;
    }

    public void setTotalCarat(BigDecimal totalCarat) {
        this.totalCarat = totalCarat;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Integer getStockQuantity() {
        return stockQuantity;
    }

    public void setStockQuantity(Integer stockQuantity) {
        this.stockQuantity = stockQuantity;
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