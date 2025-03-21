package com.sluja.anonym4ai.parsing.implementation;

import com.github.javaparser.ParseProblemException;
import com.github.javaparser.StaticJavaParser;
import com.github.javaparser.ast.CompilationUnit;
import com.sluja.anonym4ai.parsing.interfaces.ICodeParser;

public class CompilationUnitCodeParser implements ICodeParser<CompilationUnit> {
    
    @Override
    public CompilationUnit parse(final String code) throws ParseProblemException {
        return StaticJavaParser.parse(code);
    }

}
