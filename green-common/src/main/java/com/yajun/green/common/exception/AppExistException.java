package com.yajun.green.common.exception;

/**
 * User: Jack Wang
 * Date: 14-4-10
 * Time: 下午3:19
 */
public class AppExistException extends ApplicationException {

    public AppExistException(String message) {
        super(message);
    }

    public AppExistException(String message, Throwable cause) {
        super(message, cause);
    }

    public AppExistException(Throwable cause) {
        super(cause);
    }
}
