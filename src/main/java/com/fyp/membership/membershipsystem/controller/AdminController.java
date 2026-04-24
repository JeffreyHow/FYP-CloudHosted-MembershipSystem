package com.fyp.membership.membershipsystem.controller;

import com.fyp.membership.membershipsystem.entity.*;
import com.fyp.membership.membershipsystem.service.ActivityService;
import com.fyp.membership.membershipsystem.service.DashboardService;
import com.fyp.membership.membershipsystem.service.MemberService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private final DashboardService dashboardService;
    private final MemberService memberService;
    private final ActivityService activityService;

    public AdminController(DashboardService dashboardService, MemberService memberService,ActivityService activityService) {

        this.dashboardService = dashboardService;
        this.memberService = memberService;
        this.activityService = activityService;
    }

    @GetMapping("/dashboard")
    public String dashboard(Model model) {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        AdminUser adminUser = (AdminUser) auth.getPrincipal();
        Map<String, Long> monthlyData = dashboardService.getRecentFiveMonthsNewMembers();

        // summary cards
        model.addAttribute("totalMembers", dashboardService.getTotalMembers());
        model.addAttribute("activeMembers", dashboardService.getActiveMembers());
        model.addAttribute("seniorMembers", dashboardService.getSeniorMembers());
        model.addAttribute("totalPoints", dashboardService.getTotalPoints());
        model.addAttribute("avgPoints", dashboardService.getAveragePointsPerMember());
        model.addAttribute("ongoingActivities",activityService.countByStatus(ActivityStatus.ONGOING));

        // layout data
        model.addAttribute("adminName", adminUser.getName());
        model.addAttribute("pageTitle", "Admin Dashboard");
        model.addAttribute("activePage", "overview");
        model.addAttribute("content", "admin-dashboard");

        // pie chart data
        model.addAttribute("juniorCount", dashboardService.getJuniorCount());
        model.addAttribute("intermediateCount", dashboardService.getIntermediateCount());
        model.addAttribute("seniorCount", dashboardService.getSeniorCount());

        // bar chart data
        model.addAttribute("monthlyLabels", monthlyData.keySet());
        model.addAttribute("monthlyValues", monthlyData.values());

        // top 5 members
        model.addAttribute("topMembers",
                dashboardService.getTopActiveMembers());

        return "admin-layout";
    }

    @GetMapping("/members")
    public String members(@RequestParam(required = false) String keyword,
                          @RequestParam(required = false) MembershipLevel level,
                          @RequestParam(required = false) MemberStatus status, Model model) {

        Authentication auth = SecurityContextHolder
                .getContext()
                .getAuthentication();

        AdminUser adminUser = (AdminUser) auth.getPrincipal();

        // === members data (search + filter) ===
        List<Member> members = memberService.searchMembers(
                keyword,
                level,
                status
        );
        model.addAttribute("members", members);

        // === keep filter state ===
        model.addAttribute("keyword", keyword);
        model.addAttribute("selectedLevel", level);
        model.addAttribute("selectedStatus", status);

        // === dropdown options ===
        model.addAttribute("levels", MembershipLevel.values());
        model.addAttribute("statuses", MemberStatus.values());

        // === layout data ===
        model.addAttribute("adminName", adminUser.getName());
        model.addAttribute("pageTitle", "Members");
        model.addAttribute("activePage", "members");
        model.addAttribute("content", "admin-members");

        return "admin-layout";
    }

    // =====================
    // Member soft delete (POST)
    // =====================
    @PostMapping("/members/delete/{id}")
    public String deleteMember(
            @PathVariable String id,
            HttpServletRequest request
    ) {
        memberService.softDelete(id);

        String referer = request.getHeader("Referer");
        return "redirect:" + referer;
    }

    @GetMapping("/members/edit/{id}")
    public String editMemberForm(@PathVariable String id, Model model) {

        Member member = memberService.getById(id);

        model.addAttribute("member", member);
        model.addAttribute("levels", MembershipLevel.values());

        model.addAttribute("content", "admin-member-edit");

        return "admin-layout";
    }

    @PostMapping("/members/edit/{id}")
    public String updateMember(
            @PathVariable String id,
            @ModelAttribute Member member
    ) {
        memberService.updateMember(id, member);
        return "redirect:/admin/members";
    }

    @GetMapping("/members/add")
    public String addMemberForm(Model model) {
        model.addAttribute("member", new Member());
        model.addAttribute("levels", MembershipLevel.values());
        model.addAttribute("content", "admin-member-add");
        return "admin-layout";
    }

    @PostMapping("/members/add")
    public String createMember(
            @Valid @ModelAttribute("member") Member member,
            BindingResult bindingResult,
            Model model
    ) {

        if (bindingResult.hasErrors()) {
            model.addAttribute("levels", MembershipLevel.values());
            model.addAttribute("content", "admin-member-add");
            return "admin-layout";
        }

        try {
            memberService.create(member);

        } catch (IllegalArgumentException ex) {
            bindingResult.reject("globalError", ex.getMessage());
            model.addAttribute("levels", MembershipLevel.values());
            model.addAttribute("content", "admin-member-add");
            return "admin-layout";
        }

        return "redirect:/admin/members";
    }


}

