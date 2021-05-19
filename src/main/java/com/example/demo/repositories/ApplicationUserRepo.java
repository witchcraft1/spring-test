package com.example.demo.repositories;

import com.example.demo.entities.ApplicationUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository("repo")
public interface ApplicationUserRepo extends JpaRepository<ApplicationUser, Long>{
//    Optional<ApplicationUser> selectApplicationUserByUsername(String username);

    Optional<ApplicationUser> findByUsername(String username);
}
