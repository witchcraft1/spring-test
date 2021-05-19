package com.example.demo.repositories;

import com.example.demo.entities.ApplicationUser;
import static com.example.demo.security.UserRole.*;

import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository("fake") // "fake" is just a name of this Repository, which can be used with @Autowired to exactly define right Repository class
public class FakeApplicationUserDaoService implements ApplicationUserDao{
    private final PasswordEncoder passwordEncoder;

    @Autowired
    FakeApplicationUserDaoService(PasswordEncoder passwordEncoder){
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public Optional<ApplicationUser> selectApplicationUserByUsername(String username) {
        return getAllApplicationUsers().stream()
                .filter(user -> username.equals(user.getUsername()))
                .findFirst();
    }

    private List<ApplicationUser> getAllApplicationUsers(){
        return Lists.newArrayList(
                new ApplicationUser(
                        "muriel1",
                        passwordEncoder.encode("muriel74"),
                        STUDENT/*.getGrantedPermissions()*/,
                        true,true,true,true
                ),
                new ApplicationUser(
                        "admin",
                        passwordEncoder.encode("admin123"),
                        ADMIN/*.getGrantedPermissions()*/,
                        true,true,true,true
                ),
                new ApplicationUser(
                        "adminTom",
                        passwordEncoder.encode("admin123"),
                        ADMINTRAINEE/*.getGrantedPermissions()*/,
                        true,true,true,true
                )
        );
    }
}
