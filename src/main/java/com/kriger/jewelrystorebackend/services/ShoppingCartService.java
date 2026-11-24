package com.kriger.jewelrystorebackend.services;

import com.kriger.jewelrystorebackend.dao.CustomerDAO;
import com.kriger.jewelrystorebackend.dao.ProductVariantDAO;
import com.kriger.jewelrystorebackend.dao.ShoppingCartDAO;
import com.kriger.jewelrystorebackend.dtos.AddCartItemRequestDTO;
import com.kriger.jewelrystorebackend.models.CartItem;
import com.kriger.jewelrystorebackend.models.Customer;
import com.kriger.jewelrystorebackend.models.ProductVariant;
import com.kriger.jewelrystorebackend.models.ShoppingCart;
import com.kriger.jewelrystorebackend.responses.BasicResponse;
import com.kriger.jewelrystorebackend.responses.ShoppingCartResponse;
import org.springframework.stereotype.Service;

import static com.kriger.jewelrystorebackend.errors.CustomerErrors.CUSTOMER_NOT_FOUND;
import static com.kriger.jewelrystorebackend.errors.ProductVariantErrors.VARIANT_NOT_FOUND;
import static com.kriger.jewelrystorebackend.errors.ShoppingCartErrors.*;

@Service
public class ShoppingCartService {
    private final ShoppingCartDAO shoppingCartDAO;
    private final CustomerDAO customerDAO;
    private final ProductVariantDAO productVariantDAO;

    public ShoppingCartService(ShoppingCartDAO shoppingCartDAO,CustomerDAO customerDAO,ProductVariantDAO productVariantDAO){
        this.shoppingCartDAO = shoppingCartDAO;
        this.customerDAO = customerDAO;
        this.productVariantDAO = productVariantDAO;
    }

    public ShoppingCartResponse getShoppingCart(String email){
        Customer customer = this.customerDAO.getCustomerByEmail(email);
        if(customer == null)
            return new ShoppingCartResponse(false,CUSTOMER_NOT_FOUND,null);

        ShoppingCart shoppingCart = this.shoppingCartDAO.getShoppingCartByCustomerId(customer.getId());
        if(shoppingCart == null){
            int rowsAffected = this.shoppingCartDAO.addShoppingCart(customer.getId());
            if(rowsAffected == 0)
                return new ShoppingCartResponse(false,SHOPPING_CART_CREATE_FAILED,null);

            shoppingCart = this.shoppingCartDAO.getShoppingCartByCustomerId(customer.getId());
        }

        return new ShoppingCartResponse(true,null,shoppingCart);
    }

    public ShoppingCartResponse addCartItem(String email,AddCartItemRequestDTO request){
        Customer customer = this.customerDAO.getCustomerByEmail(email);
        if(customer == null)
            return new ShoppingCartResponse(false,CUSTOMER_NOT_FOUND,null);

        if(!isValidAddCartItemRequest(request))
            return new ShoppingCartResponse(false,INVALID_CART_ITEM,null);

        ProductVariant variant = this.productVariantDAO.getVariantById(request.getVariantId());
        if(variant == null)
            return new ShoppingCartResponse(false,VARIANT_NOT_FOUND,null);

        if(variant.getStockQuantity() == null || variant.getStockQuantity() <= 0)
            return new ShoppingCartResponse(false,VARIANT_OUT_OF_STOCK,null);

        CartItem cartItem = new CartItem();
        cartItem.setVariant(variant);
        cartItem.setQuantity(request.getQuantity());
        cartItem.setMetalType(request.getMetalType());
        cartItem.setSizeValue(request.getSizeValue());
        cartItem.setSizeUnit(request.getSizeUnit());

        ShoppingCart shoppingCart = this.shoppingCartDAO.getShoppingCartByCustomerId(customer.getId());
        if(shoppingCart == null){
            int rowsAffected = this.shoppingCartDAO.addShoppingCart(customer.getId());
            if(rowsAffected == 0)
                return new ShoppingCartResponse(false,SHOPPING_CART_CREATE_FAILED,null);

            shoppingCart = this.shoppingCartDAO.getShoppingCartByCustomerId(customer.getId());
        }

        CartItem matchingCartItem = this.shoppingCartDAO.getMatchingCartItem(shoppingCart.getId(),cartItem);
        int rowsAffected;

        if(matchingCartItem != null){
            int newQuantity = matchingCartItem.getQuantity() + cartItem.getQuantity();

            if(newQuantity > variant.getStockQuantity())
                return new ShoppingCartResponse(false,INSUFFICIENT_STOCK,null);

            rowsAffected = this.shoppingCartDAO.updateCartItemQuantity(
                    matchingCartItem.getId(),
                    shoppingCart.getId(),
                    newQuantity
            );
        }else{
            if(cartItem.getQuantity() > variant.getStockQuantity())
                return new ShoppingCartResponse(false,INSUFFICIENT_STOCK,null);

            rowsAffected = this.shoppingCartDAO.addCartItem(shoppingCart.getId(),cartItem);
        }

        if(rowsAffected == 0)
            return new ShoppingCartResponse(false,CART_ITEM_ADD_FAILED,null);

        shoppingCart = this.shoppingCartDAO.getShoppingCartByCustomerId(customer.getId());
        return new ShoppingCartResponse(true,null,shoppingCart);
    }

    public ShoppingCartResponse updateCartItemQuantity(String email,Long cartItemId,int quantity){
        Customer customer = this.customerDAO.getCustomerByEmail(email);
        if(customer == null)
            return new ShoppingCartResponse(false,CUSTOMER_NOT_FOUND,null);

        if(cartItemId == null || cartItemId <= 0)
            return new ShoppingCartResponse(false,CART_ITEM_NOT_FOUND,null);

        if(quantity <= 0)
            return new ShoppingCartResponse(false,INVALID_QUANTITY,null);

        ShoppingCart shoppingCart = this.shoppingCartDAO.getShoppingCartByCustomerId(customer.getId());
        if(shoppingCart == null)
            return new ShoppingCartResponse(false,SHOPPING_CART_NOT_FOUND,null);

        CartItem cartItem = this.shoppingCartDAO.getCartItemByIdAndCartId(
                cartItemId,
                shoppingCart.getId()
        );

        if(cartItem == null)
            return new ShoppingCartResponse(false,CART_ITEM_NOT_FOUND,null);

        ProductVariant variant = this.productVariantDAO.getVariantById(
                cartItem.getVariant().getId()
        );

        if(variant == null)
            return new ShoppingCartResponse(false,VARIANT_NOT_FOUND,null);

        if(variant.getStockQuantity() == null || variant.getStockQuantity() <= 0)
            return new ShoppingCartResponse(false,VARIANT_OUT_OF_STOCK,null);

        if(quantity > variant.getStockQuantity())
            return new ShoppingCartResponse(false,INSUFFICIENT_STOCK,null);

        int rowsAffected = this.shoppingCartDAO.updateCartItemQuantity(
                cartItemId,
                shoppingCart.getId(),
                quantity
        );

        if(rowsAffected == 0)
            return new ShoppingCartResponse(false,CART_ITEM_UPDATE_FAILED,null);

        shoppingCart = this.shoppingCartDAO.getShoppingCartByCustomerId(customer.getId());
        return new ShoppingCartResponse(true,null,shoppingCart);
    }

    public BasicResponse removeCartItem(String email,Long cartItemId){
        Customer customer = this.customerDAO.getCustomerByEmail(email);
        if(customer == null)
            return new BasicResponse(false,CUSTOMER_NOT_FOUND);

        if(cartItemId == null || cartItemId <= 0)
            return new BasicResponse(false,CART_ITEM_NOT_FOUND);

        ShoppingCart shoppingCart = this.shoppingCartDAO.getShoppingCartByCustomerId(customer.getId());
        if(shoppingCart == null)
            return new BasicResponse(false,SHOPPING_CART_NOT_FOUND);

        CartItem cartItem = this.shoppingCartDAO.getCartItemByIdAndCartId(
                cartItemId,
                shoppingCart.getId()
        );

        if(cartItem == null)
            return new BasicResponse(false,CART_ITEM_NOT_FOUND);

        int rowsAffected = this.shoppingCartDAO.removeCartItemByIdAndCartId(
                cartItemId,
                shoppingCart.getId()
        );

        if(rowsAffected == 0)
            return new BasicResponse(false,CART_ITEM_REMOVE_FAILED);

        return new BasicResponse(true,null);
    }

    public BasicResponse clearShoppingCart(String email){
        Customer customer = this.customerDAO.getCustomerByEmail(email);
        if(customer == null)
            return new BasicResponse(false,CUSTOMER_NOT_FOUND);

        ShoppingCart shoppingCart = this.shoppingCartDAO.getShoppingCartByCustomerId(customer.getId());
        if(shoppingCart == null)
            return new BasicResponse(false,SHOPPING_CART_NOT_FOUND);

        if(shoppingCart.getItems() == null || shoppingCart.getItems().isEmpty())
            return new BasicResponse(true,null);

        int rowsAffected = this.shoppingCartDAO.clearCartByCartId(shoppingCart.getId());
        if(rowsAffected == 0)
            return new BasicResponse(false,SHOPPING_CART_CLEAR_FAILED);

        return new BasicResponse(true,null);
    }

    private boolean isValidAddCartItemRequest(AddCartItemRequestDTO request){
        if(request == null)
            return false;

        if(request.getVariantId() == null || request.getVariantId() <= 0)
            return false;

        if(request.getQuantity() <= 0)
            return false;

        return true;
    }
}