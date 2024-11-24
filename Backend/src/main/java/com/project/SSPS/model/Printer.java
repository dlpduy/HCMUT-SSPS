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
@Table(name = "printers")
@Builder
public class Printer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @Column(name = "brand", length = 50, nullable = false)
    private String brand;
    @Column(name = "model", length = 50, nullable = false)
    private String model;
    //description
    @Column(name = "description", length = 100)
    private String description;
    @Column(name = "enabled", nullable = false)
    private boolean enabled;
    //CampusName
    @Column(name = "campus_name", length = 50, nullable = false)
    private String campusName;
    //buildingName
    @Column(name = "building_name", length = 10, nullable = false)
    private String buildingName;
    //roomNum
    @Column(name = "room_num", nullable = false)
    private Long roomNum;
}
