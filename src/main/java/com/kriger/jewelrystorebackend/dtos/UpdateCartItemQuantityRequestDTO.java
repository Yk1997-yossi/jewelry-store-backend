package com.kriger.jewelrystorebackend.dtos;

public class UpdateCartItemQuantityRequestDTO {
    private int quantity;

    public UpdateCartItemQuantityRequestDTO() {
    }

    public UpdateCartItemQuantityRequestDTO(int quantity) {
        this.quantity = quantity;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}