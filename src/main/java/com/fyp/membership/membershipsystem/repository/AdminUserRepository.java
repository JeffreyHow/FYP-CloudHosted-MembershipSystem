package com.fyp.membership.membershipsystem.repository;

import com.fyp.membership.membershipsystem.entity.AdminUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AdminUserRepository extends JpaRepository<AdminUser, String> {

    Optional<AdminUser> findByEmail(String email);

}
