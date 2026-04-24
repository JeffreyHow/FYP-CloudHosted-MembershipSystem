package com.fyp.membership.membershipsystem.controller;

import com.fyp.membership.membershipsystem.entity.Coupon;
import com.fyp.membership.membershipsystem.entity.Member;
import com.fyp.membership.membershipsystem.entity.SubscriptionPlan;
import com.fyp.membership.membershipsystem.service.CouponService;
import com.fyp.membership.membershipsystem.service.MemberService;
import com.fyp.membership.membershipsystem.service.MemberSubscriptionService;
import com.fyp.membership.membershipsystem.service.SubscriptionPlanService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/member/rewards")
public class VoucherRedeemController {

    private final CouponService couponService;
    private final MemberService memberService;

    public VoucherRedeemController(
            CouponService couponService,
            MemberService memberService
    ) {
        this.couponService = couponService;
        this.memberService = memberService;
    }

    @GetMapping("/redeem/confirm/{couponId}")
    public String confirmRedeem(@PathVariable Long couponId,
                                Model model) {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Member authMember = (Member) auth.getPrincipal();
        Member member = memberService.getById(authMember.getId());

        Coupon coupon = couponService.findById(couponId);

        model.addAttribute("coupon", coupon);
        model.addAttribute("currentPoints", member.getPoints());
        model.addAttribute("balanceAfter",
                member.getPoints() - coupon.getPointsCost());

        model.addAttribute("content", "reward-redeem-confirm");
        model.addAttribute("pageTitle", "Confirm Redemption");

        return "member-layout";
    }

    @PostMapping("/redeem/{couponId}")
    public String redeem(@PathVariable Long couponId,
                         RedirectAttributes redirectAttributes) {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Member authMember = (Member) auth.getPrincipal();

        String voucherCode =
                couponService.redeemCoupon(authMember, couponId);

        redirectAttributes.addFlashAttribute("redeemSuccess", true);
        redirectAttributes.addFlashAttribute("voucherCode", voucherCode);

        return "redirect:/member/rewards/redeem/confirm/" + couponId;
    }

}
