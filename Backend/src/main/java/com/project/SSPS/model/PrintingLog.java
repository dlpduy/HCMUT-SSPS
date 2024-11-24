package com.project.SSPS.model;

import java.util.Date;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;

//@Entity
@Table(name = "printing_loGs")
public class PrintingLog {
    private Long studentId;
    private Date time;

    private Long printerId;
    private String fileName;
    private Long fileType;
    private Long numCopy;
    private String paperType;
    private String sided;
    private String printingPages;
    private Long numPages;
}
