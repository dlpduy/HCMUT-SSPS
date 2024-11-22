package com.project.SSPS.controller;

import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.project.SSPS.dto.LoginDTO;
import com.project.SSPS.dto.RegisterDTO;
import com.project.SSPS.model.User;
import com.project.SSPS.response.LoginResponse;
import com.project.SSPS.response.UserResponse;
import com.project.SSPS.service.UserService;
import com.project.SSPS.util.SecurityUtil;
import com.project.SSPS.util.annotation.ApiMessage;
import com.project.SSPS.util.errors.InvalidException;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;

import com.project.SSPS.model.Role;

@RestController
@RequestMapping("${api.prefix}/auth")
public class AuthController {

    private final PasswordEncoder passwordEncoder;
    private final UserService userService;
    private final SecurityUtil securityUtil;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;

    public AuthController(PasswordEncoder passwordEncoder, UserService userService, SecurityUtil securityUtil,
            AuthenticationManagerBuilder authenticationManagerBuilder) {
        this.passwordEncoder = passwordEncoder;
        this.userService = userService;
        this.securityUtil = securityUtil;
        this.authenticationManagerBuilder = authenticationManagerBuilder;
    }

    @PostMapping("/register")
    public ResponseEntity<UserResponse> register(@RequestBody RegisterDTO registerDTO) {
        String hashPassword = this.passwordEncoder.encode(registerDTO.getPassword());
        User newUser = new User();
        newUser.setFullName(registerDTO.getFullName());
        newUser.setEmail(registerDTO.getEmail());
        newUser.setPassword(hashPassword);
        newUser.setRole(Role.STUDENT);
        this.userService.handleCreateUser(newUser);
        UserResponse userResponse = new UserResponse();
        userResponse.setId(newUser.getId());
        userResponse.setFullName(newUser.getFullName());
        userResponse.setEmail(newUser.getEmail());
        userResponse.setRole(newUser.getRole());
        return ResponseEntity.ok(userResponse);
    }

    @PostMapping("/login")
    @ApiMessage("Login successfully")
    public ResponseEntity<LoginResponse> login(@Valid @RequestBody LoginDTO loginDTO,
            HttpServletResponse response) {

        // Nạp input gồm username/password vào Security

        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
            loginDTO.getEmail(), loginDTO.getPassword());

        // xác thực người dùng => cần viết hàm loadUserByUsername
        Authentication authentication = authenticationManagerBuilder.getObject()
                .authenticate(authenticationToken);
        // create a token

        SecurityContextHolder.getContext().setAuthentication(authentication); // set authentication vào
                                                                              // SecurityContext
        LoginResponse resLoginDTO = new LoginResponse();
        User userDB = this.userService.getUserByEmail(loginDTO.getEmail());
        LoginResponse.UserLogin userLogin = new LoginResponse.UserLogin(userDB.getId(), userDB.getEmail(),
                userDB.getFullName(), userDB.getRole());
        resLoginDTO.setUser(userLogin);
        String access_token = this.securityUtil.createAccessToken(loginDTO.getEmail(), userLogin);
        resLoginDTO.setAccessToken(access_token);
        return ResponseEntity.ok()
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + access_token)
                .body(resLoginDTO);
    }

    @PostMapping("/logout")
    @ApiMessage("Logout successfully")
    public ResponseEntity<Void> logout() throws InvalidException {
        String email = SecurityUtil.getCurrentUserLogin().isPresent() ? SecurityUtil.getCurrentUserLogin().get()
                : "";
        if (email.isEmpty()) {
            throw new InvalidException("User is not authenticated");
        }
        return ResponseEntity.ok()
                .header(HttpHeaders.AUTHORIZATION, "")
                .body(null);
    }

}
