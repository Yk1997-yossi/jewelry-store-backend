package com.kriger.jewelrystorebackend.models;
import java.sql.Timestamp;

public class Media {
    private Long id;
    private Long productId;
    private String url;
    private String mediaType;
    private boolean isMain;
    private Timestamp createdAt;

    public Media() {
    }

    public Media(Long id, Long productId, String url, String mediaType, boolean isMain, Timestamp createdAt) {
        this.id = id;
        this.productId = productId;
        this.url = url;
        this.mediaType = mediaType;
        this.isMain = isMain;
        this.createdAt = createdAt;
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

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getMediaType() {
        return mediaType;
    }

    public void setMediaType(String mediaType) {
        this.mediaType = mediaType;
    }

    public boolean isMain() {
        return isMain;
    }

    public void setMain(boolean main) {
        isMain = main;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }
}