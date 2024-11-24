package com.project.SSPS.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;

//@Entity
@Table(name = "order_papers")
public class OrderPaper {

    private Long orderId;
    private String paperType;
    private Long quantity;
}
