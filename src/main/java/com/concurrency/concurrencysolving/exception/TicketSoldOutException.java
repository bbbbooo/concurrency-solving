package com.concurrency.concurrencysolving.exception;

public class TicketSoldOutException extends IllegalArgumentException {

    public TicketSoldOutException() {
        super("티켓이 모두 팔렸습니다.");
    }
}
