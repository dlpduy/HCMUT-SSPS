package com.project.SSPS.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "order_papers")
public class OrderPaper extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "order_id", referencedColumnName = "id")
    private Order order;

    @Column(name = "paper_type")
    private String paperType;

    private Long quantity;

    @ManyToOne
    @JoinColumn(name = "paper_type", referencedColumnName = "type", insertable = false, updatable = false)
    private Paper paper;
}
