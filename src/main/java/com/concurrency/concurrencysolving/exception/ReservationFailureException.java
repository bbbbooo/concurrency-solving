package com.concurrency.concurrencysolving.exception;

public class ReservationFailureException extends RuntimeException {

    public ReservationFailureException() {
        super("쿠폰 예약 시도가 너무 많아 실패했습니다.");
    }
}
