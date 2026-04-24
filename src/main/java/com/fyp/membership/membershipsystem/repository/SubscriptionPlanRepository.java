package com.fyp.membership.membershipsystem.repository;

import com.fyp.membership.membershipsystem.entity.SubscriptionPlan;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SubscriptionPlanRepository
        extends JpaRepository<SubscriptionPlan, String> {
}
