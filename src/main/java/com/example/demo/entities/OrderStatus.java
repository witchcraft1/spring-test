package com.example.demo.entities;

public enum OrderStatus {
    WAITING_FOR_MASTER,
    WAITING_FOR_BILLING,
    BILLED,
    REJECTED,
    IN_PROCESS,
    DONE
}
