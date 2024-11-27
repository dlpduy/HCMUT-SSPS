package com.project.SSPS.service;

import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import com.project.SSPS.dto.LoginDTO;
import com.project.SSPS.dto.RegisterDTO;
import com.project.SSPS.model.Role;
import com.project.SSPS.model.User;
import com.project.SSPS.response.LoginResponse;
import com.project.SSPS.response.UserResponse;
import com.project.SSPS.util.errors.InvalidException;

import jakarta.validation.Valid;

import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Service
public class AuthService {
    private final PasswordEncoder passwordEncoder;
    private final UserService userService;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final JwtService jwtService;

    public AuthService(PasswordEncoder passwordEncoder, UserService userService,
            AuthenticationManagerBuilder authenticationManagerBuilder, JwtService jwtService) {
        this.passwordEncoder = passwordEncoder;
        this.userService = userService;
        this.authenticationManagerBuilder = authenticationManagerBuilder;
        this.jwtService = jwtService;
    }

    public UserResponse register(RegisterDTO registerDTO) throws InvalidException {
        if (this.userService.checkUserExists(registerDTO.getUsername())) {
            throw new InvalidException("Username is already taken");
        }
        String hashPassword = this.passwordEncoder.encode(registerDTO.getPassword());
        User newUser = new User();
        newUser.setFullName(registerDTO.getFullName());
        newUser.setUsername(registerDTO.getUsername());
        newUser.setPassword(hashPassword);

        if (registerDTO.getUsername().equals("spsohcmut@gmail.com"))
            newUser.setRole(Role.SPSO);
        else
            newUser.setRole(Role.STUDENT);

        User user = this.userService.handleCreateUser(newUser);
        UserResponse userResponse = new UserResponse(user.getId(), user.getFullName(), user.getUsername(),
                user.getRole());
        return userResponse;

    }

    public LoginResponse login(LoginDTO loginDTO) {
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                loginDTO.getUsername(), loginDTO.getPassword());

        Authentication authentication = authenticationManagerBuilder.getObject()
                .authenticate(authenticationToken);

        SecurityContextHolder.getContext().setAuthentication(authentication); // set authentication vào
                                                                              // SecurityContext
        LoginResponse resLoginDTO = new LoginResponse();
        User userDB = this.userService.getUserbyUsername(loginDTO.getUsername());
        LoginResponse.UserLogin userLogin = new LoginResponse.UserLogin(userDB.getId(),
                userDB.getFullName(), userDB.getRole());
        resLoginDTO.setUser(userLogin);
        // Create a token
        String access_token = jwtService.generateToken(userDB);
        resLoginDTO.setAccessToken(access_token);
        return resLoginDTO;

    }

    public void logout() {
        SecurityContextHolder.clearContext();
    }
}