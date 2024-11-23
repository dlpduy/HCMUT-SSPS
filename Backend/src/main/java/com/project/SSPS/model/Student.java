// package com.project.SSPS.model;

// import java.util.List;

// import com.fasterxml.jackson.annotation.JsonManagedReference;

// import jakarta.persistence.Entity;
// import jakarta.persistence.GeneratedValue;
// import jakarta.persistence.GenerationType;
// import jakarta.persistence.Id;
// import jakarta.persistence.JoinColumn;
// import jakarta.persistence.OneToMany;
// import jakarta.persistence.OneToOne;
// import jakarta.persistence.Table;
// import lombok.Data;
// import lombok.EqualsAndHashCode;

// @Entity
// @EqualsAndHashCode(callSuper = true)
// @Table(name = "students")
// public class Student extends User {

// private Long numberOfPages;

// // @OneToMany(mappedBy = "student")
// // @JsonManagedReference
// // private List<File> files;
// }