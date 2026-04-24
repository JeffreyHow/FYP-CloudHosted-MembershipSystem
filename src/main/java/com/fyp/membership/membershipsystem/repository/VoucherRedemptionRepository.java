package com.fyp.membership.membershipsystem.repository;

import com.fyp.membership.membershipsystem.entity.Coupon;
import com.fyp.membership.membershipsystem.entity.Member;
import com.fyp.membership.membershipsystem.entity.VoucherRedemption;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface VoucherRedemptionRepository
        extends JpaRepository<VoucherRedemption, Long> {

    // Total Redemptions
    long count();

    List<VoucherRedemption>
    findTop5ByMemberOrderByRedeemedAtDesc(Member member);

}

