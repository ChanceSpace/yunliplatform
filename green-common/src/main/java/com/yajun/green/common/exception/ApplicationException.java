package com.yajun.green.common.exception;

/**
 * User: Jack Wang
 * Date: 14-4-10
 * Time: 下午3:19
 */
public class ApplicationException extends RuntimeException {

    public ApplicationException() {
    }

    public ApplicationException(String message) {
        super(message);
    }

    public ApplicationException(String message, Throwable cause) {
        super(message, cause);
    }

    public ApplicationException(Throwable cause) {
        super(cause);
    }
}
