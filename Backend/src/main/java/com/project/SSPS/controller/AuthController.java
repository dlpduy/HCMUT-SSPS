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
import com.project.SSPS.model.Role;
import com.project.SSPS.model.User;
import com.project.SSPS.response.LoginResponse;
import com.project.SSPS.response.UserResponse;
import com.project.SSPS.service.JwtService;
import com.project.SSPS.service.UserService;
import com.project.SSPS.util.SecurityUtil;
import com.project.SSPS.util.annotation.ApiMessage;
import com.project.SSPS.util.errors.InvalidException;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;

@RestController
@RequestMapping("${api.prefix}/auth")
public class AuthController {

        private final PasswordEncoder passwordEncoder;
        private final UserService userService;
        private final AuthenticationManagerBuilder authenticationManagerBuilder;
        private final JwtService jwtService;

        public AuthController(PasswordEncoder passwordEncoder, UserService userService,
                        AuthenticationManagerBuilder authenticationManagerBuilder, JwtService jwtService) {
                this.passwordEncoder = passwordEncoder;
                this.userService = userService;
                this.authenticationManagerBuilder = authenticationManagerBuilder;
                this.jwtService = jwtService;
        }

        @PostMapping("/register")
        public ResponseEntity<UserResponse> register(@RequestBody RegisterDTO registerDTO) throws InvalidException {
                if (this.userService.checkUserExists(registerDTO.getUsername())) {
                        throw new InvalidException("Username is already taken");
                }
                String hashPassword = this.passwordEncoder.encode(registerDTO.getPassword());
                User newUser = new User();
                newUser.setFullName(registerDTO.getFullName());
                newUser.setUsername(registerDTO.getUsername());
                newUser.setPassword(hashPassword);
                newUser.setRole(Role.STUDENT);
                User user = this.userService.handleCreateUser(newUser);
                UserResponse userResponse = new UserResponse(user.getId(), user.getFullName(), user.getUsername(),
                                user.getRole());
                return ResponseEntity.ok(userResponse);

        }

        @PostMapping("/login")
        @ApiMessage("Login successfully")
        public ResponseEntity<LoginResponse> login( @RequestBody LoginDTO loginDTO) {
                UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                                loginDTO.getUsername(), loginDTO.getPassword());
                Authentication authentication = authenticationManagerBuilder.getObject()
                                .authenticate(authenticationToken);
                // create a token

                SecurityContextHolder.getContext().setAuthentication(authentication); // set authentication vào
                                                                                      // SecurityContext
                LoginResponse resLoginDTO = new LoginResponse();
                User userDB = this.userService.getUserbyUsername(loginDTO.getUsername());
                LoginResponse.UserLogin userLogin = new LoginResponse.UserLogin(userDB.getId(), userDB.getUsername(),
                                userDB.getFullName(), userDB.getRole());
                resLoginDTO.setUser(userLogin);
                String access_token = jwtService.generateToken(userDB);
                resLoginDTO.setAccessToken(access_token);
                return ResponseEntity.ok()
                                .header(HttpHeaders.AUTHORIZATION, "Bearer " + access_token)
                                .body(resLoginDTO);

        }

        @PostMapping("/logout")
        @ApiMessage("Logout successfully")
        public ResponseEntity<Void> logout() throws InvalidException {
                String username = SecurityUtil.getCurrentUserLogin().isPresent()
                                ? SecurityUtil.getCurrentUserLogin().get()
                                : "";
                if (username.isEmpty()) {
                        throw new InvalidException("User is not logged in");
                }
                return ResponseEntity.ok()
                                .header(HttpHeaders.AUTHORIZATION, "")
                                .body(null);
        }

}
