package com.sluja.anonym4ai.parsing.implementation;

import com.github.javaparser.ParseProblemException;
import com.github.javaparser.StaticJavaParser;
import com.github.javaparser.ast.type.ClassOrInterfaceType;
import com.sluja.anonym4ai.parsing.interfaces.ICodeParser;

public class ClassCodeParser implements ICodeParser<ClassOrInterfaceType> {

    @Override
    public ClassOrInterfaceType parse(final String code) throws ParseProblemException {
        return StaticJavaParser.parseClassOrInterfaceType(code);
    }

}
