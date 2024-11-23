package com.project.SSPS.response;

import com.project.SSPS.model.Printer;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PrinterResponse {
    private Long id;
    private String brand;
    private String model;
    private String location;
    private boolean status;

    public static PrinterResponse fromPrinter(Printer printer) {
        return PrinterResponse.builder()
                .id(printer.getId())
                .brand(printer.getBrand())
                .model(printer.getModel())
                .location(printer.getLocation())
                .status(printer.isStatus())
                .build();
    }
}
