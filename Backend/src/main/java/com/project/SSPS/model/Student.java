package com.project.SSPS.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Data;

//@Entity
@Table(name = "students")
@Data
public class Student extends User {

    private Long numberOfPages;
}