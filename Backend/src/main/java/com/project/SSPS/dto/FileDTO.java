package com.project.SSPS.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class FileDTO {
    @NotBlank(message = "File name is required")
    private String fileName;
    @NotBlank(message = "File type is required")
    private String fileType;
}