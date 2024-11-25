package com.project.SSPS.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@Data
@NoArgsConstructor
public class PrintingLogListResponse {
    private List<PrintingLogResponse> printingLogs;
    private int totalPages;
}
