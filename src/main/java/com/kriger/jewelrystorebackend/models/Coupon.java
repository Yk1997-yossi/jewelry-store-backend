package com.kriger.jewelrystorebackend.models;
import java.math.BigDecimal;
import java.time.LocalDateTime;

public class Coupon {

    private Long id;
    private String code;
    private String discountType;
    private BigDecimal value;
    private BigDecimal minAmount;
    private LocalDateTime expiryDate;
    private Integer usageLimit;
    private Integer usedCount;
    private Boolean isActive;

    public Coupon() {}

    public Coupon(Long id, String code, String discountType, BigDecimal value, BigDecimal minAmount,
                  LocalDateTime expiryDate, Integer usageLimit, Integer usedCount, Boolean isActive) {
        this.id = id;
        this.code = code;
        this.discountType = discountType;
        this.value = value;
        this.minAmount = minAmount;
        this.expiryDate = expiryDate;
        this.usageLimit = usageLimit;
        this.usedCount = usedCount;
        this.isActive = isActive;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getCode() { return code; }
    public void setCode(String code) { this.code = code; }
    public String getDiscountType() { return discountType; }
    public void setDiscountType(String discountType) { this.discountType = discountType; }
    public BigDecimal getValue() { return value; }
    public void setValue(BigDecimal value) { this.value = value; }
    public BigDecimal getMinAmount() { return minAmount; }
    public void setMinAmount(BigDecimal minAmount) { this.minAmount = minAmount; }
    public LocalDateTime getExpiryDate() { return expiryDate; }
    public void setExpiryDate(LocalDateTime expiryDate) { this.expiryDate = expiryDate; }
    public Integer getUsageLimit() { return usageLimit; }
    public void setUsageLimit(Integer usageLimit) { this.usageLimit = usageLimit; }
    public Integer getUsedCount() { return usedCount; }
    public void setUsedCount(Integer usedCount) { this.usedCount = usedCount; }
    public Boolean getIsActive() { return isActive; }
    public void setIsActive(Boolean isActive) { this.isActive = isActive; }
}