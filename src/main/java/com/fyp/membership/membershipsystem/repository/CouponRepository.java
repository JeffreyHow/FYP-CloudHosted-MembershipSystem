package com.fyp.membership.membershipsystem.repository;

import com.fyp.membership.membershipsystem.entity.Coupon;
import com.fyp.membership.membershipsystem.entity.CouponStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CouponRepository extends JpaRepository<Coupon, Long> {

    // Total Coupons
    long count();

    List<Coupon> findByStatus(CouponStatus status);
}
