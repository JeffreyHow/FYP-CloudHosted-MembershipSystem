package com.fyp.membership.membershipsystem.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "rewards")
public class Reward {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Column(nullable = false)
    private int pointsCost;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private RewardType type;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "coupon_id")
    private Coupon coupon;

    @Column(nullable = false)
    private int availability;

    @Column(nullable = false)
    private boolean popular = false;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private RewardStatus status;

    @Column(nullable = false)
    private LocalDateTime createdAt;

    @Column(nullable = false)
    private LocalDateTime updatedAt;

    // ===== Constructors =====

    public Reward() {
    }

    public Reward(String name, int pointsCost, RewardType type) {
        this.name = name;
        this.pointsCost = pointsCost;
        this.type = type;
        this.status = RewardStatus.ACTIVE;
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    // ===== Getters & Setters =====

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getPointsCost() {
        return pointsCost;
    }

    public RewardType getType() {
        return type;
    }

    public Coupon getCoupon() {
        return coupon;
    }

    public void setCoupon(Coupon coupon) {
        this.coupon = coupon;
    }

    public int getAvailability() {
        return availability;
    }

    public boolean isPopular() {
        return popular;
    }

    public RewardStatus getStatus() {
        return status;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }
}
