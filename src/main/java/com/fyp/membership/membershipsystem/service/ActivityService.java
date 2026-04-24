package com.fyp.membership.membershipsystem.service;

import com.fyp.membership.membershipsystem.entity.Activity;
import com.fyp.membership.membershipsystem.entity.ActivityStatus;
import com.fyp.membership.membershipsystem.entity.MembershipLevel;
import com.fyp.membership.membershipsystem.repository.ActivityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class ActivityService {

    private final ActivityRepository activityRepository;

    @Autowired
    public ActivityService(ActivityRepository activityRepository) {
        this.activityRepository = activityRepository;
    }

    /* =======================
       Admin - Dashboard
       ======================= */

    public long countAllActivities() {
        return activityRepository.count();
    }

    public long countByStatus(ActivityStatus status) {
        return activityRepository.countByStatus(status);
    }

    /* =======================
       Admin - Activity List
       ======================= */

    public List<Activity> getAllActivities() {
        return activityRepository.findAllByOrderByStartDateDesc();
    }

    public List<Activity> getActivitiesByStatus(ActivityStatus status) {
        return activityRepository.findByStatusOrderByStartDateDesc(status);
    }

    /* =======================
       Admin - CRUD
       ======================= */

    public Activity createActivity(Activity activity) {
        activity.setStatus(calculateStatus(activity));
        return activityRepository.save(activity);
    }

    public Activity updateActivity(Activity activity) {
        activity.setStatus(calculateStatus(activity));
        return activityRepository.save(activity);
    }

    public Activity getActivityById(Long id) {
        return activityRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Activity not found"));
    }

    public void deleteActivity(Long id) {
        activityRepository.deleteById(id);
    }

    /* =======================
       Member - Visibility
       ======================= */

    public List<Activity> getActivitiesForMember(MembershipLevel level) {
        return activityRepository.findByTargetLevelsContains(level);
    }

    /* =======================
       Status Calculation
       ======================= */

    private ActivityStatus calculateStatus(Activity activity) {
        LocalDate today = LocalDate.now();

        if (activity.getStartDate() != null && today.isBefore(activity.getStartDate())) {
            return ActivityStatus.UPCOMING;
        }

        if (activity.getEndDate() != null && today.isAfter(activity.getEndDate())) {
            return ActivityStatus.ENDED;
        }

        return ActivityStatus.ONGOING;
    }

    /**
     * Cumulative participation
     */
    public long getTotalParticipants() {
        return activityRepository.sumTotalParticipants();
    }
}
