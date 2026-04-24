package com.fyp.membership.membershipsystem.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

import static com.fyp.membership.membershipsystem.entity.SubscriptionType.*;

@Entity
@Table(name = "subscription_plans")
public class SubscriptionPlan {

    @Id
    @Column(name = "id", length = 50)
    private String subscriptionType;
    // weekly, monthly, annually（你这里是业务主键，不是自增）

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String price;
    // 人可读格式，例如 "RM39.99"

    @Column(name = "price_cents")
    private Integer priceCents;
    // 推荐用来做计算

    @Column(nullable = false)
    private String period;
    // weekly / monthly / annually（或 7 days / 30 days）

    private Integer points;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Column(columnDefinition = "TEXT")
    private String features;
    // JSON string or CSV

    @Column(nullable = false)
    private Boolean highlighted = false;

    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    /* =======================
       Lifecycle Callbacks
       ======================= */

    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        this.updatedAt = LocalDateTime.now();
    }

    /* =======================
       Getter & Setter
       ======================= */

    public String getSubscriptionType() {
        return subscriptionType;
    }

    public void setSubscriptionType(String subscriptionType) {
        this.subscriptionType = subscriptionType;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public Integer getPriceCents() {
        return priceCents;
    }

    public void setPriceCents(Integer priceCents) {
        this.priceCents = priceCents;
    }

    public String getPeriod() {
        return period;
    }

    public void setPeriod(String period) {
        this.period = period;
    }

    public Integer getPoints() {
        return points;
    }

    public void setPoints(Integer points) {
        this.points = points;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getFeatures() {
        return features;
    }

    public void setFeatures(String features) {
        this.features = features;
    }

    public Boolean getHighlighted() {
        return highlighted;
    }

    public void setHighlighted(Boolean highlighted) {
        this.highlighted = highlighted;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }
}
