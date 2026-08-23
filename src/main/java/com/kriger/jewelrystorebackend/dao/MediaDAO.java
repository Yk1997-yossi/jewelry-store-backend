package com.kriger.jewelrystorebackend.dao;
import com.kriger.jewelrystorebackend.models.Media;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Repository
public class MediaDAO {
    private DataSource dataSource;

    public MediaDAO(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public int addMedia(Media media){
        String sql = "INSERT INTO product_media(product_id,url,media_type,is_main,created_at) VALUES(?,?,?,?,NOW())";
        try(Connection connection = dataSource.getConnection();
            PreparedStatement ps = connection.prepareStatement(sql)){
            ps.setLong(1,media.getProductId());
            ps.setString(2,media.getUrl());
            ps.setString(3,media.getMediaType());
            ps.setBoolean(4,media.isMain());
            return ps.executeUpdate();
        }catch (SQLException e){
            e.printStackTrace();
            return 0;
        }
    }

    public List<Media> getMediaListByProductId(Long productId){
        List<Media> mediaList = new ArrayList<>();
        String sql = " SELECT id,product_id,url,media_type,is_main,created_at FROM product_media WHERE product_id = ?";
        try(Connection connection = dataSource.getConnection();
            PreparedStatement ps = connection.prepareStatement(sql)){
            ps.setLong(1,productId);
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                Media media = new Media();
                media.setId(rs.getLong("id"));
                media.setProductId(rs.getLong("product_id"));
                media.setUrl(rs.getString("url"));
                media.setMediaType(rs.getString("media_type"));
                media.setMain(rs.getBoolean("is_main"));
                media.setCreatedAt(rs.getTimestamp("created_at"));
                mediaList.add(media);
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return mediaList;
    }

    public Media getMediaById(Long mediaId){
        String sql = "SELECT id,product_id,url,media_type,is_main,created_at FROM product_media WHERE id = ?";
        try(Connection connection = dataSource.getConnection();
            PreparedStatement ps = connection.prepareStatement(sql)){
            ps.setLong(1,mediaId);
            ResultSet rs = ps.executeQuery();
            if(rs.next()){
                Media media = new Media();
                media.setId(rs.getLong("id"));
                media.setProductId(rs.getLong("product_id"));
                media.setUrl(rs.getString("url"));
                media.setMediaType(rs.getString("media_type"));
                media.setMain(rs.getBoolean("is_main"));
                media.setCreatedAt(rs.getTimestamp("created_at"));
                return media;
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return null;
    }

    public int removeMediaById(Long id) {
        String sql = "DELETE FROM product_media WHERE id = ?";
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