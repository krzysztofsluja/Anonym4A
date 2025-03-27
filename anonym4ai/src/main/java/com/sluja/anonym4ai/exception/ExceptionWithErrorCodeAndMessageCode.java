package com.sluja.anonym4ai.exception;

import lombok.Getter;

@Getter
public class ExceptionWithErrorCodeAndMessageCode extends Exception{

    private final long errorCode;
    private final String errorMessageCode;

    public ExceptionWithErrorCodeAndMessageCode(final long errorCode, final String messageCode) {
        super(messageCode);
        this.errorCode = errorCode;
        this.errorMessageCode = messageCode;
    }
}
