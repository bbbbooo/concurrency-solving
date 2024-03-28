package com.concurrency.concurrencysolving.repository;

import com.concurrency.concurrencysolving.domain.Coupon;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface CouponRepository extends JpaRepository<Coupon, Long> {

    @Lock(LockModeType.OPTIMISTIC)
    @Query("select c from Coupon c where c.id = :couponId")
    Coupon findByIdWithOptimisticLock(Long couponId);
}
