package com.sluja.anonym4ai.parsing.implementation;

import java.util.Optional;

import com.github.javaparser.ParseProblemException;
import com.github.javaparser.StaticJavaParser;
import com.github.javaparser.ast.body.FieldDeclaration;
import com.sluja.anonym4ai.parsing.interfaces.ICodeParser;

public class FieldCodeParser implements ICodeParser<FieldDeclaration> {

    @Override
    public FieldDeclaration parse(final String code) throws ParseProblemException {
        return Optional.ofNullable(StaticJavaParser.parseBodyDeclaration(code).toFieldDeclaration())
                .orElseThrow(
                        () -> new ParseProblemException(new NullPointerException("Failed to parse field declaration")))
                .get();
    }
}