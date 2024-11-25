package com.project.SSPS.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PaperDTO {
    private String type;

    private Long width;

    private Long height;

    private Long price;
}
