package com.concurrency.concurrencysolving.controller;

import com.concurrency.concurrencysolving.dto.CouponRequest;
import com.concurrency.concurrencysolving.dto.CouponReserveRequest;
import com.concurrency.concurrencysolving.service.CouponService;
import java.util.concurrent.Semaphore;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/coupon")
@Slf4j
public class CouponController {
    private final CouponService couponService;
    private final Semaphore semaphore;
    private final Lock lock = new ReentrantLock();

    /**
     * 세마포어
     * @throws com.concurrency.concurrencysolving.exception.TicketSoldOutException 티켓 매진
     */
    @PostMapping("/semaphore")
    public ResponseEntity<String> reserveWithSemaphore(@RequestBody CouponReserveRequest couponReserveRequest) {
        try {
            semaphore.acquire();

            couponService.reserveCoupon(couponReserveRequest);
            log.info(Thread.currentThread().getName() + " 예약 성공");
            return ResponseEntity.ok("예약 완료");
        } catch (InterruptedException e) {
            log.error(e.getMessage());
            return ResponseEntity.ok(Thread.currentThread().getName() + " 예약 실패");
        } finally {
            semaphore.release();
        }
    }

    /**
     * 뮤텍스
     * @throws com.concurrency.concurrencysolving.exception.TicketSoldOutException 티켓 매진
     */
    @PostMapping("/mutex")
    public ResponseEntity<String> reserveWithMutex(@RequestBody CouponReserveRequest couponReserveRequest) {
        try {
            lock.lock();

            couponService.reserveCoupon(couponReserveRequest);
            log.info(Thread.currentThread().getName() + " 예약 성공");
            return ResponseEntity.ok("예약 완료");
        }finally {
            lock.unlock();
        }
    }

    @PostMapping("/optimistic")
    public ResponseEntity<String> reserveWithOptimisticLock(@RequestBody CouponReserveRequest couponReserveRequest) {
        couponService.reserveCouponWithOptimisticLock(couponReserveRequest);

        return ResponseEntity.ok("예약 완료");
    }

    @PostMapping
    public ResponseEntity<String> create(@RequestBody CouponRequest couponRequest) {
        couponService.createCouponBySemaphore(couponRequest);

        return ResponseEntity.ok("쿠폰 생성 성공");
    }
}
