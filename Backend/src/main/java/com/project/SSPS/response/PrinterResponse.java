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
    private String buildingName;
    private String campusName;
    private String description;
    private String model;
    private Long roomNum;
    private boolean status;

    public static PrinterResponse fromPrinter(Printer printer) {
        return PrinterResponse.builder()
                .id(printer.getId())
                .brand(printer.getBrand())
                .buildingName(printer.getBuildingName())
                .campusName(printer.getCampusName())
                .description(printer.getDescription())
                .model(printer.getModel())
                .roomNum(printer.getRoomNum())
                .status(printer.isStatus())
                .build();
    }
}
