package com.kriger.jewelrystorebackend.dao;

import com.kriger.jewelrystorebackend.models.Category;
import com.kriger.jewelrystorebackend.models.Product;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

@Repository
public class ProductDAO {
    private final DataSource dataSource;

    public ProductDAO(DataSource dataSource){
        this.dataSource = dataSource;
    }

    public List<Product> getAllProducts(){
        List<Product> productList = new ArrayList<>();
        String sql = "SELECT " +
                "p.id,p.category_id,p.base_sku,p.name,p.slug,p.description," +
                "p.is_active,p.fixed_diamonds_count,p.dynamic_diamonds_count," +
                "p.fixed_diamonds_weight,p.created_at,p.updated_at," +
                "c.name AS category_name,c.slug AS category_slug " +
                "FROM products p JOIN categories c ON p.category_id = c.id";

        try(Connection connection = this.dataSource.getConnection();
            PreparedStatement ps = connection.prepareStatement(sql);
            ResultSet rs = ps.executeQuery()){
            while(rs.next()){
                Category category = new Category();
                category.setId(rs.getLong("category_id"));
                category.setName(rs.getString("category_name"));
                category.setSlug(rs.getString("category_slug"));

                Product product = new Product();
                product.setId(rs.getLong("id"));
                product.setBaseSku(rs.getString("base_sku"));
                product.setName(rs.getString("name"));
                product.setSlug(rs.getString("slug"));
                product.setDescription(rs.getString("description"));
                product.setIsActive(rs.getBoolean("is_active"));
                product.setFixedDiamondsCount(rs.getInt("fixed_diamonds_count"));
                product.setDynamicDiamondsCount(rs.getInt("dynamic_diamonds_count"));
                product.setFixedDiamondsWeight(rs.getBigDecimal("fixed_diamonds_weight"));
                product.setCategory(category);

                if(rs.getTimestamp("created_at") != null)
                    product.setCreatedAt(rs.getTimestamp("created_at").toLocalDateTime());

                if(rs.getTimestamp("updated_at") != null)
                    product.setUpdatedAt(rs.getTimestamp("updated_at").toLocalDateTime());

                productList.add(product);
            }
        }catch(SQLException e){
            System.err.println("Error fetching products from database:");
            e.printStackTrace();
            return null;
        }
        return productList;
    }

    public Product getProductById(Long id){
        Product product = null;
        String sql = "SELECT " +
                "p.id AS product_id,p.category_id,p.base_sku,p.name AS product_name," +
                "p.slug AS product_slug,p.description,p.is_active," +
                "p.fixed_diamonds_count,p.dynamic_diamonds_count,p.fixed_diamonds_weight," +
                "p.created_at,p.updated_at,c.name AS category_name,c.slug AS category_slug " +
                "FROM products p JOIN categories c ON p.category_id = c.id " +
                "WHERE p.id = ?";

        try(Connection connection = this.dataSource.getConnection();
            PreparedStatement ps = connection.prepareStatement(sql)){
            ps.setLong(1,id);
            ResultSet rs = ps.executeQuery();

            if(rs.next()){
                Category category = new Category();
                category.setId(rs.getLong("category_id"));
                category.setName(rs.getString("category_name"));
                category.setSlug(rs.getString("category_slug"));

                product = new Product();
                product.setId(rs.getLong("product_id"));
                product.setBaseSku(rs.getString("base_sku"));
                product.setName(rs.getString("product_name"));
                product.setSlug(rs.getString("product_slug"));
                product.setDescription(rs.getString("description"));
                product.setIsActive(rs.getBoolean("is_active"));
                product.setFixedDiamondsCount(rs.getInt("fixed_diamonds_count"));
                product.setDynamicDiamondsCount(rs.getInt("dynamic_diamonds_count"));
                product.setFixedDiamondsWeight(rs.getBigDecimal("fixed_diamonds_weight"));
                product.setCategory(category);

                if(rs.getTimestamp("created_at") != null)
                    product.setCreatedAt(rs.getTimestamp("created_at").toLocalDateTime());

                if(rs.getTimestamp("updated_at") != null)
                    product.setUpdatedAt(rs.getTimestamp("updated_at").toLocalDateTime());
            }
        }catch(SQLException e){
            System.err.println("Error fetching product by ID:");
            e.printStackTrace();
        }
        return product;
    }

    public boolean isBaseSkuExists(String baseSku){
        String sql = "SELECT id FROM products WHERE base_sku = ?";
        try(Connection connection = this.dataSource.getConnection();
            PreparedStatement ps = connection.prepareStatement(sql)){
            ps.setString(1,baseSku);
            ResultSet rs = ps.executeQuery();
            return rs.next();
        }catch(SQLException e){
            System.err.println("Error checking product base SKU:");
            e.printStackTrace();
            return false;
        }
    }

    public boolean isSlugExists(String slug){
        String sql = "SELECT id FROM products WHERE slug = ?";
        try(Connection connection = this.dataSource.getConnection();
            PreparedStatement ps = connection.prepareStatement(sql)){
            ps.setString(1,slug);
            ResultSet rs = ps.executeQuery();
            return rs.next();
        }catch(SQLException e){
            System.err.println("Error checking product slug:");
            e.printStackTrace();
            return false;
        }
    }

    public int addProduct(Product product){
        String sql = "INSERT INTO products(" +
                "category_id,base_sku,name,slug,description,is_active," +
                "dynamic_diamonds_count,fixed_diamonds_count,fixed_diamonds_weight" +
                ") VALUES(?,?,?,?,?,?,?,?,?)";

        try(Connection connection = this.dataSource.getConnection();
            PreparedStatement ps = connection.prepareStatement(sql,Statement.RETURN_GENERATED_KEYS)){
            ps.setLong(1,product.getCategory().getId());
            ps.setString(2,product.getBaseSku());
            ps.setString(3,product.getName());
            ps.setString(4,product.getSlug());
            ps.setString(5,product.getDescription());
            ps.setBoolean(6,product.getIsActive());
            ps.setInt(7,product.getDynamicDiamondsCount());
            ps.setInt(8,product.getFixedDiamondsCount());
            ps.setBigDecimal(9,product.getFixedDiamondsWeight());

            int rowsAffected = ps.executeUpdate();

            if(rowsAffected > 0){
                try(ResultSet rs = ps.getGeneratedKeys()){
                    if(rs.next())
                        product.setId(rs.getLong(1));
                }
            }

            return rowsAffected;
        }catch(SQLException e){
            System.err.println("Error adding product:");
            e.printStackTrace();
            return 0;
        }
    }

    public int removeProductById(Long id){
        String sql = "DELETE FROM products WHERE id = ?";
        try(Connection connection = this.dataSource.getConnection();
            PreparedStatement ps = connection.prepareStatement(sql)){
            ps.setLong(1,id);
            return ps.executeUpdate();
        }catch(SQLException e){
            System.err.println("Error deleting product:");
            e.printStackTrace();
            return 0;
        }
    }

    public int updateProduct(Product product){
        String sql = "UPDATE products SET category_id = ?,base_sku = ?,name = ?,slug = ?,description = ?,is_active = ?,dynamic_diamonds_count = ?,fixed_diamonds_count = ?,fixed_diamonds_weight = ? WHERE id = ?";
        try(Connection connection = this.dataSource.getConnection();
            PreparedStatement ps = connection.prepareStatement(sql)){
            ps.setLong(1,product.getCategory().getId());
            ps.setString(2,product.getBaseSku());
            ps.setString(3,product.getName());
            ps.setString(4,product.getSlug());
            ps.setString(5,product.getDescription());
            ps.setBoolean(6,product.getIsActive());
            ps.setInt(7,product.getDynamicDiamondsCount());
            ps.setInt(8,product.getFixedDiamondsCount());
            ps.setBigDecimal(9,product.getFixedDiamondsWeight());
            ps.setLong(10,product.getId());
            return ps.executeUpdate();
        }catch(SQLException e){
            System.err.println("Error updating product:");
            e.printStackTrace();
            return 0;
        }
    }

    public boolean isBaseSkuUsedByAnotherProduct(String baseSku,Long id) throws SQLException{
        String sql = "SELECT id FROM products WHERE base_sku = ? AND id <> ?";
        try(Connection connection = this.dataSource.getConnection();
            PreparedStatement ps = connection.prepareStatement(sql)){
            ps.setString(1,baseSku);
            ps.setLong(2,id);
            try(ResultSet rs = ps.executeQuery()){
                return rs.next();
            }
        }
    }

    public boolean isSlugUsedByAnotherProduct(String slug,Long id) throws SQLException{
        String sql = "SELECT id FROM products WHERE slug = ? AND id <> ?";
        try(Connection connection = this.dataSource.getConnection();
            PreparedStatement ps = connection.prepareStatement(sql)){
            ps.setString(1,slug);
            ps.setLong(2,id);
            try(ResultSet rs = ps.executeQuery()){
                return rs.next();
            }
        }
    }
}
