package com.example.demo.entities;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder

@Entity
@Table
public class Student {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    private String name;
    private String email;
    private LocalDate dob;
    private Integer age;

    public static void main(String[] args) {
        new Student(1L,"","",LocalDate.now(),15) ;
    }
}


