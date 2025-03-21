package com.sluja.anonym4ai.parsing.exception;

import com.sluja.anonym4ai.enums.CodeParsingOrderElement;

public class CodeParsingStrategyNotFoundException extends Exception {

    public CodeParsingStrategyNotFoundException(final CodeParsingOrderElement element) {
        super("Not parsing strategy found for given level: " + element.name());
    }

}
