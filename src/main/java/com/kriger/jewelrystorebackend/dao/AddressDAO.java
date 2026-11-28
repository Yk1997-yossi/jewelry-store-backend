package com.kriger.jewelrystorebackend.dao;

import com.kriger.jewelrystorebackend.models.Address;
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
public class AddressDAO {
    private final DataSource dataSource;

    public AddressDAO(DataSource dataSource){
        this.dataSource = dataSource;
    }

    public int addAddress(Long customerId,Address address){
        String clearDefaultSql = "UPDATE addresses SET is_default = false WHERE customer_id = ?";
        String addAddressSql = "INSERT INTO addresses(customer_id,city,street,house_number,zip_code,apartment,delivery_notes,is_default) VALUES(?,?,?,?,?,?,?,?)";

        try(Connection connection = this.dataSource.getConnection()){
            connection.setAutoCommit(false);

            try{
                if(Boolean.TRUE.equals(address.getDefault())){
                    try(PreparedStatement clearPs = connection.prepareStatement(clearDefaultSql)){
                        clearPs.setLong(1,customerId);
                        clearPs.executeUpdate();
                    }
                }

                try(PreparedStatement ps = connection.prepareStatement(addAddressSql,Statement.RETURN_GENERATED_KEYS)){
                    ps.setLong(1,customerId);
                    ps.setString(2,address.getCity());
                    ps.setString(3,address.getStreet());
                    ps.setString(4,address.getHouseNumber());
                    ps.setString(5,address.getZipCode());
                    ps.setString(6,address.getApartment());
                    ps.setString(7,address.getDeliveryNotes());
                    ps.setBoolean(8,Boolean.TRUE.equals(address.getDefault()));

                    int rowsAffected = ps.executeUpdate();

                    if(rowsAffected == 0){
                        connection.rollback();
                        return 0;
                    }

                    try(ResultSet rs = ps.getGeneratedKeys()){
                        if(rs.next())
                            address.setId(rs.getLong(1));
                    }

                    connection.commit();
                    return rowsAffected;
                }
            }catch(SQLException e){
                connection.rollback();
                throw e;
            }
        }catch(SQLException e){
            System.err.println("Error adding address:");
            e.printStackTrace();
            return 0;
        }
    }

    public List<Address> getAllAddressesByCustomerId(Long customerId){
        List<Address> addressList = new ArrayList<>();
        String sql = "SELECT id,city,street,house_number,zip_code,apartment,delivery_notes,is_default FROM addresses WHERE customer_id = ?";
        try(Connection connection = this.dataSource.getConnection();
            PreparedStatement ps = connection.prepareStatement(sql)){
            ps.setLong(1,customerId);
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                Address address = new Address();
                address.setId(rs.getLong("id"));
                address.setCity(rs.getString("city"));
                address.setStreet(rs.getString("street"));
                address.setHouseNumber(rs.getString("house_number"));
                address.setZipCode(rs.getString("zip_code"));
                address.setApartment(rs.getString("apartment"));
                address.setDeliveryNotes(rs.getString("delivery_notes"));
                address.setDefault(rs.getBoolean("is_default"));
                addressList.add(address);
            }
        }catch(SQLException e){
            System.err.println("Error fetching addresses from database:");
            e.printStackTrace();
            return null;
        }
        return addressList;
    }

    public Address getAddressByIdAndCustomerId(Long id,Long customerId){
        Address address = null;
        String sql = "SELECT id,city,street,house_number,zip_code,apartment,delivery_notes,is_default FROM addresses WHERE id = ? AND customer_id = ?";
        try(Connection connection = this.dataSource.getConnection();
            PreparedStatement ps = connection.prepareStatement(sql)){
            ps.setLong(1,id);
            ps.setLong(2,customerId);
            ResultSet rs = ps.executeQuery();
            if(rs.next()){
                address = new Address();
                address.setId(rs.getLong("id"));
                address.setCity(rs.getString("city"));
                address.setStreet(rs.getString("street"));
                address.setHouseNumber(rs.getString("house_number"));
                address.setZipCode(rs.getString("zip_code"));
                address.setApartment(rs.getString("apartment"));
                address.setDeliveryNotes(rs.getString("delivery_notes"));
                address.setDefault(rs.getBoolean("is_default"));
            }
        }catch(SQLException e){
            System.err.println("Error fetching address:");
            e.printStackTrace();
        }
        return address;
    }

    public int updateAddress(Long customerId,Address address){
        String clearDefaultSql = "UPDATE addresses SET is_default = false WHERE customer_id = ?";
        String updateAddressSql = "UPDATE addresses SET city = ?,street = ?,house_number = ?,zip_code = ?,apartment = ?,delivery_notes = ?,is_default = ? WHERE id = ? AND customer_id = ?";

        try(Connection connection = this.dataSource.getConnection()){
            connection.setAutoCommit(false);

            try{
                if(Boolean.TRUE.equals(address.getDefault())){
                    try(PreparedStatement clearPs = connection.prepareStatement(clearDefaultSql)){
                        clearPs.setLong(1,customerId);
                        clearPs.executeUpdate();
                    }
                }

                try(PreparedStatement ps = connection.prepareStatement(updateAddressSql)){
                    ps.setString(1,address.getCity());
                    ps.setString(2,address.getStreet());
                    ps.setString(3,address.getHouseNumber());
                    ps.setString(4,address.getZipCode());
                    ps.setString(5,address.getApartment());
                    ps.setString(6,address.getDeliveryNotes());
                    ps.setBoolean(7,Boolean.TRUE.equals(address.getDefault()));
                    ps.setLong(8,address.getId());
                    ps.setLong(9,customerId);

                    int rowsAffected = ps.executeUpdate();

                    if(rowsAffected == 0){
                        connection.rollback();
                        return 0;
                    }

                    connection.commit();
                    return rowsAffected;
                }
            }catch(SQLException e){
                connection.rollback();
                throw e;
            }
        }catch(SQLException e){
            System.err.println("Error updating address:");
            e.printStackTrace();
            return 0;
        }
    }

    public int removeAddressByIdAndCustomerId(Long addressId,Long customerId){
        String sql = "DELETE FROM addresses WHERE id = ? AND customer_id = ?";
        try(Connection connection = this.dataSource.getConnection();
            PreparedStatement ps = connection.prepareStatement(sql)){
            ps.setLong(1,addressId);
            ps.setLong(2,customerId);
            return ps.executeUpdate();
        }catch(SQLException e){
            System.err.println("Error deleting address:");
            e.printStackTrace();
            return 0;
        }
    }

    public int clearDefaultAddressesByCustomerId(Long customerId){
        String sql = "UPDATE addresses SET is_default = false WHERE customer_id = ?";
        try(Connection connection = this.dataSource.getConnection();
            PreparedStatement ps = connection.prepareStatement(sql)){
            ps.setLong(1,customerId);
            return ps.executeUpdate();
        }catch(SQLException e){
            System.err.println("Error clearing default addresses:");
            e.printStackTrace();
            return 0;
        }
    }
}