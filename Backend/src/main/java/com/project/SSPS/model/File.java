package com.project.SSPS.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "files", uniqueConstraints = {
        @UniqueConstraint(columnNames = { "student_id", "file_name", "file_type" }) })
@Data
public class File {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "file_name", nullable = false, length = 50)
    private String fileName;

    @ManyToOne
    @JoinColumn(name = "student_id", nullable = false)
    private User student;

    @ManyToOne
    @JoinColumn(name = "file_type", referencedColumnName = "type", insertable = false, updatable = false)
    private FileType fileType;
}
