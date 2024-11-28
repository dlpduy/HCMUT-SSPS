package com.project.SSPS.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PaperDTO {
    @NotBlank(message = "Type is mandatory")
    private String type;
    @NotNull(message = "Description is mandatory")
    private Long width;
    @NotNull(message = "Height is mandatory")
    private Long height;
    @NotNull(message = "Price is mandatory")
    private Long price;
}
