package com.project.SSPS.dto;

import com.project.SSPS.model.PrintingLog;
import lombok.Getter;

@Getter
public class PrintRequestDTO {
    // Getters and setters
        private Long printerId;
        private Long numCopy;
        private String paperType;
        private PrintingLog.Sided sided;
        private String printingPages;
        private Long numPages;
        private Long fileId;

    public void setNumCopy(Long numCopy) { this.numCopy = numCopy; }

    public void setPaperType(String paperType) { this.paperType = paperType; }

    public void setSided(PrintingLog.Sided sided) { this.sided = sided; }

    public void setPrintingPages(String printingPages) { this.printingPages = printingPages; }

    public void setNumPages(Long numPages) { this.numPages = numPages; }

    public void setPrinterId(Long printerId) { this.printerId = printerId; }

    public void setFileId(Long fileId) { this.fileId = fileId; }
}
