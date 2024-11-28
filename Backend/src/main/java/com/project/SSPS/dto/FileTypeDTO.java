package com.project.SSPS.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FileTypeDTO {
    @NotBlank(message = "Type is mandatory")
    private String type;
    @NotBlank(message = "Description is mandatory")
    private String description;
}
