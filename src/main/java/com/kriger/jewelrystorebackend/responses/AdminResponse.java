package com.kriger.jewelrystorebackend.responses;

import com.kriger.jewelrystorebackend.dtos.AdminResponseDTO;

public class AdminResponse extends BasicResponse {
    private AdminResponseDTO admin;
    private String token;

    public AdminResponse(boolean success,String errorMessage,AdminResponseDTO admin,String token){
        super(success,errorMessage);
        this.admin = admin;
        this.token = token;
    }

    public AdminResponseDTO getAdmin(){
        return admin;
    }

    public void setAdmin(AdminResponseDTO admin){
        this.admin = admin;
    }

    public String getToken(){
        return token;
    }

    public void setToken(String token){
        this.token = token;
    }
}