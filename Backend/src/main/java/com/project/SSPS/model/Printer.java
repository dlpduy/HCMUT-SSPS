package com.project.SSPS.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "printer")
@Builder
public class Printer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "printer_id")
    private Long id;
    @Column(name = "brand", length = 100, nullable = false)
    private String brand;
    @Column(name = "building", length = 100, nullable = false)
    private String location;
    @Column(name = "status")
    private boolean status;
    @Column(name = "model", length = 100, nullable = false)
    private String model;

//    @OneToMany(mappedBy = "printer")
//    private List<Print> print_list;
}
