package com.project.SSPS.response;

import com.project.SSPS.model.PrintingLog;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class PrintingLogResponse {
    private Long studentId;
    private Long printerId;
    private String fileName;
    private String fileType;
    private Long numCopy;
    private String paperType;
    private String sided;
    private String printingPages;
    private Long numPages;
    private LocalDateTime time;

    public static PrintingLogResponse fromPrintingLog(PrintingLog printingLog) {
        PrintingLogResponse response = new PrintingLogResponse();
        response.setStudentId(printingLog.getFile().getStudent().getId());
        response.setFileName(printingLog.getFile().getFileName());
        response.setFileType(printingLog.getFile().getFileType());
        response.setPaperType(printingLog.getPaper().getType());
        response.setPrinterId(printingLog.getPrinter().getId());
        response.setNumCopy(printingLog.getNumCopy());
        response.setSided(printingLog.getSided().name());
        response.setPrintingPages(printingLog.getPrintingPages());
        response.setNumPages(printingLog.getNumPages());
        response.setTime(printingLog.getTime());
        return response;
    }
}