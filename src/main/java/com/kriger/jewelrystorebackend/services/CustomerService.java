package com.kriger.jewelrystorebackend.services;
import com.kriger.jewelrystorebackend.dtos.CustomerResponseDTO;
import com.kriger.jewelrystorebackend.dao.CustomerDAO;
import com.kriger.jewelrystorebackend.dtos.LoginRequestDTO;
import com.kriger.jewelrystorebackend.dtos.RegisterRequestDTO;
import com.kriger.jewelrystorebackend.dtos.UpdateCustomerDetailsRequestDTO;
import com.kriger.jewelrystorebackend.models.Customer;
import com.kriger.jewelrystorebackend.responses.AuthResponse;
import com.kriger.jewelrystorebackend.responses.BasicResponse;
import com.kriger.jewelrystorebackend.responses.CustomerListResponse;
import com.kriger.jewelrystorebackend.responses.CustomerResponse;
import com.kriger.jewelrystorebackend.utils.JwtUtil;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.List;

import static com.kriger.jewelrystorebackend.errors.CustomerErrors.*;

@Service
public class CustomerService {
    private CustomerDAO customerDAO;
    private PasswordEncoder passwordEncoder;
    private JwtUtil jwtUtil;

    public CustomerService(CustomerDAO customerDAO, PasswordEncoder passwordEncoder, JwtUtil jwtUtil){
        this.customerDAO = customerDAO;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtil = jwtUtil;
    }

    public AuthResponse addNewCustomer(RegisterRequestDTO request) {
        if (!isValidEmailFormat(request.getEmail()))
            return new AuthResponse(false, INVALID_EMAIL_FORMAT, null, null);
        if (isEmailInUse(request.getEmail()))
            return new AuthResponse(false, EMAIL_ALREADY_IN_USE, null, null);
        if (!isValidName(request.getFirstName()))
            return new AuthResponse(false, INVALID_FIRST_NAME, null, null);
        if (!isValidName(request.getLastName()))
            return new AuthResponse(false, INVALID_LAST_NAME, null, null);
        if (!isValidPassword(request.getPassword()))
            return new AuthResponse(false, WEAK_PASSWORD, null, null);
        if (!isValidPhone(request.getPhone()))
            return new AuthResponse(false, INVALID_PHONE, null, null);

        String hashedPassword = passwordEncoder.encode(request.getPassword());
        Customer customer = new Customer();
        customer.setFirstName(request.getFirstName());
        customer.setLastName(request.getLastName());
        customer.setEmail(request.getEmail());
        customer.setPhone(request.getPhone());
        customer.setPasswordHash(hashedPassword);

        Customer savedCustomer = this.customerDAO.addNewCustomer(customer);
        if (savedCustomer == null)
            return new AuthResponse(false, DATABASE_SAVE_ERROR, null, null);

        String token = this.jwtUtil.generateToken(savedCustomer.getEmail());
        CustomerResponseDTO customerDTO = convertToCustomerResponseDTO(savedCustomer);
        return new AuthResponse(true, null,customerDTO, token);
    }

    public AuthResponse login(LoginRequestDTO request){
        if (request.getEmail() == null || request.getEmail().trim().isEmpty())
            return new AuthResponse(false, EMPTY_EMAIL, null, null);
        if (request.getPassword() == null || request.getPassword().trim().isEmpty())
            return new AuthResponse(false, EMPTY_PASSWORD, null, null);
        if (!isValidEmailFormat(request.getEmail()))
            return new AuthResponse(false, INVALID_EMAIL_FORMAT, null, null);

        Customer customer = customerDAO.getCustomerByEmail(request.getEmail());
        if (customer == null)
            return new AuthResponse(false, INVALID_CREDENTIALS, null, null);
        if (!passwordEncoder.matches(request.getPassword(), customer.getPasswordHash()))
            return new AuthResponse(false, INVALID_CREDENTIALS, null, null);

        String token = this.jwtUtil.generateToken(customer.getEmail());
        CustomerResponseDTO customerDTO = convertToCustomerResponseDTO(customer);
        return new AuthResponse(true, null, customerDTO, token);
    }

    public CustomerResponse getCustomerById(Long id){
        Customer customer = this.customerDAO.getCustomerById(id);
        if(customer == null)
            return new CustomerResponse(false, CUSTOMER_NOT_FOUND, null);
        return new CustomerResponse(true, null, customer);
    }

    public CustomerResponse getCustomerByEmail(String email) {
        Customer customer = customerDAO.getCustomerByEmail(email);
        if (customer == null)
            return new CustomerResponse(false, CUSTOMER_NOT_FOUND, null);
        return new CustomerResponse(true, null, customer);
    }

    public BasicResponse deleteCustomerById(Long id) {
        Customer customerToDelete = customerDAO.getCustomerById(id);
        if (customerToDelete == null)
            return new BasicResponse(false, CUSTOMER_TO_DELETE_NOT_FOUND);

        int rowsAffected = customerDAO.deleteCustomerById(id);
        if (rowsAffected == 0)
            return new BasicResponse(false, DATABASE_SAVE_ERROR);

        return new BasicResponse(true, null);
    }

    public CustomerResponse updateCustomerDetails(Long id, UpdateCustomerDetailsRequestDTO update){
        if (!isValidName(update.getFirstName()))
            return new CustomerResponse(false, INVALID_FIRST_NAME, null);
        if (!isValidName(update.getLastName()))
            return new CustomerResponse(false, INVALID_LAST_NAME, null);
        if (!isValidPhone(update.getPhone()))
            return new CustomerResponse(false, INVALID_PHONE, null);

        Customer customer = this.customerDAO.getCustomerById(id);
        if(customer == null)
            return new CustomerResponse(false, CUSTOMER_NOT_FOUND, null);

        customer.setFirstName(update.getFirstName());
        customer.setLastName(update.getLastName());
        customer.setPhone(update.getPhone());

        int rowsAffected = customerDAO.updateCustomer(customer);
        if (rowsAffected == 0)
            return new CustomerResponse(false, DATABASE_SAVE_ERROR, null);

        return new CustomerResponse(true, null, customer);
    }

    public CustomerListResponse getAllCustomers(){
        List<Customer> customerList = this.customerDAO.getAllCustomers();
        if(customerList == null)
            return new CustomerListResponse(false, DATABASE_FETCH_ERROR, null);
        return new CustomerListResponse(true, null, customerList);
    }
    private CustomerResponseDTO convertToCustomerResponseDTO(Customer customer) {
        CustomerResponseDTO dto = new CustomerResponseDTO();
        dto.setId(customer.getId());
        dto.setEmail(customer.getEmail());
        dto.setFirstName(customer.getFirstName());
        dto.setLastName(customer.getLastName());
        dto.setPhone(customer.getPhone());
        return dto;
    }

    private boolean isEmailInUse(String email){
        Customer customer = this.customerDAO.getCustomerByEmail(email);
        if(customer != null)
            return true;
        return false;
    }

    private boolean isValidEmailFormat(String email) {
        if (email == null || email.trim().isEmpty()) {
            return false;
        }
        String emailRegex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[a-zA-Z]{2,}$";
        return email.matches(emailRegex);
    }

    private boolean isValidName(String name) {
        if (name == null || name.trim().isEmpty()) {
            return false;
        }
        String nameRegex = "^[a-zA-Zא-ת\\s\\-]{2,50}$";
        return name.matches(nameRegex);
    }

    private boolean isValidPhone(String phone) {
        if (phone == null || phone.trim().isEmpty()) {
            return false;
        }
        String cleanPhone = phone.replaceAll("[\\s\\-]", "");
        String phoneRegex = "^05\\d{8}$";
        return cleanPhone.matches(phoneRegex);
    }

    private boolean isValidPassword(String password) {
        if (password == null) {
            return false;
        }
        String passwordRegex = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d).{6,}$";
        return password.matches(passwordRegex);
    }
}