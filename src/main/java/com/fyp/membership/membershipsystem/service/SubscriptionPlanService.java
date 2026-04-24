package com.fyp.membership.membershipsystem.service;

import com.fyp.membership.membershipsystem.entity.SubscriptionPlan;
import com.fyp.membership.membershipsystem.repository.SubscriptionPlanRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SubscriptionPlanService {

    private final SubscriptionPlanRepository subscriptionPlanRepository;

    public SubscriptionPlanService(SubscriptionPlanRepository subscriptionPlanRepository) {
        this.subscriptionPlanRepository = subscriptionPlanRepository;
    }

    public List<SubscriptionPlan> getAllPlans() {
        return subscriptionPlanRepository.findAll();
    }

    public Optional<SubscriptionPlan> getPlanById(String id) {
        return subscriptionPlanRepository.findById(id);
    }

}
