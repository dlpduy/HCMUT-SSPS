package com.project.SSPS.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.project.SSPS.dto.LoginDTO;
import com.project.SSPS.dto.RegisterDTO;
import com.project.SSPS.response.LoginResponse;
import com.project.SSPS.response.UserResponse;
import com.project.SSPS.service.AuthService;
import com.project.SSPS.util.annotation.ApiMessage;
import com.project.SSPS.util.errors.GlobalException;
import jakarta.validation.Valid;

@RestController
@RequestMapping("${api.prefix}/auth")
public class AuthController {
    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/register")
    @ApiMessage("Register successfully")
    public ResponseEntity<?> register(@RequestBody RegisterDTO registerDTO) {
        try {
            UserResponse resRegisterDTO = this.authService.register(registerDTO);
            return ResponseEntity.ok().body(resRegisterDTO);
        } catch (Exception e) {
            return GlobalException.handleException(e);
        }

    }

    @PostMapping("/login")
    @ApiMessage("Login successfully")
    public ResponseEntity<LoginResponse> login(@Valid @RequestBody LoginDTO loginDTO) {
        LoginResponse resLoginDTO = this.authService.login(loginDTO);
        return ResponseEntity.ok().body(resLoginDTO);
    }

    @PostMapping("/logout")
    @ApiMessage("Logout successfully")
    public ResponseEntity<Void> logout() {
        this.authService.logout();
        return ResponseEntity.ok().build();
    }

}
