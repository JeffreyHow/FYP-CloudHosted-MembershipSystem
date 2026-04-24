package com.fyp.membership.membershipsystem.service;

import com.fyp.membership.membershipsystem.entity.*;
import com.fyp.membership.membershipsystem.repository.CouponRepository;
import com.fyp.membership.membershipsystem.repository.MemberRepository;
import com.fyp.membership.membershipsystem.repository.VoucherCodeRepository;
import com.fyp.membership.membershipsystem.repository.VoucherRedemptionRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
public class CouponService {

    private final CouponRepository couponRepository;
    private final VoucherRedemptionRepository voucherRedemptionRepository;
    private final VoucherCodeRepository voucherCodeRepository;
    private final MemberRepository memberRepository;

    public CouponService(
            CouponRepository couponRepository,
            VoucherRedemptionRepository voucherRedemptionRepository,
            VoucherCodeRepository voucherCodeRepository,
            MemberRepository memberRepository
    ) {
        this.couponRepository = couponRepository;
        this.voucherRedemptionRepository = voucherRedemptionRepository;
        this.voucherCodeRepository = voucherCodeRepository;
        this.memberRepository = memberRepository;
    }

    // 1️⃣ Total Coupons
    public long getTotalCoupons() {
        return couponRepository.count();
    }

    // 2️⃣ Total Redemptions (claimed)
    public long getTotalRedemptions() {
        return voucherRedemptionRepository.count();
    }

    // 3️⃣ Usage Rate (used / generated)
    public double getUsageRate() {
        long totalCodes = voucherCodeRepository.count();

        if (totalCodes == 0) {
            return 0.0;
        }

        long usedCodes = voucherRedemptionRepository.count();
        return (double) usedCodes / totalCodes * 100;
    }

    /**
     * Get all coupons for admin coupon management page
     */
    public List<Coupon> findAllCoupons() {
        return couponRepository.findAll();
    }

    public void create(Coupon coupon) {

        coupon.setStatus(CouponStatus.ACTIVE);

        couponRepository.save(coupon);
    }

    public Coupon findById(Long id) {
        return couponRepository.findById(id)
                .orElseThrow(() ->
                        new EntityNotFoundException("Coupon not found: " + id));
    }

    public void update(Long id, Coupon updated) {

        Coupon coupon = findById(id);

        coupon.setName(updated.getName());
        coupon.setDiscountType(updated.getDiscountType());
        coupon.setDiscountValue(updated.getDiscountValue());
        coupon.setMinPurchase(updated.getMinPurchase());
        coupon.setUsageLimit(updated.getUsageLimit());
        coupon.setExpiryDate(updated.getExpiryDate());

        couponRepository.save(coupon);
    }

    public void delete(Long id) {

        Coupon coupon = findById(id);

        coupon.setStatus(CouponStatus.INACTIVE);

        couponRepository.save(coupon);
    }

    public List<Coupon> getAvailableCouponsForMember(Member member) {
        return couponRepository.findByStatus(CouponStatus.ACTIVE);
    }

    @Transactional
    public String redeemCoupon(Member authMember, Long couponId) {

        // 1️⃣ 拿 managed entities
        Member member = memberRepository.findById(authMember.getId())
                .orElseThrow(() -> new IllegalStateException("Member not found"));

        Coupon coupon = couponRepository.findById(couponId)
                .orElseThrow(() -> new IllegalStateException("Coupon not found"));

        // 2️⃣ Check points
        if (member.getPoints() < coupon.getPointsCost()) {
            throw new IllegalStateException("Insufficient points");
        }

        // 3️⃣ Check availability
        if (coupon.getUsedCount() >= coupon.getUsageLimit()) {
            throw new IllegalStateException("Reward is out of stock");
        }

        // 4️⃣ Deduct points & update stock
        member.setPoints(member.getPoints() - coupon.getPointsCost());
        coupon.setUsedCount(coupon.getUsedCount() + 1);

        // 5️⃣ Generate unique voucher code
        String generatedCode = generateVoucherCode(coupon);

        // 6️⃣ Create VoucherCode (claimed but not used)
        VoucherCode voucherCode = new VoucherCode();
        voucherCode.setCode(generatedCode);
        voucherCode.setCoupon(coupon);
        voucherCode.setMember(member);
        voucherCode.setUsed(false);
        voucherCode.setRedeemedAt(LocalDateTime.now());
        voucherCode.setCreatedAt(LocalDateTime.now());

        voucherCodeRepository.save(voucherCode);

        // 7️⃣ Create VoucherRedemption (audit)
        VoucherRedemption redemption = new VoucherRedemption();
        redemption.setCoupon(coupon);
        redemption.setMember(member);
        redemption.setVoucherCode(generatedCode);
        redemption.setRedeemedAt(LocalDateTime.now());

        voucherRedemptionRepository.save(redemption);

        // 8️⃣ return code to show user
        return generatedCode;
    }

    private String generateVoucherCode(Coupon coupon) {
        return "VCH-" + UUID.randomUUID()
                .toString()
                .substring(0, 8)
                .toUpperCase();

    }


}
