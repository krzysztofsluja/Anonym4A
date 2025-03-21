package com.sluja.anonym4ai.anonymization.service;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Function;

import com.github.javaparser.ParseProblemException;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.Node;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.stmt.BlockStmt;
import com.github.javaparser.ast.stmt.Statement;
import com.github.javaparser.ast.type.ClassOrInterfaceType;
import com.sluja.anonym4ai.anonymization.AbstractCodeAnonymizer;
import com.sluja.anonym4ai.anonymization.AbstractUnifiedCodeAnonymizer;
import com.sluja.anonym4ai.anonymization.implementation.BlockCodeAnonymizer;
import com.sluja.anonym4ai.anonymization.implementation.ClassCodeAnonymizer;
import com.sluja.anonym4ai.anonymization.implementation.CompilationUnitCodeAnonymizer;
import com.sluja.anonym4ai.anonymization.implementation.MethodCodeAnonymizer;
import com.sluja.anonym4ai.anonymization.implementation.StatementCodeAnonymizer;
import com.sluja.anonym4ai.enums.CodeParsingOrderElement;

import lombok.AllArgsConstructor;
import lombok.Getter;
    
public class CodeAnonymizationService {
        
    private final AbstractCodeAnonymizer<ClassOrInterfaceDeclaration, ClassOrInterfaceType> classAnonymizer;
    private final AbstractUnifiedCodeAnonymizer<MethodDeclaration> methodAnonymizer;
    private final AbstractUnifiedCodeAnonymizer<BlockStmt> blockAnonymizer;
    private final AbstractUnifiedCodeAnonymizer<Statement> statementAnonymizer;
    private final AbstractUnifiedCodeAnonymizer<CompilationUnit> compilationUnitCodeAnonymizer;
    private final Map<CodeParsingOrderElement, CodeParsingStrategy<? extends Node>> strategies;
        
    public CodeAnonymizationService() {
        this.classAnonymizer = new ClassCodeAnonymizer();
        this.methodAnonymizer = new MethodCodeAnonymizer();
        this.blockAnonymizer = new BlockCodeAnonymizer();
        this.statementAnonymizer = new StatementCodeAnonymizer();
        this.compilationUnitCodeAnonymizer = new CompilationUnitCodeAnonymizer();
        this.strategies = initializeStrategies();
    }

    private Map<CodeParsingOrderElement, CodeParsingStrategy<? extends Node>> initializeStrategies() {
        return Map.of(
        CodeParsingOrderElement.COMPILATION_UNIT, 
                       new CodeParsingStrategy<>(CodeParsingOrderElement.COMPILATION_UNIT, compilationUnitCodeAnonymizer::parse),
        CodeParsingOrderElement.CLASS, 
                       new CodeParsingStrategy<>(CodeParsingOrderElement.CLASS, classAnonymizer::parse),
        CodeParsingOrderElement.METHOD, 
                       new CodeParsingStrategy<>(CodeParsingOrderElement.METHOD, methodAnonymizer::parse),
        CodeParsingOrderElement.BLOCK, 
                       new CodeParsingStrategy<>(CodeParsingOrderElement.BLOCK, blockAnonymizer::parse),
        CodeParsingOrderElement.STATEMENT, 
                       new CodeParsingStrategy<>(CodeParsingOrderElement.STATEMENT, statementAnonymizer::parse));
    }

    private <T extends Node> T parse(final String code, final CodeParsingOrderIndicator indicator) throws IllegalArgumentException, ParseProblemException {
        //TODO throw exception if codeType is not found
        try {
            return Optional.ofNullable((CodeParsingStrategy<T>) strategies.get(indicator.getCurrentLevel()))
                        .map(strategy -> strategy.getParseFunction().apply(code))
                        .orElseThrow(() -> new IllegalArgumentException("No strategy found for code type: " + indicator.getCurrentLevel()));
        } catch (ParseProblemException e) {
            //TODO log
            return parse(code, indicator.nextLevel());
        } catch (final IllegalArgumentException e) {
            //TODO log
        }
        return null;
    }

    public <T extends Node> T parse(final String code) {
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

        public CodeParsingOrderIndicator nextLevel() {
            if(reachedEnd()) {
                //TODO: throw exception
            }
            this.currentLevel = order.get(order.indexOf(currentLevel) + 1);
            return this;
        }

    }
    

    /* private Map<AnonymizedCodeElement, Function<? extends TypeDeclaration, String>> initializeStrategies() {
        final Map<AnonymizedCodeElement, Function<? extends TypeDeclaration, String>> strategies = new HashMap<>();
        strategies.put(AnonymizedCodeElement.CLASS, classAnonymizer::anonymize);
        /* strategies.put(AnonymizedCodeElement.CLASS, new CodeParserStrategy<ClassOrInterfaceDeclaration>(AnonymizedCodeElement.CLASS, StaticJavaParser.parseClassOrInterfaceType, classAnonymizer::anonymize));
        strategies.put(AnonymizedCodeElement.METHOD, new CodeParserStrategy<>(AnonymizedCodeElement.METHOD, StaticJavaParser.parseMethodDeclaration(type), methodAnonymizer::anonymize));
        strategies.put(AnonymizedCodeElement.METHOD, new CodeParserStrategy<>(AnonymizedCodeElement.CLASS, StaticJavaParser.parseVariableDeclarationExpr(declaration), variableAnonymizer::anonymize));
        strategies.put(AnonymizedCodeElement.CLASS, new CodeParserStrategy<>(AnonymizedCodeElement.CLASS, StaticJavaParser.parseClassOrInterfaceType(type), classAnonymizer::anonymize));
        strategies.put(AnonymizedCodeElement.CLASS, new CodeParserStrategy<>(AnonymizedCodeElement.CLASS, StaticJavaParser.parseClassOrInterfaceType(type), classAnonymizer::anonymize));
        return strategies;
    } */
        
    /* private static class CodeParserStrategy<T extends Node> {
        private final AnonymizedCodeElement codeType;
        private final Function<String, T> parseFunction;
        private final Function<T, String> anonymizeFunction;
        
        public CodeParserStrategy(final AnonymizedCodeElement codeType, 
                                  final Function<String, T> parseFunction;
                                  final Function<T, String> anonymizeFunction) {
            this.codeType = codeType;
            this.parseFunction = parseFunction;
            this.anonymizeFunction = anonymizeFunction;
        }
        
        public Optional<String> tryParse(final String code) {
            try {
                T parsedNode = parseFunction.apply(code);
                final String anonymizedCode = anonymizeFunction.apply(parsedNode);
                return Optional.of(anonymizedCode);
            } catch (Exception e) {
                return Optional.empty();
            }
        }
    } */
        
}
