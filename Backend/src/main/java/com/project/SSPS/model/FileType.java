package com.project.SSPS.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;

//@Entity
@Table(name = "file_types")
public class FileType {
    private String type;
    private String description;
    private Long SpsoId;
}
