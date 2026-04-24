package com.fyp.membership.membershipsystem.controller;

import com.fyp.membership.membershipsystem.entity.Coupon;
import com.fyp.membership.membershipsystem.entity.Member;
import com.fyp.membership.membershipsystem.entity.MemberSubscription;
import com.fyp.membership.membershipsystem.entity.SubscriptionPlan;
import com.fyp.membership.membershipsystem.service.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/member")
public class MemberController {

    private final MemberDashboardService memberDashboardService;
    private final SubscriptionPlanService subscriptionPlanService;
    private final MemberSubscriptionService memberSubscriptionService;
    private final MemberService memberService;
    private final CouponService couponService;
    private final MemberActivityService memberActivityService;

    public MemberController(MemberActivityService memberActivityService,CouponService couponService,MemberService memberService,MemberDashboardService memberDashboardService, SubscriptionPlanService subscriptionPlanService, MemberSubscriptionService memberSubscriptionService) {
        this.memberDashboardService = memberDashboardService;
        this.subscriptionPlanService = subscriptionPlanService;
        this.memberSubscriptionService = memberSubscriptionService;
        this.memberService = memberService;
        this.couponService = couponService;
        this.memberActivityService = memberActivityService;
    }

    @GetMapping("/dashboard")
    public String dashboard(Model model) {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Member authMember = (Member) auth.getPrincipal();
        Member member = memberService.getById(authMember.getId());
        long memberDays = ChronoUnit.DAYS.between(
                member.getCreatedAt().toLocalDate(),
                LocalDate.now()
        );

        // ===== Dashboard data =====
        model.addAttribute("memberLevel",member.getLevel());
        model.addAttribute("totalPoints", member.getPoints());
        model.addAttribute("voucherCount",
                memberDashboardService.getAvailableVouchers(member));
        model.addAttribute("memberSince", member.getCreatedAt());
        model.addAttribute("memberDays", memberDays);
        model.addAttribute("subscriptionName",member.getSubscription().name());
        model.addAttribute("nextBillingDate",memberSubscriptionService.getNextBillingDate(member));
        model.addAttribute("subscriptionStatus",memberSubscriptionService.isSubscriptionActive(member));
        model.addAttribute(
                "recentRedemptions",
                memberActivityService.getRecentRedemptions(member)
        );


        // ===== Layout data =====
        model.addAttribute("memberName", member.getName());
        model.addAttribute("pageTitle", "Member Dashboard");
        model.addAttribute("activePage", "dashboard");
        model.addAttribute("content", "member-dashboard");

        return "member-layout";
    }

    @GetMapping("/plans")
    public String plans(Model model) {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Member member = (Member) auth.getPrincipal();
        Optional<MemberSubscription> activeSub =
                memberSubscriptionService.getActiveSubscription(member);
        Optional<String> mostPopularPlanType =
                memberSubscriptionService.getMostPopularPlanType();

        // ===== Most popular plan =====
        model.addAttribute(
                "mostPopularPlanType",
                mostPopularPlanType.orElse(null)
        );


        // ===== Current subscription =====
        model.addAttribute("currentPlan", activeSub.orElse(null));

        // ===== Page data =====
        model.addAttribute("plans", subscriptionPlanService.getAllPlans());

        // ===== Layout data =====
        model.addAttribute("memberName", member.getName());
        model.addAttribute("pageTitle", "Member Plans");
        model.addAttribute("activePage", "plans");
        model.addAttribute("content", "member-plans");

        return "member-layout";
    }

    @GetMapping("/rewards")
    public String rewards(Model model) {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Member authMember = (Member) auth.getPrincipal();
        Member member = memberService.getById(authMember.getId());
        List<Coupon> coupons =
                couponService.getAvailableCouponsForMember(member);

        // Available coupons
        model.addAttribute("coupons", coupons);
        // ===== Total points =====
        model.addAttribute("totalPoints", member.getPoints());

        // ===== Layout data =====
        model.addAttribute("memberName", member.getName());
        model.addAttribute("pageTitle", "Member Rewards");
        model.addAttribute("activePage", "rewards");
        model.addAttribute("content", "member-rewards");

        return "member-layout";
    }
}
