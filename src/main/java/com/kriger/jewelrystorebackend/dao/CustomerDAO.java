package com.kriger.jewelrystorebackend.dao;
import com.kriger.jewelrystorebackend.models.Customer;
import org.springframework.stereotype.Repository;
import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Repository
public class CustomerDAO {
    private final DataSource dataSource;

    public CustomerDAO(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public Customer addNewCustomer(Customer customer) {
        String sql = "INSERT INTO customers (email, first_name, last_name, phone, password_hash, created_at, updated_at) VALUES (?, ?, ?, ?, ?, NOW(), NOW())";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, customer.getEmail());
            ps.setString(2, customer.getFirstName());
            ps.setString(3, customer.getLastName());
            ps.setString(4, customer.getPhone());
            ps.setString(5, customer.getPasswordHash());
            ps.executeUpdate();

            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) {
                    customer.setId(rs.getLong(1));
                }
            }
            return customer;

        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public Customer getCustomerById(Long customerId) {
        Customer customer = null;
        String sql = "SELECT id, email, first_name, last_name, phone, password_hash FROM customers WHERE id = ?";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setLong(1, customerId);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                customer = new Customer();
                customer.setId(rs.getLong("id"));
                customer.setEmail(rs.getString("email"));
                customer.setFirstName(rs.getString("first_name"));
                customer.setLastName(rs.getString("last_name"));
                customer.setPhone(rs.getString("phone"));
                customer.setPasswordHash(rs.getString("password_hash"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
        return customer;
    }

    public Customer getCustomerByEmail(String customerEmail) {
        Customer customer = null;
        String sql = "SELECT id, email, first_name, last_name, phone, password_hash FROM customers WHERE email = ?";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setString(1, customerEmail);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                customer = new Customer();
                customer.setId(rs.getLong("id"));
                customer.setEmail(rs.getString("email"));
                customer.setFirstName(rs.getString("first_name"));
                customer.setLastName(rs.getString("last_name"));
                customer.setPhone(rs.getString("phone"));
                customer.setPasswordHash(rs.getString("password_hash"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
        return customer;
    }

    public int updateCustomer(Customer customer) {
        String sql = "UPDATE customers SET email = ?, first_name = ?, last_name = ?, phone = ?, password_hash = ?, updated_at = NOW() WHERE id = ?";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setString(1, customer.getEmail());
            ps.setString(2, customer.getFirstName());
            ps.setString(3, customer.getLastName());
            ps.setString(4, customer.getPhone());
            ps.setString(5, customer.getPasswordHash());
            ps.setLong(6, customer.getId());
            return ps.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
            return 0;
        }
    }

    public int deleteCustomerById(Long id) {
        String sql = "DELETE FROM customers WHERE id = ?";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setLong(1, id);
            return ps.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
            return 0;
        }
    }

    public List<Customer> getAllCustomers(){
        List<Customer> customerList= new ArrayList<>();
        String sql = "SELECT * FROM customers";
        try(Connection connection = dataSource.getConnection();
            PreparedStatement ps = connection.prepareStatement(sql)){
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                Customer customer = new Customer();
                customer.setId(rs.getLong("id"));
                customer.setEmail(rs.getString("email"));
                customer.setFirstName(rs.getString("first_name"));
                customer.setLastName(rs.getString("last_name"));
                customer.setPhone(rs.getString("phone"));
                customer.setPasswordHash(rs.getString("password_hash"));
                customerList.add(customer);
            }
        }catch (SQLException e){
            e.printStackTrace();
            return null;
        }
        return customerList;
    }
}