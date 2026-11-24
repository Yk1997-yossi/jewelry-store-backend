package com.kriger.jewelrystorebackend.services;

import com.kriger.jewelrystorebackend.dao.AdminDAO;
import com.kriger.jewelrystorebackend.dtos.AdminResponseDTO;
import com.kriger.jewelrystorebackend.dtos.LoginRequestDTO;
import com.kriger.jewelrystorebackend.models.Admin;
import com.kriger.jewelrystorebackend.responses.AdminResponse;
import com.kriger.jewelrystorebackend.utils.JwtUtil;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import static com.kriger.jewelrystorebackend.errors.AdminErrors.*;

@Service
public class AdminService {
    private final AdminDAO adminDAO;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    public AdminService(AdminDAO adminDAO,PasswordEncoder passwordEncoder,JwtUtil jwtUtil){
        this.adminDAO = adminDAO;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtil = jwtUtil;
    }

    public AdminResponse login(LoginRequestDTO request){
        if(request == null)
            return new AdminResponse(false,INVALID_CREDENTIALS,null,null);

        if(request.getEmail() == null || request.getEmail().trim().isEmpty())
            return new AdminResponse(false,EMPTY_EMAIL,null,null);

        if(request.getPassword() == null || request.getPassword().trim().isEmpty())
            return new AdminResponse(false,EMPTY_PASSWORD,null,null);

        if(!isValidEmailFormat(request.getEmail()))
            return new AdminResponse(false,INVALID_EMAIL_FORMAT,null,null);

        Admin admin = this.adminDAO.getAdminByEmail(request.getEmail());
        if(admin == null)
            return new AdminResponse(false,INVALID_CREDENTIALS,null,null);

        if(!this.passwordEncoder.matches(request.getPassword(),admin.getPasswordHash()))
            return new AdminResponse(false,INVALID_CREDENTIALS,null,null);

        this.adminDAO.updateLastLogin(admin.getId());

        String token = this.jwtUtil.generateToken(admin.getEmail(),admin.getRole());
        AdminResponseDTO adminDTO = convertToAdminResponseDTO(admin);

        return new AdminResponse(true,null,adminDTO,token);
    }

    private AdminResponseDTO convertToAdminResponseDTO(Admin admin){
        AdminResponseDTO adminDTO = new AdminResponseDTO();
        adminDTO.setId(admin.getId());
        adminDTO.setEmail(admin.getEmail());
        adminDTO.setFirstName(admin.getFirstName());
        adminDTO.setLastName(admin.getLastName());
        adminDTO.setRole(admin.getRole());
        return adminDTO;
    }

    private boolean isValidEmailFormat(String email){
        if(email == null || email.trim().isEmpty())
            return false;

        String emailRegex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[a-zA-Z]{2,}$";
        return email.matches(emailRegex);
    }
}