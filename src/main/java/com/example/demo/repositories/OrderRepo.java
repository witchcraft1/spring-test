package com.example.demo.repositories;

import com.example.demo.entities.ApplicationUser;
import com.example.demo.entities.RepairOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepo extends JpaRepository<RepairOrder, Long> {
    List<RepairOrder> findAllByUser(ApplicationUser user);

}
