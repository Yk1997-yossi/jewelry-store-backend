package com.kriger.jewelrystorebackend.dao;

import com.kriger.jewelrystorebackend.models.Category;
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
public class CategoryDAO {
    private final DataSource dataSource;

    public CategoryDAO(DataSource dataSource){
        this.dataSource = dataSource;
    }

    public List<Category> getAllCategories(){
        List<Category> categoryList = new ArrayList<>();
        String sql = "SELECT id,name,slug,created_at,updated_at FROM categories";
        try(Connection connection = this.dataSource.getConnection();
            PreparedStatement ps = connection.prepareStatement(sql);
            ResultSet rs = ps.executeQuery()){
            while(rs.next()){
                Category category = new Category();
                category.setId(rs.getLong("id"));
                category.setName(rs.getString("name"));
                category.setSlug(rs.getString("slug"));

                if(rs.getTimestamp("created_at") != null)
                    category.setCreatedAt(rs.getTimestamp("created_at").toLocalDateTime());

                if(rs.getTimestamp("updated_at") != null)
                    category.setUpdatedAt(rs.getTimestamp("updated_at").toLocalDateTime());

                categoryList.add(category);
            }
        }catch(SQLException e){
            System.err.println("Error fetching categories from database:");
            e.printStackTrace();
            return null;
        }
        return categoryList;
    }

    public Category getCategoryById(Long id){
        Category category = null;
        String sql = "SELECT id,name,slug,created_at,updated_at FROM categories WHERE id = ?";
        try(Connection connection = this.dataSource.getConnection();
            PreparedStatement ps = connection.prepareStatement(sql)){
            ps.setLong(1,id);
            ResultSet rs = ps.executeQuery();
            if(rs.next()){
                category = new Category();
                category.setId(rs.getLong("id"));
                category.setName(rs.getString("name"));
                category.setSlug(rs.getString("slug"));

                if(rs.getTimestamp("created_at") != null)
                    category.setCreatedAt(rs.getTimestamp("created_at").toLocalDateTime());

                if(rs.getTimestamp("updated_at") != null)
                    category.setUpdatedAt(rs.getTimestamp("updated_at").toLocalDateTime());
            }
        }catch(SQLException e){
            System.err.println("Error fetching category by ID:");
            e.printStackTrace();
        }
        return category;
    }

    public Category getCategoryBySlug(String slug){
        Category category = null;
        String sql = "SELECT id,name,slug,created_at,updated_at FROM categories WHERE slug = ?";
        try(Connection connection = this.dataSource.getConnection();
            PreparedStatement ps = connection.prepareStatement(sql)){
            ps.setString(1,slug);
            ResultSet rs = ps.executeQuery();
            if(rs.next()){
                category = new Category();
                category.setId(rs.getLong("id"));
                category.setName(rs.getString("name"));
                category.setSlug(rs.getString("slug"));

                if(rs.getTimestamp("created_at") != null)
                    category.setCreatedAt(rs.getTimestamp("created_at").toLocalDateTime());

                if(rs.getTimestamp("updated_at") != null)
                    category.setUpdatedAt(rs.getTimestamp("updated_at").toLocalDateTime());
            }
        }catch(SQLException e){
            System.err.println("Error fetching category by slug:");
            e.printStackTrace();
        }
        return category;
    }

    public int addCategory(Category category){
        String sql = "INSERT INTO categories(name,slug) VALUES(?,?)";
        try(Connection connection = this.dataSource.getConnection();
            PreparedStatement ps = connection.prepareStatement(sql,Statement.RETURN_GENERATED_KEYS)){
            ps.setString(1,category.getName());
            ps.setString(2,category.getSlug());

            int rowsAffected = ps.executeUpdate();

            if(rowsAffected > 0){
                try(ResultSet rs = ps.getGeneratedKeys()){
                    if(rs.next())
                        category.setId(rs.getLong(1));
                }
            }

            return rowsAffected;
        }catch(SQLException e){
            System.err.println("Error adding category:");
            e.printStackTrace();
            return 0;
        }
    }

    public int removeCategoryById(Long id){
        String sql = "DELETE FROM categories WHERE id = ?";
        try(Connection connection = this.dataSource.getConnection();
            PreparedStatement ps = connection.prepareStatement(sql)){
            ps.setLong(1,id);
            return ps.executeUpdate();
        }catch(SQLException e){
            System.err.println("Error deleting category:");
            e.printStackTrace();
            return 0;
        }
    }
    public int updateCategory(Category category){
        String sql = "UPDATE categories SET name = ?,slug = ? WHERE id = ?";
        try(Connection connection = this.dataSource.getConnection();
            PreparedStatement ps = connection.prepareStatement(sql)){
            ps.setString(1,category.getName());
            ps.setString(2,category.getSlug());
            ps.setLong(3,category.getId());
            return ps.executeUpdate();
        }catch(SQLException e){
            System.err.println("Error updating category:");
            e.printStackTrace();
            return 0;
        }
    }
}