package com.kriger.jewelrystorebackend.dao;

import com.kriger.jewelrystorebackend.models.CartItem;
import com.kriger.jewelrystorebackend.models.Customer;
import com.kriger.jewelrystorebackend.models.ProductVariant;
import com.kriger.jewelrystorebackend.models.ShoppingCart;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Repository
public class ShoppingCartDAO {
    private final DataSource dataSource;

    public ShoppingCartDAO(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public int addShoppingCart(Long customerId) {
        String sql = "INSERT INTO shopping_cart(customer_id) VALUES(?)";
        try(Connection connection = this.dataSource.getConnection();
            PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setLong(1,customerId);
            return ps.executeUpdate();
        }catch(SQLException e) {
            System.err.println("Error adding shopping cart:");
            e.printStackTrace();
            return 0;
        }
    }

    public ShoppingCart getShoppingCartByCustomerId(Long customerId) {
        ShoppingCart shoppingCart = null;
        String sql = "SELECT id,customer_id FROM shopping_cart WHERE customer_id = ?";
        try(Connection connection = this.dataSource.getConnection();
            PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setLong(1,customerId);
            ResultSet rs = ps.executeQuery();
            if(rs.next()) {
                shoppingCart = new ShoppingCart();
                shoppingCart.setId(rs.getLong("id"));

                Customer customer = new Customer();
                customer.setId(rs.getLong("customer_id"));
                shoppingCart.setCustomer(customer);

                shoppingCart.setItems(getAllCartItemsByCartId(shoppingCart.getId()));
            }
        }catch(SQLException e) {
            System.err.println("Error fetching shopping cart:");
            e.printStackTrace();
        }
        return shoppingCart;
    }

    public int addCartItem(Long cartId,CartItem cartItem) {
        String sql = "INSERT INTO cart_items(cart_id,variant_id,metal_type,size_value,size_unit,quantity) VALUES(?,?,?,?,?,?)";
        try(Connection connection = this.dataSource.getConnection();
            PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setLong(1,cartId);
            ps.setLong(2,cartItem.getVariant().getId());
            ps.setString(3,cartItem.getMetalType());
            ps.setBigDecimal(4,cartItem.getSizeValue());
            ps.setString(5,cartItem.getSizeUnit());
            ps.setInt(6,cartItem.getQuantity());
            return ps.executeUpdate();
        }catch(SQLException e) {
            System.err.println("Error adding cart item:");
            e.printStackTrace();
            return 0;
        }
    }

    public List<CartItem> getAllCartItemsByCartId(Long cartId) {
        List<CartItem> cartItemList = new ArrayList<>();
        String sql = "SELECT id,variant_id,metal_type,size_value,size_unit,quantity FROM cart_items WHERE cart_id = ?";
        try(Connection connection = this.dataSource.getConnection();
            PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setLong(1,cartId);
            ResultSet rs = ps.executeQuery();
            while(rs.next()) {
                CartItem cartItem = new CartItem();
                cartItem.setId(rs.getLong("id"));

                ProductVariant variant = new ProductVariant();
                variant.setId(rs.getLong("variant_id"));
                cartItem.setVariant(variant);

                cartItem.setMetalType(rs.getString("metal_type"));
                cartItem.setSizeValue(rs.getBigDecimal("size_value"));
                cartItem.setSizeUnit(rs.getString("size_unit"));
                cartItem.setQuantity(rs.getInt("quantity"));
                cartItemList.add(cartItem);
            }
        }catch(SQLException e) {
            System.err.println("Error fetching cart items:");
            e.printStackTrace();
        }
        return cartItemList;
    }

    public CartItem getCartItemByIdAndCartId(Long cartItemId,Long cartId) {
        CartItem cartItem = null;
        String sql = "SELECT id,variant_id,metal_type,size_value,size_unit,quantity FROM cart_items WHERE id = ? AND cart_id = ?";
        try(Connection connection = this.dataSource.getConnection();
            PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setLong(1,cartItemId);
            ps.setLong(2,cartId);
            ResultSet rs = ps.executeQuery();
            if(rs.next()) {
                cartItem = new CartItem();
                cartItem.setId(rs.getLong("id"));

                ProductVariant variant = new ProductVariant();
                variant.setId(rs.getLong("variant_id"));
                cartItem.setVariant(variant);

                cartItem.setMetalType(rs.getString("metal_type"));
                cartItem.setSizeValue(rs.getBigDecimal("size_value"));
                cartItem.setSizeUnit(rs.getString("size_unit"));
                cartItem.setQuantity(rs.getInt("quantity"));
            }
        }catch(SQLException e) {
            System.err.println("Error fetching cart item:");
            e.printStackTrace();
        }
        return cartItem;
    }

    public CartItem getMatchingCartItem(Long cartId,CartItem cartItem) {
        CartItem matchingCartItem = null;
        String sql = "SELECT id,variant_id,metal_type,size_value,size_unit,quantity FROM cart_items WHERE cart_id = ? AND variant_id = ? AND metal_type <=> ? AND size_value <=> ? AND size_unit <=> ?";
        try(Connection connection = this.dataSource.getConnection();
            PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setLong(1,cartId);
            ps.setLong(2,cartItem.getVariant().getId());
            ps.setString(3,cartItem.getMetalType());
            ps.setBigDecimal(4,cartItem.getSizeValue());
            ps.setString(5,cartItem.getSizeUnit());
            ResultSet rs = ps.executeQuery();
            if(rs.next()) {
                matchingCartItem = new CartItem();
                matchingCartItem.setId(rs.getLong("id"));

                ProductVariant variant = new ProductVariant();
                variant.setId(rs.getLong("variant_id"));
                matchingCartItem.setVariant(variant);

                matchingCartItem.setMetalType(rs.getString("metal_type"));
                matchingCartItem.setSizeValue(rs.getBigDecimal("size_value"));
                matchingCartItem.setSizeUnit(rs.getString("size_unit"));
                matchingCartItem.setQuantity(rs.getInt("quantity"));
            }
        }catch(SQLException e) {
            System.err.println("Error fetching matching cart item:");
            e.printStackTrace();
        }
        return matchingCartItem;
    }

    public int updateCartItemQuantity(Long cartItemId,Long cartId,int quantity) {
        String sql = "UPDATE cart_items SET quantity = ? WHERE id = ? AND cart_id = ?";
        try(Connection connection = this.dataSource.getConnection();
            PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1,quantity);
            ps.setLong(2,cartItemId);
            ps.setLong(3,cartId);
            return ps.executeUpdate();
        }catch(SQLException e) {
            System.err.println("Error updating cart item quantity:");
            e.printStackTrace();
            return 0;
        }
    }

    public int removeCartItemByIdAndCartId(Long cartItemId,Long cartId) {
        String sql = "DELETE FROM cart_items WHERE id = ? AND cart_id = ?";
        try(Connection connection = this.dataSource.getConnection();
            PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setLong(1,cartItemId);
            ps.setLong(2,cartId);
            return ps.executeUpdate();
        }catch(SQLException e) {
            System.err.println("Error removing cart item:");
            e.printStackTrace();
            return 0;
        }
    }

    public int clearCartByCartId(Long cartId) {
        String sql = "DELETE FROM cart_items WHERE cart_id = ?";
        try(Connection connection = this.dataSource.getConnection();
            PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setLong(1,cartId);
            return ps.executeUpdate();
        }catch(SQLException e) {
            System.err.println("Error clearing shopping cart:");
            e.printStackTrace();
            return 0;
        }
    }
}