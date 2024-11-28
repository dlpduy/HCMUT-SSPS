package com.project.SSPS.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.project.SSPS.dto.FileTypeDTO;
import com.project.SSPS.model.FileType;
import com.project.SSPS.model.User;
import com.project.SSPS.repository.FileTypeRepository;
import com.project.SSPS.repository.UserRepository;
import com.project.SSPS.response.FileTypeResponse;
import com.project.SSPS.response.RestResponse;

import jakarta.servlet.http.HttpServletRequest;

@Service
public class FileTypeService {

    private final FileTypeRepository fileTypeRepository;
    private final JwtService jwtService;
    private final UserRepository userRepository;

    public FileTypeService(FileTypeRepository fileTypeRepository, JwtService jwtService,
            UserRepository userRepository) {
        this.fileTypeRepository = fileTypeRepository;
        this.jwtService = jwtService;
        this.userRepository = userRepository;

    }

    public FileTypeResponse create(FileTypeDTO fileTypeDTO, HttpServletRequest request) throws Exception {
        FileType fileType = new FileType();
        fileType.setType(fileTypeDTO.getType());
        fileType.setDescription(fileTypeDTO.getDescription());
        String token = request.getHeader("Authorization").replace("Bearer ", "");
        Long userId = jwtService.extractUserId(token);
        User user = userRepository.findById(userId).orElse(null);
        if (fileTypeRepository.existsByType(fileTypeDTO.getType())) {
            throw new Exception("File type already exists");
        }
        fileType.setUser(user);
        fileTypeRepository.save(fileType);
        return FileTypeResponse.fromFileType(fileType);
    }

    public FileTypeResponse getById(Long id) throws Exception {
        FileType fileType = fileTypeRepository.findById(id).orElse(null);
        if (fileType == null) {
            throw new Exception("File type not found");
        }
        return FileTypeResponse.fromFileType(fileType);
    }

    public List<FileTypeResponse> getAll() {
        List<FileType> fileTypes = fileTypeRepository.findAll();
        return fileTypes
                .stream()
                .map(FileTypeResponse::fromFileType)
                .collect(Collectors.toList());
    }

    public FileTypeResponse update(Long id, FileTypeDTO fileTypeDTO) {
        FileType fileType = fileTypeRepository.findById(id).orElse(null);
        if (fileType == null) {
            throw new RuntimeException("File type not found");
        }
        if (fileTypeRepository.existsByType(fileTypeDTO.getType())
                && !fileType.getType().equals(fileTypeDTO.getType())) {
            throw new RuntimeException("File type already exists, you can't update");
        }
        fileType.setType(fileTypeDTO.getType());
        fileType.setDescription(fileTypeDTO.getDescription());
        fileTypeRepository.save(fileType);
        return FileTypeResponse.fromFileType(fileType);
    }

    public RestResponse<Object> delete(Long id) {
        FileType fileType = fileTypeRepository.findById(id).orElse(null);
        if (fileType == null) {
            throw new RuntimeException("File type not found");
        }
        fileTypeRepository.delete(fileType);
        return new RestResponse<>(200, null, "File type " + fileType.getType() + "is deleted successfully", null);
    }

}
