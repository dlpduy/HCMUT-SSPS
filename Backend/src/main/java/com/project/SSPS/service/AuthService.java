package com.project.SSPS.service;

import java.util.Random;

import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import com.project.SSPS.dto.EmailDTO;
import com.project.SSPS.dto.ForgotPasswordDTO;
import com.project.SSPS.dto.LoginDTO;
import com.project.SSPS.dto.RegisterDTO;
import com.project.SSPS.dto.UpdatePasswordDTO;
import com.project.SSPS.model.Role;
import com.project.SSPS.model.User;
import com.project.SSPS.response.LoginResponse;
import com.project.SSPS.response.RestResponse;
import com.project.SSPS.response.UserResponse;
import com.project.SSPS.util.errors.InvalidException;

import jakarta.servlet.http.HttpServletResponse;
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
    private final EmailService emailService;

    public AuthService(PasswordEncoder passwordEncoder, UserService userService,
            AuthenticationManagerBuilder authenticationManagerBuilder, JwtService jwtService,
            EmailService emailService) {
        this.passwordEncoder = passwordEncoder;
        this.userService = userService;
        this.authenticationManagerBuilder = authenticationManagerBuilder;
        this.jwtService = jwtService;
        this.emailService = emailService;
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

    public RestResponse<Object> forgotPassword(ForgotPasswordDTO forgotPasswordDTO) {
        User user = this.userService.getUserbyUsername(forgotPasswordDTO.getUsername());
        if (user == null) {
            throw new RuntimeException("User not found");
        }
        Random random = new Random();
        String code_verify = String.format("%06d", random.nextInt(1000000));
        user.setCodeVerify(code_verify);
        this.userService.handleUpdateUser(user);
        EmailDTO newEmail = new EmailDTO(user.getUsername(),
                "[HCMUT - Student Smart Printing Service] Reset your password",
                "Dear " + user.getFullName() + ",\n\n"
                        + "We noticed that you forgot your login password and you are requesting a new password for the account associated with "
                        + user.getUsername() + ".\n\n"
                        + "Your code verify is: " + code_verify + "\n\n"
                        + "Please use this code to reset your password. If you did not request this, please ignore this email.\n\n"
                        + "\n\n"
                        + "Best regards,\n"
                        + "The L04 Group 2 team\n\n"
                        + "Please contact us in the following ways:\n"
                        + "Email: " + "spsohcmut@gmail.com\n"
                        + "Tel: 0999998386\n"
                        + "Address: 268, Ly Thuong Kiet, Ward 14, District 10, HCM City.\n");
        this.emailService.sendEmail(newEmail);

        return new RestResponse<>(HttpServletResponse.SC_OK, null, "Send code verify to email successfully", null);
    }

    public RestResponse<Object> updatePassword(UpdatePasswordDTO updatePasswordDTO) {
        User user = this.userService.getUserbyUsernameAndCodeVerify(updatePasswordDTO.getUsername(),
                updatePasswordDTO.getCodeVerify());
        if (user == null) {
            throw new RuntimeException("Invalid code verify");
        }
        String hashPassword = this.passwordEncoder.encode(updatePasswordDTO.getNewPassword());
        user.setPassword(hashPassword);
        user.setCodeVerify(null);
        this.userService.handleUpdateUser(user);
        return new RestResponse<>(HttpServletResponse.SC_OK, null, "Update password successfully", null);
    }
}