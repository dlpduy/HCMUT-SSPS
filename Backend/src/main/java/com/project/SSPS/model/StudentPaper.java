package com.project.SSPS.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;

//@Entity
@Table(name = "student_papers")
public class StudentPaper {
    private Long studentId;
    private Long paperType;
    private Long quantity;
}
