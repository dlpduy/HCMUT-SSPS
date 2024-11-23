package com.project.SSPS.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;
@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "printer")
public class Printer {
    @Id
    @Column(name = "printer_id")
    private Long id;
    @Column(name = "brand", length = 20, nullable = false)
    private String brand;
    @Column(name = "building", length = 10, nullable = false)
    private String location;
    @Column(name = "status")
    private boolean status;
    @Column(name = "model", length = 20, nullable = false)
    private String model;

//    @OneToMany(mappedBy = "printer")
//    private List<Print> print_list;
}
