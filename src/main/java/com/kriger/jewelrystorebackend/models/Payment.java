package com.kriger.jewelrystorebackend.models;
import java.math.BigDecimal;
import java.time.LocalDateTime;

public class Payment {

    private Long id;
    private Order order;
    private String transactionId;
    private BigDecimal amount;
    private String status;
    private String errorCode;
    private String errorMessage;
    private LocalDateTime createdAt;

    public Payment() {}

    public Payment(Long id, Order order, String transactionId, BigDecimal amount, String status,
                   String errorCode, String errorMessage, LocalDateTime createdAt) {
        this.id = id;
        this.order = order;
        this.transactionId = transactionId;
        this.amount = amount;
        this.status = status;
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
        this.createdAt = createdAt;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Order getOrder() { return order; }
    public void setOrder(Order order) { this.order = order; }

    public String getTransactionId() { return transactionId; }
    public void setTransactionId(String transactionId) { this.transactionId = transactionId; }

    public BigDecimal getAmount() { return amount; }
    public void setAmount(BigDecimal amount) { this.amount = amount; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public String getErrorCode() { return errorCode; }
    public void setErrorCode(String errorCode) { this.errorCode = errorCode; }

    public String getErrorMessage() { return errorMessage; }
    public void setErrorMessage(String errorMessage) { this.errorMessage = errorMessage; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
}