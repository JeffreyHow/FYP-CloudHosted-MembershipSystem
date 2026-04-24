package com.fyp.membership.membershipsystem.repository;

import com.fyp.membership.membershipsystem.entity.Member;
import com.fyp.membership.membershipsystem.entity.MemberStatus;
import com.fyp.membership.membershipsystem.entity.MembershipLevel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, String> {

    Optional<Member> findById(String id);
    Optional<Member> findByPhone(String phone);

    // ===== Dashboard Summary Cards =====
    long countByStatus(MemberStatus status);

    long countByLevel(MembershipLevel level);

    @Query("SELECT COALESCE(SUM(m.points), 0) FROM Member m")
    long getTotalPoints();

    @Query("""
    SELECT YEAR(m.createdAt), MONTH(m.createdAt), COUNT(m)
    FROM Member m
    WHERE m.createdAt IS NOT NULL
    GROUP BY YEAR(m.createdAt), MONTH(m.createdAt)
""")
    List<Object[]> countMembersByYearMonth();

    List<Member> findTop5ByStatusOrderByPointsDesc(MemberStatus status);

    @Query("""
        SELECT m FROM Member m
        WHERE
            (:keyword IS NULL OR
                LOWER(m.name) LIKE LOWER(CONCAT('%', :keyword, '%')) OR
                LOWER(m.email) LIKE LOWER(CONCAT('%', :keyword, '%')) OR
                m.phone LIKE CONCAT('%', :keyword, '%')
            )
        AND
            (:level IS NULL OR m.level = :level)
        AND
            (:status IS NULL OR m.status = :status)
    """)
    List<Member> search(
            @Param("keyword") String keyword,
            @Param("level") MembershipLevel level,
            @Param("status") MemberStatus status
    );

    @Modifying
    @Transactional
    @Query("""
        UPDATE Member m
        SET m.status = :status
        WHERE m.id = :id
    """)
    void updateStatus(
            @Param("id") String id,
            @Param("status") MemberStatus status
    );

    boolean existsByEmail(String email);

    boolean existsByPhone(String phone);
}


