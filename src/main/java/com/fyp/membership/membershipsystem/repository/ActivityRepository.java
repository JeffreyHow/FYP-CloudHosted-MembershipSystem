package com.fyp.membership.membershipsystem.repository;

import com.fyp.membership.membershipsystem.entity.Activity;
import com.fyp.membership.membershipsystem.entity.ActivityStatus;
import com.fyp.membership.membershipsystem.entity.MembershipLevel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.List;

public interface ActivityRepository extends JpaRepository<Activity, Long> {

    /* =======================
       Admin - Dashboard Stats
       ======================= */

    long countByStatus(ActivityStatus status);

    /* =======================
       Admin - Activity List
       ======================= */

    List<Activity> findAllByOrderByStartDateDesc();

    List<Activity> findByStatusOrderByStartDateDesc(ActivityStatus status);

    /* =======================
       Member - Visibility
       ======================= */

    List<Activity> findByTargetLevelsContains(MembershipLevel level);

    /* =======================
       Time-based (optional)
       ======================= */

    List<Activity> findByStartDateAfter(LocalDate date);

    List<Activity> findByEndDateBefore(LocalDate date);

    @Query("SELECT COALESCE(SUM(a.participantCount), 0) FROM Activity a")
    long sumTotalParticipants();
}
