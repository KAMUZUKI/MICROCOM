package com.mu.exception;

/**
 * @author MUZUKI
 * @Classname RecommendException
 * @Description TODO
 * @Date 2023/5/25 16:29
 */

public class RecommendException extends RuntimeException{
    public RecommendException(String message) {
        super(message);
    }

    public RecommendException(String message, Throwable cause) {
        super(message, cause);
    }

    public RecommendException(Throwable cause) {
        super(cause);
    }

    public RecommendException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
