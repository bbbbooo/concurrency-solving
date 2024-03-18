package com.concurrency.concurrencysolving.repository;

import com.concurrency.concurrencysolving.domain.Coupon;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CouponRepository extends JpaRepository<Coupon, Long> {

}
