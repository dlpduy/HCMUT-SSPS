package com.project.SSPS.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "printing_logs", uniqueConstraints = { @UniqueConstraint(columnNames = { "file_id", "time" }) })
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PrintingLog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "printer_id", referencedColumnName = "id", nullable = false)
    private Printer printer;

    @ManyToOne
    @JoinColumn(name = "paper_type", referencedColumnName = "type", nullable = false)
    private Paper paper;

    @ManyToOne
    @NotNull(message = "File ID is mandatory")
    @JoinColumn(name = "file_id", referencedColumnName = "id", nullable = false)
    private File file;

    @NotNull(message = "Number of copies is mandatory")
    @Min(value = 1, message = "Number of copies must be at least 1")
    @Column(name = "num_copy", nullable = false)
    private Long numCopy;

    @NotNull(message = "Sided is mandatory")
    @Enumerated(EnumType.STRING)
    @Column(name = "sided", nullable = false)
    private Sided sided;

    @NotBlank(message = "Printing pages are mandatory")
    @Size(max = 100, message = "Printing pages can have at most 100 characters")
    @Column(name = "printing_pages", nullable = false)
    private String printingPages;

    @NotNull(message = "Number of pages is mandatory")
    @Min(value = 1, message = "Number of pages must be at least 1")
    @Column(name = "num_pages", nullable = false)
    private Long numPages;

    @NotNull(message = "Time is mandatory")
    @Column(name = "create_at", nullable = false)
    private LocalDateTime createAt;

    public enum Sided {
        Single,
        Double
    }

    @PrePersist
    public void prePersist() {
        createAt = LocalDateTime.now();
    }
}