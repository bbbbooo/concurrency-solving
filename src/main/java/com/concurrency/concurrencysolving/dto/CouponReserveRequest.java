package com.concurrency.concurrencysolving.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class CouponReserveRequest {
    private Long couponId;

    @JsonCreator
    public CouponReserveRequest(@JsonProperty("couponId") Long couponId) {
        this.couponId = couponId;
    }
}
