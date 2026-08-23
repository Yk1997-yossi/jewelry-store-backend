package com.kriger.jewelrystorebackend.dao;
import com.kriger.jewelrystorebackend.models.Category;
import org.springframework.stereotype.Repository;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Repository
public class CategoryDAO {

    private DataSource dataSource;

    public CategoryDAO(DataSource dataSource) {
        this.dataSource = dataSource;
    }
    public List<Category> getAllCategories(){
        List<Category> categoryList = new ArrayList<>();
        String sql = " SELECT id,name,slug,created_at, updated_at FROM categories";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql);
             ResultSet rs = ps.executeQuery())
        {
            while (rs.next()){
                Long id = rs.getLong("id");
                String name = rs.getString("name");
                String slug = rs.getString("slug");
                Category category = new Category();
                category.setId(id);
                category.setName(name);
                category.setSlug(slug);
                categoryList.add(category);
            }
        }catch (SQLException e) {
            System.err.println("Error fetching categories from database:");
            e.printStackTrace();
        }
        return categoryList;
    }
    public Category getCategoryById(Long id){
        Category categoryById = null;
        String sql = "SELECT id,name,slug,created_at,updated_at FROM categories WHERE id = ?";
        try(Connection connection = dataSource.getConnection();
            PreparedStatement ps = connection.prepareStatement(sql)){
            ps.setLong(1,id);
            ResultSet rs = ps.executeQuery();
            if(rs.next()){
                categoryById = new Category();
                String name = rs.getString("name");
                String slug = rs.getString("slug");
                categoryById.setId(id);
                categoryById.setName(name);
                categoryById.setSlug(slug);
            }
        }
        catch (SQLException e){
            System.err.println("Error fetching category by ID:");
            e.printStackTrace();
        }
        return categoryById;
    }
    public int addCategory(Category categoryToAdd){
        String sql = "INSERT INTO categories(name,slug) VALUES(?,?)";
        try(Connection connection = dataSource.getConnection();
            PreparedStatement ps = connection.prepareStatement(sql);) {
            String name = categoryToAdd.getName();
            String slug = categoryToAdd.getSlug();
            ps.setString(1,name);
            ps.setString(2,slug);
            return ps.executeUpdate();
        }
        catch (SQLException e){
            System.err.println("Error adding category:");
            e.printStackTrace();
            return 0;
        }
    }
    public int removeCategoryById(Long id){
        String sql = "DELETE FROM categories WHERE id = ?";
        try(Connection connection = dataSource.getConnection();
            PreparedStatement ps = connection.prepareStatement(sql);) {
            ps.setLong(1,id);
            return ps.executeUpdate();
        }
        catch (SQLException e){
            System.err.println("Error deleting category:");
            e.printStackTrace();
            return 0;
        }
    }
}