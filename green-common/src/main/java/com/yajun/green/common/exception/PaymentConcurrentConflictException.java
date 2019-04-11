package com.yajun.green.common.exception;

/**
 * User: Jack Wang
 * Date: 14-4-10
 * Time: 下午3:19
 */
public class PaymentConcurrentConflictException extends RuntimeException {

    public PaymentConcurrentConflictException() {
    }

    public PaymentConcurrentConflictException(String message) {
        super(message);
    }

    public PaymentConcurrentConflictException(String message, Throwable cause) {
        super(message, cause);
    }

    public PaymentConcurrentConflictException(Throwable cause) {
        super(cause);
    }
}
