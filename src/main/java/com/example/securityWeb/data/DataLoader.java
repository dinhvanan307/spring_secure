package com.example.securityWeb.data;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.example.securityWeb.auth.AppUser;
import com.example.securityWeb.auth.Role;
import com.example.securityWeb.auth.RoleRepository;
import com.example.securityWeb.auth.UserRepository;

@Configuration
public class DataLoader {

  @Bean
  CommandLineRunner initData(RoleRepository roleRepo, UserRepository userRepo, PasswordEncoder encoder) {
    return args -> {
      Role roleUser = roleRepo.findByName("ROLE_USER").orElseGet(() -> roleRepo.save(new Role("ROLE_USER")));
      Role roleAdmin = roleRepo.findByName("ROLE_ADMIN").orElseGet(() -> roleRepo.save(new Role("ROLE_ADMIN")));

      if (userRepo.findByUsername("user").isEmpty()) {
        AppUser user = new AppUser("user", encoder.encode("password"));
        user.getRoles().add(roleUser);
        userRepo.save(user);
      }

      if (userRepo.findByUsername("admin").isEmpty()) {
        AppUser admin = new AppUser("admin", encoder.encode("password"));
        admin.getRoles().add(roleUser);
        admin.getRoles().add(roleAdmin);
        userRepo.save(admin);
      }
    };
  }
}
