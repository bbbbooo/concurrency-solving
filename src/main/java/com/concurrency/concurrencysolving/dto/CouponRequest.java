package com.concurrency.concurrencysolving.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class CouponRequest {
    private final String couponName;
    private final Long maxCouponCount;
}
