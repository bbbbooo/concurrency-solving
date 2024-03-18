package com.concurrency.concurrencysolving.service;

import com.concurrency.concurrencysolving.domain.Coupon;
import com.concurrency.concurrencysolving.dto.CouponRequest;
import com.concurrency.concurrencysolving.dto.CouponReserveRequest;
import com.concurrency.concurrencysolving.repository.CouponRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class CouponService {
    private final CouponRepository couponRepository;

    @Transactional
    public void createCouponBySemaphore(CouponRequest couponRequest) {
        Coupon coupon = Coupon.builder()
            .couponName(couponRequest.getCouponName())
            .maxCouponCount(couponRequest.getMaxCouponCount())
            .currentCouponCount(0L)
            .build();

        couponRepository.save(coupon);
    }

    @Transactional
    public void reserveCoupon(CouponReserveRequest couponReserveRequest) {
        Coupon coupon = couponRepository.getReferenceById(couponReserveRequest.getCouponId());
        coupon.reserve();
    }
}
