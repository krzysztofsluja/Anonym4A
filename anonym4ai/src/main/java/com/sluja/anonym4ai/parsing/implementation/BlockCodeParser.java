package com.sluja.anonym4ai.parsing.implementation;

import com.github.javaparser.ParseProblemException;
import com.github.javaparser.StaticJavaParser;
import com.github.javaparser.ast.stmt.BlockStmt;
import com.sluja.anonym4ai.parsing.interfaces.ICodeParser;

public class BlockCodeParser implements ICodeParser<BlockStmt> {

    @Override
    public BlockStmt parse(final String code) throws ParseProblemException {
        return StaticJavaParser.parseBlock(code);
    }

}
