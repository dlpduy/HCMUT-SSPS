package com.project.SSPS.dto;

import jakarta.validation.constraints.*;
import lombok.*;

@Getter
@Setter
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PrinterDTO {

    private Long id;

    @NotBlank(message = "Brand is mandatory")
    @Size(max = 50)
    private String brand;

    @NotBlank(message = "Model is mandatory")
    @Size(max = 50)
    private String model;

    @Size(max = 100)
    private String description;

    @NotNull
    private boolean enabled;

    @NotBlank(message = "Campus Name is mandatory")
    @Size(max = 50)
    private String campusName;

    @NotBlank(message = "Building Name is mandatory")
    @Size(max = 10)
    private String buildingName;

    @NotNull(message = "Room Num is mandatory")
    private Long roomNum;
}