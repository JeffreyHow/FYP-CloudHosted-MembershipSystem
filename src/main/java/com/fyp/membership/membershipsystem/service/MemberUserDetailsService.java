package com.fyp.membership.membershipsystem.service;

import com.fyp.membership.membershipsystem.repository.AdminUserRepository;
import com.fyp.membership.membershipsystem.repository.MemberRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class MemberUserDetailsService implements UserDetailsService {

    private final MemberRepository memberRepository;

    public MemberUserDetailsService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String phone)
            throws UsernameNotFoundException {

        return memberRepository.findByPhone(phone)
                .orElseThrow(() ->
                        new UsernameNotFoundException("Member not found"));
    }
}

