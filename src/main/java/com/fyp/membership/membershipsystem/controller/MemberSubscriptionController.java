package com.fyp.membership.membershipsystem.controller;

import com.fyp.membership.membershipsystem.entity.Member;
import com.fyp.membership.membershipsystem.entity.SubscriptionPlan;
import com.fyp.membership.membershipsystem.service.MemberSubscriptionService;
import com.fyp.membership.membershipsystem.service.SubscriptionPlanService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/member")
public class MemberSubscriptionController {

    private final MemberSubscriptionService memberSubscriptionService;
    private final SubscriptionPlanService subscriptionPlanService;

    public MemberSubscriptionController(
            MemberSubscriptionService memberSubscriptionService,
            SubscriptionPlanService subscriptionPlanService
    ) {
        this.memberSubscriptionService = memberSubscriptionService;
        this.subscriptionPlanService = subscriptionPlanService;
    }

    // ===== Subscribe Now =====
    @PostMapping("/subscribe")
    public String subscribe(
            @RequestParam("planId") String planId,
            Authentication authentication
    ) {
        Member member = (Member) authentication.getPrincipal();

        SubscriptionPlan plan = subscriptionPlanService
                .getPlanById(planId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid plan"));

        memberSubscriptionService.subscribe(member, plan);

        return "redirect:/member/plans";
    }
}
