package com.kriger.jewelrystorebackend.dao;

import com.kriger.jewelrystorebackend.models.ProductVariant;
import org.springframework.stereotype.Repository;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Repository
public class ProductVariantDAO {
    private final DataSource dataSource;

    public ProductVariantDAO(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public int addVariant(ProductVariant variant){
        String sql = "INSERT INTO product_variants (" +
                "product_id,weight_per_stone,total_carat,price,stock_quantity,created_at,updated_at) " +
                "VALUES(?,?,?,?,?,NOW(),NOW())";
        try(Connection connection = this.dataSource.getConnection();
            PreparedStatement ps = connection.prepareStatement(sql)){
            ps.setLong(1,variant.getProductId());
            ps.setBigDecimal(2,variant.getWeightPerStone());
            ps.setBigDecimal(3,variant.getTotalCarat());
            ps.setBigDecimal(4,variant.getPrice());
            ps.setInt(5,variant.getStockQuantity());
            return ps.executeUpdate();
        }catch (SQLException e){
            e.printStackTrace();
            return 0;
        }
    }

    public List<ProductVariant> getVariantsByProductId(Long productId){
        List<ProductVariant> variantList = new ArrayList<>();
        String sql = "SELECT id, product_id, weight_per_stone, total_carat, price, stock_quantity, created_at, updated_at " +
                "FROM product_variants " +
                "WHERE product_id = ?";
        try(Connection connection = this.dataSource.getConnection();
            PreparedStatement ps = connection.prepareStatement(sql)){
            ps.setLong(1, productId);
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                ProductVariant variant = new ProductVariant();
                variant.setId(rs.getLong("id"));
                variant.setProductId(rs.getLong("product_id"));
                variant.setWeightPerStone(rs.getBigDecimal("weight_per_stone"));
                variant.setTotalCarat(rs.getBigDecimal("total_carat"));
                variant.setPrice(rs.getBigDecimal("price"));
                variant.setStockQuantity(rs.getInt("stock_quantity"));
                if (rs.getTimestamp("created_at") != null) {
                    variant.setCreatedAt(rs.getTimestamp("created_at").toLocalDateTime());
                }
                if (rs.getTimestamp("updated_at") != null) {
                    variant.setUpdatedAt(rs.getTimestamp("updated_at").toLocalDateTime());
                }
                variantList.add(variant);
            }
        } catch (SQLException e){
            e.printStackTrace();
        }
        return variantList;
    }

    public ProductVariant getVariantById(Long id){
        ProductVariant variant = null;
        String sql = "SELECT id, product_id, weight_per_stone, total_carat, price, stock_quantity, created_at, updated_at " +
                "FROM product_variants " +
                "WHERE id = ?";
        try(Connection connection = this.dataSource.getConnection();
            PreparedStatement ps = connection.prepareStatement(sql)){
            ps.setLong(1, id);
            ResultSet rs = ps.executeQuery();
            if(rs.next()){
                variant = new ProductVariant();
                variant.setId(rs.getLong("id"));
                variant.setProductId(rs.getLong("product_id"));
                variant.setWeightPerStone(rs.getBigDecimal("weight_per_stone"));
                variant.setTotalCarat(rs.getBigDecimal("total_carat"));
                variant.setPrice(rs.getBigDecimal("price"));
                variant.setStockQuantity(rs.getInt("stock_quantity"));
                if (rs.getTimestamp("created_at") != null) {
                    variant.setCreatedAt(rs.getTimestamp("created_at").toLocalDateTime());
                }
                if (rs.getTimestamp("updated_at") != null) {
                    variant.setUpdatedAt(rs.getTimestamp("updated_at").toLocalDateTime());
                }
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return variant;
    }

    public int updateVariant(ProductVariant variant){
        String sql = "UPDATE product_variants SET " +
                "weight_per_stone = ?, total_carat = ?, price = ?, stock_quantity = ?, updated_at = NOW() " +
                "WHERE id = ?";
        try(Connection connection = this.dataSource.getConnection();
            PreparedStatement ps = connection.prepareStatement(sql)){
            ps.setBigDecimal(1, variant.getWeightPerStone());
            ps.setBigDecimal(2, variant.getTotalCarat());
            ps.setBigDecimal(3, variant.getPrice());
            ps.setInt(4, variant.getStockQuantity());
            ps.setLong(5, variant.getId());
            return ps.executeUpdate();
        }catch (SQLException e){
            e.printStackTrace();
            return 0;
        }
    }

    public int removeVariantById(Long id){
        String sql = "DELETE FROM product_variants WHERE id = ?";
        try(Connection connection = this.dataSource.getConnection();
            PreparedStatement ps = connection.prepareStatement(sql)){
            ps.setLong(1, id);
            return ps.executeUpdate();
        }catch (SQLException e){
            e.printStackTrace();
            return 0;
        }
    }
}