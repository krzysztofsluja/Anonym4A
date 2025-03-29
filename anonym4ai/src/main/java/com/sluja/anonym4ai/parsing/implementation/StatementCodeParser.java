package com.sluja.anonym4ai.parsing.implementation;

import com.github.javaparser.ParseProblemException;
import com.github.javaparser.StaticJavaParser;
import com.github.javaparser.ast.stmt.Statement;
import com.sluja.anonym4ai.parsing.interfaces.ICodeParser;

public class StatementCodeParser implements ICodeParser<Statement> {

    @Override
    public Statement parse(final String code) throws ParseProblemException {
        return StaticJavaParser.parseStatement(code);
    }
}
