package com.fyp.membership.membershipsystem.service;

import com.fyp.membership.membershipsystem.entity.Member;
import com.fyp.membership.membershipsystem.entity.MembershipLevel;
import com.fyp.membership.membershipsystem.entity.MemberStatus;
import com.fyp.membership.membershipsystem.repository.MemberRepository;
import org.springframework.stereotype.Service;

import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Service
public class DashboardService {

    private final MemberRepository memberRepository;

    public DashboardService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    // ===== Summary Cards =====

    /** Total members (all) */
    public long getTotalMembers() {
        return memberRepository.count();
    }

    /** Active members only */
    public long getActiveMembers() {
        return memberRepository.countByStatus(MemberStatus.ACTIVE);
    }

    /** Senior members only */
    public long getSeniorMembers() {
        return memberRepository.countByLevel(MembershipLevel.SENIOR);
    }

    /** Total points (sum of all members' points) */
    public long getTotalPoints() {
        return memberRepository.getTotalPoints();
    }

    /** Average points per member */
    public long getAveragePointsPerMember() {
        long totalMembers = getTotalMembers();
        if (totalMembers == 0) {
            return 0;
        }
        return getTotalPoints() / totalMembers;
    }

    public long getJuniorCount() {
        return memberRepository.countByLevel(MembershipLevel.JUNIOR);
    }

    public long getIntermediateCount() {
        return memberRepository.countByLevel(MembershipLevel.INTERMEDIATE);
    }

    public long getSeniorCount() {
        return memberRepository.countByLevel(MembershipLevel.SENIOR);
    }

    public Map<String, Long> getRecentFiveMonthsNewMembers() {

        List<Object[]> results = memberRepository.countMembersByYearMonth();

        Map<YearMonth, Long> dbData = new HashMap<>();
        for (Object[] row : results) {
            int year = (int) row[0];
            int month = (int) row[1];
            long count = (long) row[2];

            dbData.put(YearMonth.of(year, month), count);
        }

        Map<String, Long> finalData = new LinkedHashMap<>();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMM");

        YearMonth current = YearMonth.now();

        for (int i = 4; i >= 0; i--) {
            YearMonth ym = current.minusMonths(i);
            String label = ym.format(formatter);

            finalData.put(label, dbData.getOrDefault(ym, 0L));
        }

        return finalData;
    }

    public List<Member> getTopActiveMembers() {
        return memberRepository
                .findTop5ByStatusOrderByPointsDesc(MemberStatus.ACTIVE);
    }
}
