package com.sluja.anonym4ai.anonymization.interfaces;

import com.github.javaparser.ast.Node;

public interface ICodeAnonymizer {

    String anonymize(final Node node);

}
