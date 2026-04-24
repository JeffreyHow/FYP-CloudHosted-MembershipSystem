package com.fyp.membership.membershipsystem.service;

import com.fyp.membership.membershipsystem.entity.AdminUser;
import com.fyp.membership.membershipsystem.repository.AdminUserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class AdminRegisterService {

    private final AdminUserRepository adminRepository;
    private final PasswordEncoder passwordEncoder;

    public AdminRegisterService(AdminUserRepository adminRepository,
                                PasswordEncoder passwordEncoder) {
        this.adminRepository = adminRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public void register(String name,
                         String email,
                         String password,
                         String confirmPassword) {

        // 1️⃣ password & confirm password
        if (!password.equals(confirmPassword)) {
            throw new IllegalArgumentException("Passwords do not match");
        }

        // 2️⃣ email already exists?
        if (adminRepository.findByEmail(email).isPresent()) {
            throw new IllegalArgumentException("Email already registered");
        }

        // 3️⃣ create admin
        AdminUser admin = new AdminUser();
        admin.setName(name);
        admin.setEmail(email);

        // 4️⃣ BCrypt password
        admin.setPasswordHash(passwordEncoder.encode(password));

        // 5️⃣ save to DB
        adminRepository.save(admin);
    }
}

