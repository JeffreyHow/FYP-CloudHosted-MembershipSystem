package com.fyp.membership.membershipsystem.service;

import com.fyp.membership.membershipsystem.entity.Activity;
import com.fyp.membership.membershipsystem.entity.Member;
import com.fyp.membership.membershipsystem.repository.ActivityRepository;
import com.fyp.membership.membershipsystem.repository.VoucherCodeRepository;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class MemberDashboardService {

    private final VoucherCodeRepository voucherCodeRepository;

    public MemberDashboardService(VoucherCodeRepository voucherCodeRepository) {
        this.voucherCodeRepository = voucherCodeRepository;
    }

    public int getAvailableVouchers(Member member) {
        return voucherCodeRepository
                .countByMemberAndIsUsedFalse(member);
    }
}

