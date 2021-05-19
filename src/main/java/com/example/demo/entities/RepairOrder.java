package com.example.demo.entities;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDate;

@Data
@Entity
public class RepairOrder {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String description;

    @Enumerated(EnumType.STRING)
    private OrderStatus orderStatus;
    private Long workPrice;
    private LocalDate registerDate;

    @ManyToOne
    private ApplicationUser user;

    @ManyToOne
    private ApplicationUser master;
}
