package com.project.SSPS.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "student_papers")
public class StudentPaper {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "student_id")
    private Long studentId;

    @Column(name = "paper_type")
    private String paperType;

    @ManyToOne
    @JoinColumn(name = "paper_type", referencedColumnName = "type", insertable = false, updatable = false)
    private Paper paper;

    private Long quantity;
}
