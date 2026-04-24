package com.fyp.membership.membershipsystem.service;

import com.fyp.membership.membershipsystem.entity.Member;
import com.fyp.membership.membershipsystem.entity.MemberStatus;
import com.fyp.membership.membershipsystem.entity.MembershipLevel;
import com.fyp.membership.membershipsystem.repository.MemberRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class MemberService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    public MemberService(MemberRepository memberRepository,PasswordEncoder passwordEncoder) {
        this.memberRepository = memberRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public List<Member> getAllMembers() {
        return memberRepository.findAll();
    }

    /**
     * Search members with optional keyword, level, status
     */
    public List<Member> searchMembers(
            String keyword,
            MembershipLevel level,
            MemberStatus status
    ) {

        // keyword process
        if (keyword != null) {
            keyword = keyword.trim();
            if (keyword.isEmpty()) {
                keyword = null;
            }
        }

        return memberRepository.search(keyword, level, status);
    }

    /**
     * Soft delete member (set status = INACTIVE)
     */
    @Transactional
    public void softDelete(String id) {
        memberRepository.updateStatus(id, MemberStatus.INACTIVE);
    }

    public Member getById(String id) {
        return memberRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Member not found"));
    }

    public void updateMember(String id, Member updated) {

        Member member = memberRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Member not found"));

        // === update allowed fields only ===
        member.setName(updated.getName());
        member.setPhone(updated.getPhone());
        member.setEmail(updated.getEmail());
        member.setLevel(updated.getLevel());
        member.setPoints(updated.getPoints());

        memberRepository.save(member);
    }


    // =========================
    // Create new member
    // =========================
    public void create(Member member) {

        if (memberRepository.existsByEmail(member.getEmail())) {
            throw new IllegalArgumentException("Email already exists");
        }
        if (memberRepository.existsByPhone(member.getPhone())) {
            throw new IllegalArgumentException("Phone number already exists");
        }

        member.setStatus(MemberStatus.ACTIVE);

        memberRepository.save(member);
    }

    public void registerMember(
            String name,
            String phone,
            String email,
            String rawPassword
    ) {

        if (memberRepository.existsByEmail(email)) {
            throw new IllegalArgumentException("Email already exists");
        }

        if (memberRepository.existsByPhone(phone)) {
            throw new IllegalArgumentException("Phone number already exists");
        }

        Member member = new Member();
        member.setName(name);
        member.setPhone(phone);
        member.setEmail(email);
        member.setPasswordHash(passwordEncoder.encode(rawPassword));

        memberRepository.save(member);
    }



}
