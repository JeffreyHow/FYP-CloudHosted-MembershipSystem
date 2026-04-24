package com.fyp.membership.membershipsystem.service;

import com.fyp.membership.membershipsystem.entity.Member;
import com.fyp.membership.membershipsystem.entity.VoucherRedemption;
import com.fyp.membership.membershipsystem.repository.VoucherRedemptionRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MemberActivityService {

    private final VoucherRedemptionRepository voucherRedemptionRepository;

    public MemberActivityService(
            VoucherRedemptionRepository voucherRedemptionRepository) {
        this.voucherRedemptionRepository = voucherRedemptionRepository;
    }

    public List<VoucherRedemption> getRecentRedemptions(Member member) {
        return voucherRedemptionRepository
                .findTop5ByMemberOrderByRedeemedAtDesc(member);
    }
}


