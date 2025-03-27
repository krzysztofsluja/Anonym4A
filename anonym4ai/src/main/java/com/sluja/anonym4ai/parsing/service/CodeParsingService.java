package com.sluja.anonym4ai.parsing.service;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Function;

import com.github.javaparser.ParseProblemException;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.Node;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.stmt.BlockStmt;
import com.github.javaparser.ast.stmt.Statement;
import com.github.javaparser.ast.type.ClassOrInterfaceType;
import com.sluja.anonym4ai.enums.CodeParsingOrderElement;
import com.sluja.anonym4ai.exception.ExceptionWithErrorCodeAndMessageCode;
import com.sluja.anonym4ai.parsing.exception.CodeParsingStrategyNotFoundException;
import com.sluja.anonym4ai.parsing.implementation.BlockCodeParser;
import com.sluja.anonym4ai.parsing.implementation.ClassCodeParser;
import com.sluja.anonym4ai.parsing.implementation.CompilationUnitCodeParser;
import com.sluja.anonym4ai.parsing.implementation.MethodCodeParser;
import com.sluja.anonym4ai.parsing.implementation.StatementCodeParser;
import com.sluja.anonym4ai.parsing.interfaces.ICodeParser;

import lombok.AllArgsConstructor;
import lombok.Getter;

public class CodeParsingService {

    private final ICodeParser<ClassOrInterfaceType> classCodeParser;
    private final ICodeParser<BlockStmt> blockCodeParser;
    private final ICodeParser<CompilationUnit> compilationUnitCodeParser;
    private final ICodeParser<MethodDeclaration> methodCodeParser;
    private final ICodeParser<Statement> statementCodeParser;
    private final Map<CodeParsingOrderElement, CodeParsingStrategy<? extends Node>> strategies;


    public CodeParsingService() {
        classCodeParser = (ICodeParser<ClassOrInterfaceType>) new ClassCodeParser();
        blockCodeParser = (ICodeParser<BlockStmt>) new BlockCodeParser();
        compilationUnitCodeParser = (ICodeParser<CompilationUnit>) new CompilationUnitCodeParser();
        methodCodeParser = (ICodeParser<MethodDeclaration>) new MethodCodeParser();
        statementCodeParser = (ICodeParser<Statement>) new StatementCodeParser();
        this.strategies = initializeStrategies();
    }
    
    private Map<CodeParsingOrderElement, CodeParsingStrategy<? extends Node>> initializeStrategies() {
        return Map.of(
        CodeParsingOrderElement.COMPILATION_UNIT, 
                       new CodeParsingStrategy<>(CodeParsingOrderElement.COMPILATION_UNIT, compilationUnitCodeParser::parse),
        CodeParsingOrderElement.CLASS, 
                       new CodeParsingStrategy<>(CodeParsingOrderElement.CLASS, classCodeParser::parse),
        CodeParsingOrderElement.METHOD, 
                       new CodeParsingStrategy<>(CodeParsingOrderElement.METHOD, methodCodeParser::parse),
        CodeParsingOrderElement.BLOCK, 
                       new CodeParsingStrategy<>(CodeParsingOrderElement.BLOCK, blockCodeParser::parse),
        CodeParsingOrderElement.STATEMENT, 
                       new CodeParsingStrategy<>(CodeParsingOrderElement.STATEMENT, statementCodeParser::parse));
    }

    private <T extends Node> T parse(final String code, final CodeParsingOrderIndicator indicator) throws ExceptionWithErrorCodeAndMessageCode {
        try {
            return Optional.ofNullable((CodeParsingStrategy<T>) strategies.get(indicator.getCurrentLevel()))
                        .map(strategy -> strategy.getParseFunction().apply(code))
                        .orElseThrow(() -> new CodeParsingStrategyNotFoundException(indicator.getCurrentLevel()));
        } catch (final ParseProblemException e) {
            //TODO log
            return parse(code, indicator.nextLevel());
        }
    }

    public Node parse(final String code) throws ExceptionWithErrorCodeAndMessageCode{
        return parse(code, new CodeParsingOrderIndicator());
    }

    @AllArgsConstructor
    private class CodeParsingStrategy<T extends Node> {
        private final CodeParsingOrderElement codeType;
        @Getter
        private final Function<String, T> parseFunction;
    }

    @Getter
    private class CodeParsingOrderIndicator {
        private CodeParsingOrderElement currentLevel;
        private final List<CodeParsingOrderElement> order;

        public CodeParsingOrderIndicator() {
            this.currentLevel = CodeParsingOrderElement.COMPILATION_UNIT;
            this.order = List.of(CodeParsingOrderElement.COMPILATION_UNIT, 
                                 CodeParsingOrderElement.CLASS, 
                                 CodeParsingOrderElement.METHOD, 
                                 CodeParsingOrderElement.BLOCK, 
                                 CodeParsingOrderElement.STATEMENT);
        }

        private boolean reachedEnd() {
           return Objects.nonNull(currentLevel) && order.indexOf(currentLevel) == order.size() - 1;
        }

        public CodeParsingOrderIndicator nextLevel() throws CodeParsingStrategyNotFoundException{
            if(reachedEnd()) {
                throw new CodeParsingStrategyNotFoundException();
            }
            this.currentLevel = order.get(order.indexOf(currentLevel) + 1);
            return this;
        }

    }

}
