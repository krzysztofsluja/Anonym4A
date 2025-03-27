package com.sluja.anonym4ai.parsing.exception;

import com.sluja.anonym4ai.enums.CodeParsingOrderElement;
import com.sluja.anonym4ai.exception.ExceptionWithErrorCodeAndMessageCode;

public class CodeParsingStrategyNotFoundException extends ExceptionWithErrorCodeAndMessageCode {

    public CodeParsingStrategyNotFoundException(final CodeParsingOrderElement element) {
        super(1001L, "Not parsing strategy found for given level: " + element.name());
    }

    public CodeParsingStrategyNotFoundException() {
        super(1002L, "Not parsing strategy found - end of order");
    }

}
