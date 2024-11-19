package com.project.SSPS.service;


import com.project.SSPS.dto.UserCreationDTO;
import com.project.SSPS.mapper.AdminMapper;
import com.project.SSPS.mapper.StudentMapper;
import com.project.SSPS.model.User;
import com.project.SSPS.repository.UserRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
//@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true) kien thuc moi
public class AdminService {

    private final EmailService emailService;
    private final UserRepository userRepository;
    private final StudentMapper studentMapper;
    private final AdminMapper adminMapper;
    PasswordEncoder passwordEncoder;


    public User addStudent(UserCreationDTO userCreationDTO) {
        User student = studentMapper.toUser(userCreationDTO);
        student.setPassword(passwordEncoder.encode(student.getPassword()));
        userRepository.save(student);
        var subject = "Create account successfully";
        var body = "Your account has been created successfully";
        emailService.sendMail(userCreationDTO.getEmail(), subject, body);
        return student;
    }

    public User addAdmin(UserCreationDTO userCreationDTO) {
        User admin = adminMapper.toUser(userCreationDTO);
        admin.setPassword(passwordEncoder.encode(admin.getPassword()));
        userRepository.save(admin);
        var subject = "Create account successfully";
        var body = "Your account has been created successfully";
        emailService.sendMail(userCreationDTO.getEmail(), subject, body);
        return admin;
    }
}
