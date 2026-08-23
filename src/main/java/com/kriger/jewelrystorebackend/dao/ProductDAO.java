package com.kriger.jewelrystorebackend.dao;
import com.kriger.jewelrystorebackend.models.Category;
import com.kriger.jewelrystorebackend.models.Product;
import org.springframework.stereotype.Repository;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Repository
public class ProductDAO {
    private final DataSource dataSource;

    public ProductDAO(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public List<Product> getAllProducts() {
        List<Product> productList = new ArrayList<>();
        String sql = "SELECT " +
                "p.id, p.category_id, p.base_sku, p.name, p.slug, p.description, " +
                "p.is_active, p.fixed_diamonds_count, p.dynamic_diamonds_count, " +
                "p.fixed_diamonds_weight, p.created_at, p.updated_at, " +
                "c.name AS category_name, c.slug AS category_slug " +
                "from products p JOIN categories c " +
                "ON p.category_id = c.id";

        try (Connection connection = dataSource.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
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

                productList.add(product);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return productList;
    }

    public Product getProductById(Long id) {
        Product product = null;
        String sql = "SELECT " +
                "p.id AS product_id, p.category_id, p.base_sku, p.name AS product_name, p.slug AS product_slug, p.description, " +
                "p.is_active, p.fixed_diamonds_count, p.dynamic_diamonds_count, p.fixed_diamonds_weight, p.created_at, p.updated_at, " +
                "c.name AS category_name, c.slug AS category_slug " +
                "from products p JOIN categories c " +
                "ON p.category_id = c.id " +
                "WHERE p.id = ?";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setLong(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
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
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return product;
    }

    public int addProduct(Product product) {
        String sql = "INSERT INTO " +
                "products (category_id, base_sku, name, slug, description, is_active, " +
                "dynamic_diamonds_count, fixed_diamonds_count, fixed_diamonds_weight, " +
                "created_at, updated_at) " +
                "VALUES (?,?,?,?,?,?,?,?,?,NOW(),NOW())";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {

            ps.setLong(1, product.getCategory().getId());
            ps.setString(2, product.getBaseSku());
            ps.setString(3, product.getName());
            ps.setString(4, product.getSlug());
            ps.setString(5, product.getDescription());
            ps.setBoolean(6, product.getIsActive());
            ps.setInt(7, product.getDynamicDiamondsCount());
            ps.setInt(8, product.getFixedDiamondsCount());
            ps.setBigDecimal(9, product.getFixedDiamondsWeight());

            int rowsAffected = ps.executeUpdate();
            try (ResultSet generatedKeys = ps.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    product.setId(generatedKeys.getLong(1));
                }
            }
            return rowsAffected;
        } catch (SQLException e) {
            e.printStackTrace();
            return 0;
        }
    }

    public int removeProductById(Long id) {
        String sql = "DELETE FROM products WHERE id = ?";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setLong(1, id);
            return ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            return 0;
        }
    }
}