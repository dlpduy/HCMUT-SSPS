package com.project.SSPS.dto;

import com.project.SSPS.model.PrintingLog;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class PrintRequestDTO {
    // Getters and setters
    @NotNull(message = "Printer ID is required")
    private Long printerId;
    @NotNull(message = "Number of copies is required")
    private Long numCopy;
    @NotBlank(message = "Paper type is required")
    private String paperType;
    @NotNull(message = "Sided is required")
    private PrintingLog.Sided sided;
    @NotBlank(message = "Printing pages is required")
    private String printingPages;
    @NotNull(message = "Number of pages is required")
    private Long numPages;
    @NotNull(message = "File ID is required")
    private Long fileId;

    public void setNumCopy(Long numCopy) {
        this.numCopy = numCopy;
    }

    public void setPaperType(String paperType) {
        this.paperType = paperType;
    }

    public void setSided(PrintingLog.Sided sided) {
        this.sided = sided;
    }

    public void setPrintingPages(String printingPages) {
        this.printingPages = printingPages;
    }

    public void setNumPages(Long numPages) {
        this.numPages = numPages;
    }

    public void setPrinterId(Long printerId) {
        this.printerId = printerId;
    }

    public void setFileId(Long fileId) {
        this.fileId = fileId;
    }
}
