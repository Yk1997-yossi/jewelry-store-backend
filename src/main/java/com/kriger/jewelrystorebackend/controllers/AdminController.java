package com.kriger.jewelrystorebackend.controllers;

import com.kriger.jewelrystorebackend.dtos.LoginRequestDTO;
import com.kriger.jewelrystorebackend.responses.AdminResponse;
import com.kriger.jewelrystorebackend.services.AdminService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admins")
public class AdminController {
    private final AdminService adminService;

    public AdminController(AdminService adminService){
        this.adminService = adminService;
    }

    @PostMapping("/login")
    public AdminResponse login(@RequestBody LoginRequestDTO request){
        return this.adminService.login(request);
    }
}