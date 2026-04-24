package com.fyp.membership.membershipsystem.repository;

import com.fyp.membership.membershipsystem.entity.Member;
import com.fyp.membership.membershipsystem.entity.MemberSubscription;
import com.fyp.membership.membershipsystem.entity.SubscriptionStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import org.springframework.data.domain.Pageable;
import java.util.List;
import java.util.Optional;

public interface MemberSubscriptionRepository
        extends JpaRepository<MemberSubscription, Long> {

    Optional<MemberSubscription> findByMemberAndStatus(
            Member member,
            SubscriptionStatus status
    );

    Optional<MemberSubscription> findFirstByMemberAndStatusOrderByStartDateDesc(
            Member member,
            SubscriptionStatus status
    );

    List<MemberSubscription> findByMemberOrderByStartDateDesc(Member member);

    @Query("""
    SELECT ms.plan.subscriptionType
    FROM MemberSubscription ms
    GROUP BY ms.plan.subscriptionType
    ORDER BY COUNT(ms.id) DESC
""")
    List<String> findMostPopularPlanIds(Pageable pageable);
}
