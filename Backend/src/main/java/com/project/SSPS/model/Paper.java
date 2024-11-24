package com.project.SSPS.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;

//@Entity
@Table(name = "papers")
public class Paper {
    private String type;
    private Long price;
    private Long width;
    private Long height;
    private Long SpsoId;
}
