package com.fyp.membership.membershipsystem.service;

import com.fyp.membership.membershipsystem.entity.*;
import com.fyp.membership.membershipsystem.repository.MemberRepository;
import com.fyp.membership.membershipsystem.repository.MemberSubscriptionRepository;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class MemberSubscriptionService {

    private final MemberSubscriptionRepository memberSubscriptionRepository;
    private final MemberRepository memberRepository;

    public MemberSubscriptionService(
            MemberSubscriptionRepository memberSubscriptionRepository,
            MemberRepository memberRepository
    ) {
        this.memberSubscriptionRepository = memberSubscriptionRepository;
        this.memberRepository = memberRepository;
    }


    @Transactional
    public MemberSubscription subscribe(Member authMember, SubscriptionPlan plan) {

        // ⭐ 1. use ID from DB to take managed Member
        Member member = memberRepository.findById(authMember.getId())
                .orElseThrow(() -> new IllegalStateException("Member not found"));

        // 2. Cancel existing active subscription
        memberSubscriptionRepository
                .findFirstByMemberAndStatusOrderByStartDateDesc(
                        member, SubscriptionStatus.ACTIVE
                )
                .ifPresent(active -> {
                    active.setStatus(SubscriptionStatus.CANCELLED);
                });

        // 3. Calculate subscription period
        LocalDateTime start = LocalDateTime.now();
        LocalDateTime end;

        switch (plan.getPeriod().toLowerCase()) {
            case "week" -> end = start.plusWeeks(1);
            case "month" -> end = start.plusMonths(1);
            case "year" -> end = start.plusYears(1);
            default -> throw new IllegalStateException("Invalid plan period");
        }

        // ⭐ 4. Award points
        int bonusPoints = plan.getPoints();
        member.setPoints(member.getPoints() + bonusPoints);

        // 5. Update member subcription
        SubscriptionType subscriptionType =
                SubscriptionType.valueOf(
                        plan.getSubscriptionType().toUpperCase()
                );
        member.setSubscription(subscriptionType);

        // 6. Create new subscription
        MemberSubscription subscription = new MemberSubscription();
        subscription.setMember(member);
        subscription.setPlan(plan);
        subscription.setStartDate(start);
        subscription.setEndDate(end);
        subscription.setStatus(SubscriptionStatus.ACTIVE);
        subscription.setPointsAwarded(bonusPoints);

        return memberSubscriptionRepository.save(subscription);
    }



    // ===== Current subscription =====
    public Optional<MemberSubscription> getActiveSubscription(Member member) {
        return memberSubscriptionRepository
                .findFirstByMemberAndStatusOrderByStartDateDesc(
                        member, SubscriptionStatus.ACTIVE
                );
    }

    // ===== Subscription history =====
    public List<MemberSubscription> getSubscriptionHistory(Member member) {
        return memberSubscriptionRepository
                .findByMemberOrderByStartDateDesc(member);
    }

    // ===== Most Popular Plan =====
    public Optional<String> getMostPopularPlanType() {
        return memberSubscriptionRepository
                .findMostPopularPlanIds(PageRequest.of(0, 1)) // 只拿第一名
                .stream()
                .findFirst();
    }

    public LocalDate getNextBillingDate(Member member) {
        return getActiveSubscription(member)
                .map(sub -> sub.getEndDate()
                        .toLocalDate()
                        .plusDays(1))
                .orElse(null);
    }


    public boolean isSubscriptionActive(Member member) {
        return getActiveSubscription(member)
                .filter(sub ->
                        !sub.getEndDate()
                                .toLocalDate()
                                .isBefore(LocalDate.now())
                )
                .isPresent();
    }


}
