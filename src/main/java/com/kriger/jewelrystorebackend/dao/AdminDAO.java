package com.kriger.jewelrystorebackend.dao;

import com.kriger.jewelrystorebackend.models.Admin;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@Repository
public class AdminDAO {
    private final DataSource dataSource;

    public AdminDAO(DataSource dataSource){
        this.dataSource = dataSource;
    }

    public Admin getAdminByEmail(String email){
        Admin admin = null;
        String sql = "SELECT id,email,password_hash,first_name,last_name,role,last_login,created_at,updated_at FROM admins WHERE email = ?";
        try(Connection connection = this.dataSource.getConnection();
            PreparedStatement ps = connection.prepareStatement(sql)){
            ps.setString(1,email);
            ResultSet rs = ps.executeQuery();
            if(rs.next()){
                admin = new Admin();
                admin.setId(rs.getLong("id"));
                admin.setEmail(rs.getString("email"));
                admin.setPasswordHash(rs.getString("password_hash"));
                admin.setFirstName(rs.getString("first_name"));
                admin.setLastName(rs.getString("last_name"));
                admin.setRole(rs.getString("role"));

                if(rs.getTimestamp("last_login") != null)
                    admin.setLastLogin(rs.getTimestamp("last_login").toLocalDateTime());

                if(rs.getTimestamp("created_at") != null)
                    admin.setCreatedAt(rs.getTimestamp("created_at").toLocalDateTime());

                if(rs.getTimestamp("updated_at") != null)
                    admin.setUpdatedAt(rs.getTimestamp("updated_at").toLocalDateTime());
            }
        }catch(SQLException e){
            System.err.println("Error fetching admin by email:");
            e.printStackTrace();
        }
        return admin;
    }

    public int updateLastLogin(Long adminId){
        String sql = "UPDATE admins SET last_login = NOW() WHERE id = ?";
        try(Connection connection = this.dataSource.getConnection();
            PreparedStatement ps = connection.prepareStatement(sql)){
            ps.setLong(1,adminId);
            return ps.executeUpdate();
        }catch(SQLException e){
            System.err.println("Error updating admin last login:");
            e.printStackTrace();
            return 0;
        }
    }
}