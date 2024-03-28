package com.concurrency.concurrencysolving.service;

import com.concurrency.concurrencysolving.domain.Coupon;
import com.concurrency.concurrencysolving.dto.CouponRequest;
import com.concurrency.concurrencysolving.dto.CouponReserveRequest;
import com.concurrency.concurrencysolving.exception.OptimisticException;
import com.concurrency.concurrencysolving.exception.ReservationFailureException;
import com.concurrency.concurrencysolving.repository.CouponRepository;
import jakarta.persistence.OptimisticLockException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class CouponService {
    private final CouponRepository couponRepository;
    private int lockCount = 0;


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

    @Transactional
    public void reserveCouponWithOptimisticLock(CouponReserveRequest couponReserveRequest) {
        try {
            if (lockCount > 4) {
                throw new ReservationFailureException();
            }

            Coupon coupon = couponRepository.findByIdWithOptimisticLock(couponReserveRequest.getCouponId());
            coupon.reserve();
        } catch (OptimisticException e) {
            try {
            log.error(e.getMessage());
                Thread.sleep(50);
            } catch (InterruptedException ignore) {
            }
            lockCount++;
            reserveCouponWithOptimisticLock(couponReserveRequest);
        }
    }
}
