package com.fyp.membership.membershipsystem.controller;

import com.fyp.membership.membershipsystem.entity.Activity;
import com.fyp.membership.membershipsystem.entity.ActivityStatus;
import com.fyp.membership.membershipsystem.entity.MembershipLevel;
import com.fyp.membership.membershipsystem.service.ActivityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/admin/activities")
public class AdminActivityController {

    private final ActivityService activityService;

    @Autowired
    public AdminActivityController(ActivityService activityService) {
        this.activityService = activityService;
    }

    /* =======================
       Activity List
       ======================= */

    @GetMapping
    public String listActivities(Model model) {

        long totalParticipants =
                activityService.getTotalParticipants();

        model.addAttribute("activities", activityService.getAllActivities());

        model.addAttribute("totalActivities", activityService.countAllActivities());
        model.addAttribute("upcomingCount",
                activityService.countByStatus(ActivityStatus.UPCOMING));
        model.addAttribute("ongoingCount",
                activityService.countByStatus(ActivityStatus.ONGOING));
        model.addAttribute("endedCount",
                activityService.countByStatus(ActivityStatus.ENDED));
        model.addAttribute("totalParticipants", totalParticipants);

        model.addAttribute("activePage", "activities");
        model.addAttribute("content", "admin-activities-list");

        return "admin-layout";
    }

    /* =======================
       Create Activity
       ======================= */

    @GetMapping("/add")
    public String addActivityForm(Model model) {

        model.addAttribute("activity", new Activity());
        model.addAttribute("membershipLevels", MembershipLevel.values());
        model.addAttribute("content", "admin-activities-add");

        return "admin-layout";
    }

    @PostMapping("/add")
    public String addActivity(@ModelAttribute Activity activity) {

        activityService.createActivity(activity);
        return "redirect:/admin/activities";
    }

    /* =======================
       Edit Activity
       ======================= */

    @GetMapping("/edit/{id}")
    public String editActivityForm(@PathVariable Long id, Model model) {

        Activity activity = activityService.getActivityById(id);

        model.addAttribute("activity", activity);
        model.addAttribute("membershipLevels", MembershipLevel.values());
        model.addAttribute("content", "admin-activities-edit");

        return "admin-layout";
    }

    @PostMapping("/edit/{id}")
    public String updateActivity(@PathVariable Long id,
                                 @ModelAttribute Activity activity) {

        activity.setId(id);
        activityService.updateActivity(activity);

        return "redirect:/admin/activities";
    }

    /* =======================
       Delete Activity
       ======================= */

    @PostMapping("/delete/{id}")
    public String deleteActivity(@PathVariable Long id) {

        activityService.deleteActivity(id);
        return "redirect:/admin/activities";
    }
}
