package com.fyp.membership.membershipsystem.service;

import com.fyp.membership.membershipsystem.entity.AdminUser;
import com.fyp.membership.membershipsystem.entity.Member;
import com.fyp.membership.membershipsystem.repository.AdminUserRepository;
import com.fyp.membership.membershipsystem.repository.MemberRepository;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final MemberRepository memberRepository;
    private final AdminUserRepository adminUserRepository;

    public AuthService(MemberRepository memberRepository,
                       AdminUserRepository adminUserRepository) {
        this.memberRepository = memberRepository;
        this.adminUserRepository = adminUserRepository;
    }

    // ===============================
    // Member Login
    // ===============================
    public Member loginMember(String phone, String password) {

        Member member = memberRepository.findByPhone(phone)
                .orElseThrow(() -> new RuntimeException("Member not found"));

        if (!member.getPasswordHash().equals(password)) {
            throw new RuntimeException("Invalid password");
        }

        if (!member.getStatus().name().equals("ACTIVE")) {
            throw new RuntimeException("Member is inactive");
        }

        return member; // login success
    }

    // ===============================
    // Admin Login
    // ===============================
    public AdminUser loginAdmin(String email, String password) {

        AdminUser admin = adminUserRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Admin not found"));

        if (!admin.getPasswordHash().equals(password)) {
            throw new RuntimeException("Invalid password");
        }

        return admin; // login success
    }
}
