package com.concurrency.concurrencysolving.service;

import static org.junit.jupiter.api.Assertions.assertTrue;

import com.concurrency.concurrencysolving.dto.CouponReserveRequest;
import com.concurrency.concurrencysolving.repository.CouponRepository;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.OptimisticLockingFailureException;
import org.springframework.orm.ObjectOptimisticLockingFailureException;

@SpringBootTest
class CouponServiceTest {

    @Autowired
    CouponService couponService;

    @Autowired
    CouponRepository couponRepository;

    @DisplayName("낙관적락 테스트")
    @Test
    void optimisticTest() {
        // given
        CouponReserveRequest couponReserveRequest = new CouponReserveRequest(1L);
        int threadNumber = 3;
        ExecutorService executorService = Executors.newFixedThreadPool(threadNumber);

        // when
        Future<?> future = executorService.submit(
            () -> couponService.reserveCouponWithOptimisticLock(couponReserveRequest));

        Future<?> future2 = executorService.submit(
            () -> couponService.reserveCouponWithOptimisticLock(couponReserveRequest));

        Future<?> future3 = executorService.submit(
            () -> couponService.reserveCouponWithOptimisticLock(couponReserveRequest));


        // then
        try {
            future.get();
            future2.get();
            future3.get();
        } catch (ExecutionException | InterruptedException e) {
            assertTrue(e.getCause() instanceof ObjectOptimisticLockingFailureException || e.getCause() instanceof OptimisticLockingFailureException);
        }
    }
}