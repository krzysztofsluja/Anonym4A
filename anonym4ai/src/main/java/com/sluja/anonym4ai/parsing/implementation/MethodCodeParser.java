package com.sluja.anonym4ai.parsing.implementation;

import com.github.javaparser.ParseProblemException;
import com.github.javaparser.StaticJavaParser;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.sluja.anonym4ai.parsing.interfaces.ICodeParser;

public class MethodCodeParser implements ICodeParser<MethodDeclaration> {

    @Override
    public MethodDeclaration parse(final String code) throws ParseProblemException {
        return StaticJavaParser.parseMethodDeclaration(code);
    }

}
