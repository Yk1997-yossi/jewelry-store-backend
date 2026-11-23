package com.kriger.jewelrystorebackend.responses;

import com.kriger.jewelrystorebackend.models.ShoppingCart;

public class ShoppingCartResponse extends BasicResponse {
    private ShoppingCart shoppingCart;

    public ShoppingCartResponse(boolean success,String errorMessage,ShoppingCart shoppingCart){
        super(success,errorMessage);
        this.shoppingCart = shoppingCart;
    }

    public ShoppingCart getShoppingCart(){
        return shoppingCart;
    }

    public void setShoppingCart(ShoppingCart shoppingCart){
        this.shoppingCart = shoppingCart;
    }
}