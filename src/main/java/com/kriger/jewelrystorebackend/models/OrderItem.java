package com.kriger.jewelrystorebackend.models;
import java.math.BigDecimal;
import java.time.LocalDateTime;

public class OrderItem {

    private Long id;
    private Order order;
    private ProductVariant variant;
    private String metalType;
    private BigDecimal sizeValue;
    private String sizeUnit;
    private Integer quantity;
    private BigDecimal priceAtPurchase;
    private LocalDateTime createdAt;

    public OrderItem() {}

    public OrderItem(Long id, Order order, ProductVariant variant, String metalType,
                     BigDecimal sizeValue, String sizeUnit, Integer quantity,
                     BigDecimal priceAtPurchase, LocalDateTime createdAt) {
        this.id = id;
        this.order = order;
        this.variant = variant;
        this.metalType = metalType;
        this.sizeValue = sizeValue;
        this.sizeUnit = sizeUnit;
        this.quantity = quantity;
        this.priceAtPurchase = priceAtPurchase;
        this.createdAt = createdAt;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Order getOrder() { return order; }
    public void setOrder(Order order) { this.order = order; }

    public ProductVariant getVariant() { return variant; }
    public void setVariant(ProductVariant variant) { this.variant = variant; }

    public String getMetalType() { return metalType; }
    public void setMetalType(String metalType) { this.metalType = metalType; }

    public BigDecimal getSizeValue() { return sizeValue; }
    public void setSizeValue(BigDecimal sizeValue) { this.sizeValue = sizeValue; }

    public String getSizeUnit() { return sizeUnit; }
    public void setSizeUnit(String sizeUnit) { this.sizeUnit = sizeUnit; }

    public Integer getQuantity() { return quantity; }
    public void setQuantity(Integer quantity) { this.quantity = quantity; }

    public BigDecimal getPriceAtPurchase() { return priceAtPurchase; }
    public void setPriceAtPurchase(BigDecimal priceAtPurchase) { this.priceAtPurchase = priceAtPurchase; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
}