package com.fyp.membership.membershipsystem.repository;

import com.fyp.membership.membershipsystem.entity.Coupon;
import com.fyp.membership.membershipsystem.entity.Member;
import com.fyp.membership.membershipsystem.entity.VoucherCode;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface VoucherCodeRepository
        extends JpaRepository<VoucherCode, Long> {

    long countByCoupon(Coupon coupon);

    long countByCouponAndUsedTrue(Coupon coupon);

    Optional<VoucherCode> findByCode(String code);

    long count();

    long countByUsedTrue();

    int countByMemberAndIsUsedFalse(Member member);
}

