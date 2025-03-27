package com.sluja.anonym4ai.anonymization.exception;

import com.sluja.anonym4ai.exception.ExceptionWithErrorCodeAndMessageCode;

public class CodeAnonymizationStrategyNotFoundException extends ExceptionWithErrorCodeAndMessageCode{

    public CodeAnonymizationStrategyNotFoundException() {
        super(2001L, "Strategy for code anonymization not found!");
    }
}
