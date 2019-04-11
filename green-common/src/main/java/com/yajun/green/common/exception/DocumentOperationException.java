package com.yajun.green.common.exception;

/**
 * User: Jack Wang
 * Date: 14-4-10
 * Time: 下午3:19
 */
public class DocumentOperationException extends ApplicationException {

    public DocumentOperationException(String message) {
        super(message);
    }

    public DocumentOperationException(String message, Throwable cause) {
        super(message, cause);
    }

    public DocumentOperationException(Throwable cause) {
        super(cause);
    }
}
