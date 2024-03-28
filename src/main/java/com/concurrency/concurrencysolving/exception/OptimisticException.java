package com.concurrency.concurrencysolving.exception;

import org.springframework.dao.OptimisticLockingFailureException;

public class OptimisticException extends OptimisticLockingFailureException {

    public OptimisticException(String msg) {
        super("현재 락에 걸려있습니다.");
    }
}
