package com.example.demo.entities;


import javax.persistence.*;

@Entity
@Table
public class MyUser {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;


}
