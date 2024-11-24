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
    @Column(name = "id")
    private Long id;
    @Column(name = "brand", length = 100, nullable = false)
    private String brand;
    @Column(name = "model", length = 100, nullable = false)
    private String model;
    @Column(name = "description", length = 100, nullable = false)
    private String description;
    @Column(name = "status")
    private boolean status;
    @Column(name = "campus_name", length = 100, nullable = false)
    private String campusName;
    @Column(name = "building_name", length = 100, nullable = false)
    private String buildingName;
    @Column(name = "room_num", length = 100, nullable = false)
    private String roomNum;

    // @OneToMany(mappedBy = "printer")
    // private List<Print> print_list;
}
