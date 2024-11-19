package com.project.SSPS.controller;

import com.project.SSPS.dto.UserCreationDTO;
import com.project.SSPS.model.User;
import com.project.SSPS.response.APIResponse;
import com.project.SSPS.service.AdminService;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequiredArgsConstructor
@RequestMapping("/admin")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AdminController {

    private static final Logger log = LoggerFactory.getLogger(AdminController.class);
    private final AdminService adminService;

    @PostMapping("/add/student")
    public APIResponse<User> addStudent(@Valid @RequestBody UserCreationDTO request) {
        return APIResponse.<User>builder()
                .data(adminService.addStudent(request))
                .message("Student add successfully")
                .build();
    }

    @PostMapping("/add/admin")
    public APIResponse<User>addAdmin(@Valid @RequestBody UserCreationDTO request) {
        var authentication = SecurityContextHolder.getContext().getAuthentication();
        log.info(authentication.getAuthorities().toString());
        System.out.println("Authorities: " + authentication.getAuthorities());
        return APIResponse.<User>builder()
                .data(adminService.addAdmin(request))
                .message("Admin add successfully")
                .build();
    }
}
