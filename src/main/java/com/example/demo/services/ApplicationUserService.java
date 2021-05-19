package com.example.demo.services;


import com.example.demo.entities.ApplicationUser;
import com.example.demo.entities.Bill;
import com.example.demo.exceptions.UserAlreadyExistsException;
import com.example.demo.repositories.ApplicationUserRepo;
import com.example.demo.repositories.BillRepo;
import com.example.demo.security.UserRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service/*("applicationUserService")*/
public class ApplicationUserService implements UserDetailsService {
    private final ApplicationUserRepo applicationUserRepo;
    private final BillRepo billRepo;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public ApplicationUserService(
            /*if we have more that 1 implementation of ApplicationUserDao marked with @Repository then we can use @Qualifier to identify desired @Repository*/
            /*@Qualifier("fake")*/ @Qualifier("repo") ApplicationUserRepo applicationUserRepo, BillRepo billRepo, PasswordEncoder passwordEncoder) {
        this.applicationUserRepo = applicationUserRepo;
        this.billRepo = billRepo;
        this.passwordEncoder = passwordEncoder;
    }


    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        return applicationUserRepo.findByUsername/*selectApplicationUserByUsername*/(s)
                .orElseThrow(()->
                        new UsernameNotFoundException(String.format("User with username %s not found", s))
                );
    }

    public ApplicationUser registerNewApplicationUser(String email, String pass){
        Optional<ApplicationUser> userOptional = applicationUserRepo.findByUsername(email);
        if(userOptional.isPresent()){
            throw new UserAlreadyExistsException();
        }

        ApplicationUser savedUser = applicationUserRepo.save(new ApplicationUser(
                email,
                passwordEncoder.encode(pass),
                UserRole.ADMIN,
                true,
                true,
                true,
                true
        ));

        billRepo.save(new Bill(savedUser));

        return savedUser;
    }
}
