package com.concurrency.concurrencysolving.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table
public class Coupon {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Long id;

    private String couponName;
    private Long maxCouponCount;
    private Long currentCouponCount;

    @Builder
    public Coupon(Long id, String couponName, Long maxCouponCount, Long currentCouponCount) {
        this.id = id;
        this.couponName = couponName;
        this.maxCouponCount = maxCouponCount;
        this.currentCouponCount = currentCouponCount;
    }

    public void reserve() {
        if (maxCouponCount <= currentCouponCount) {
            throw new IllegalArgumentException();
        }

        currentCouponCount++;
    }
}
