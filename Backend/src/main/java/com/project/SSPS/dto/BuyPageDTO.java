package com.project.SSPS.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class BuyPageDTO {
        @NotBlank(message = "Paper type is mandatory")
        private String paperType;
        @NotNull(message = "Quantity is mandatory")
        private Long quantity;
}