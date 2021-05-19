package com.example.demo.repositories;

import com.example.demo.entities.ApplicationUser;
import com.example.demo.entities.Bill;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BillRepo extends JpaRepository<Bill, Long> {
    Optional<Bill> findByUser(ApplicationUser user);
}
