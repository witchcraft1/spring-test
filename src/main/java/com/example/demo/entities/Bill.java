package com.example.demo.entities;


import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
public class Bill {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @OneToOne
    private ApplicationUser user;
    private Long cashAvailable = 0L;
    private Long cashSpent = 0L;
    private String credit_cart_details;

    public Bill(ApplicationUser user){
        this.user = user;
    }
    public Bill(){}
}
