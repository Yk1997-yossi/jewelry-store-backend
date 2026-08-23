package com.kriger.jewelrystorebackend.models;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Product {

    private Long id;
    private Category category;
    private String baseSku;
    private String name;
    private String slug;
    private String description;
    private Boolean isActive;
    private Integer dynamicDiamondsCount;
    private Integer fixedDiamondsCount;
    private BigDecimal fixedDiamondsWeight;
    private List<Media> mediaList = new ArrayList<>();
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public Product() {}

    public Product(Long id, Category category, String baseSku, String name, String slug, String description, Boolean isActive, Integer dynamicDiamondsCount, Integer fixedDiamondsCount, BigDecimal fixedDiamondsWeight, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = id;
        this.category = category;
        this.baseSku = baseSku;
        this.name = name;
        this.slug = slug;
        this.description = description;
        this.isActive = isActive;
        this.dynamicDiamondsCount = dynamicDiamondsCount;
        this.fixedDiamondsCount = fixedDiamondsCount;
        this.fixedDiamondsWeight = fixedDiamondsWeight;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Category getCategory() { return category; }
    public void setCategory(Category category) { this.category = category; }
    public String getBaseSku() { return baseSku; }
    public void setBaseSku(String baseSku) { this.baseSku = baseSku; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getSlug() { return slug; }
    public void setSlug(String slug) { this.slug = slug; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public Boolean getIsActive() { return isActive; }
    public void setIsActive(Boolean isActive) { this.isActive = isActive; }
    public Integer getDynamicDiamondsCount() { return dynamicDiamondsCount; }
    public void setDynamicDiamondsCount(Integer dynamicDiamondsCount) { this.dynamicDiamondsCount = dynamicDiamondsCount; }
    public Integer getFixedDiamondsCount() { return fixedDiamondsCount; }
    public void setFixedDiamondsCount(Integer fixedDiamondsCount) { this.fixedDiamondsCount = fixedDiamondsCount; }
    public BigDecimal getFixedDiamondsWeight() { return fixedDiamondsWeight; }
    public void setFixedDiamondsWeight(BigDecimal fixedDiamondsWeight) { this.fixedDiamondsWeight = fixedDiamondsWeight; }

    public List<Media> getMediaList() {
        return mediaList;
    }

    public void setMediaList(List<Media> mediaList) {
        this.mediaList = mediaList;
    }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
    public LocalDateTime getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; }
}