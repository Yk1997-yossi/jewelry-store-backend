package com.kriger.jewelrystorebackend.controllers;

import com.kriger.jewelrystorebackend.dtos.AddCartItemRequestDTO;
import com.kriger.jewelrystorebackend.dtos.UpdateCartItemQuantityRequestDTO;
import com.kriger.jewelrystorebackend.responses.BasicResponse;
import com.kriger.jewelrystorebackend.responses.ShoppingCartResponse;
import com.kriger.jewelrystorebackend.services.ShoppingCartService;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/shoppingCart")
public class ShoppingCartController {
    private final ShoppingCartService shoppingCartService;

    public ShoppingCartController(ShoppingCartService shoppingCartService){
        this.shoppingCartService = shoppingCartService;
    }

    @GetMapping
    public ShoppingCartResponse getShoppingCart(Authentication authentication){
        String email = authentication.getName();
        return this.shoppingCartService.getShoppingCart(email);
    }

    @PostMapping("/items")
    public ShoppingCartResponse addCartItem(Authentication authentication,@RequestBody AddCartItemRequestDTO request){
        String email = authentication.getName();
        return this.shoppingCartService.addCartItem(email,request);
    }

    @PutMapping("/items/{cartItemId}")
    public ShoppingCartResponse updateCartItemQuantity(Authentication authentication,@PathVariable Long cartItemId,@RequestBody UpdateCartItemQuantityRequestDTO request){
        String email = authentication.getName();
        return this.shoppingCartService.updateCartItemQuantity(email,cartItemId,request.getQuantity());
    }

    @DeleteMapping("/items/{cartItemId}")
    public BasicResponse removeCartItem(Authentication authentication,@PathVariable Long cartItemId){
        String email = authentication.getName();
        return this.shoppingCartService.removeCartItem(email,cartItemId);
    }

    @DeleteMapping
    public BasicResponse clearShoppingCart(Authentication authentication){
        String email = authentication.getName();
        return this.shoppingCartService.clearShoppingCart(email);
    }
}