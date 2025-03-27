package com.sluja.anonym4ai.anonymization.service;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;

import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.Node;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.stmt.BlockStmt;
import com.github.javaparser.ast.stmt.Statement;
import com.github.javaparser.ast.type.ClassOrInterfaceType;
import com.sluja.anonym4ai.anonymization.AbstractCodeAnonymizer;
import com.sluja.anonym4ai.anonymization.AbstractUnifiedCodeAnonymizer;
import com.sluja.anonym4ai.anonymization.exception.CodeAnonymizationStrategyNotFoundException;
import com.sluja.anonym4ai.anonymization.implementation.BlockCodeAnonymizer;
import com.sluja.anonym4ai.anonymization.implementation.ClassCodeAnonymizer;
import com.sluja.anonym4ai.anonymization.implementation.CompilationUnitCodeAnonymizer;
import com.sluja.anonym4ai.anonymization.implementation.MethodCodeAnonymizer;
import com.sluja.anonym4ai.anonymization.implementation.StatementCodeAnonymizer;
import com.sluja.anonym4ai.anonymization.interfaces.ICodeAnonymizer;
import com.sluja.anonym4ai.exception.ExceptionWithErrorCodeAndMessageCode;
import com.sluja.anonym4ai.parsing.service.CodeParsingService;
    
public class CodeAnonymizationService implements ICodeAnonymizer {
        
    private final AbstractCodeAnonymizer<ClassOrInterfaceDeclaration, ClassOrInterfaceType> classAnonymizer;
    private final AbstractUnifiedCodeAnonymizer<MethodDeclaration> methodAnonymizer;
    private final AbstractUnifiedCodeAnonymizer<BlockStmt> blockAnonymizer;
    private final AbstractUnifiedCodeAnonymizer<Statement> statementAnonymizer;
    private final AbstractUnifiedCodeAnonymizer<CompilationUnit> compilationUnitCodeAnonymizer;
    private final CodeParsingService codeParsingService;
    private final Map<Class <? extends Node>, Function<Node, String>> strategies; 
    
        
    public CodeAnonymizationService() {
        this.classAnonymizer = new ClassCodeAnonymizer();
        this.methodAnonymizer = new MethodCodeAnonymizer();
        this.blockAnonymizer = new BlockCodeAnonymizer();
        this.statementAnonymizer = new StatementCodeAnonymizer();
        this.compilationUnitCodeAnonymizer = new CompilationUnitCodeAnonymizer();
        this.codeParsingService = new CodeParsingService();
        this.strategies = initializeStrategies();
    }

    private Map<Class <? extends Node>, Function<Node, String>> initializeStrategies() {
        final Map<Class <? extends Node>, Function<Node, String>> result = new HashMap<>();
        result.put(CompilationUnit.class, 
            node -> compilationUnitCodeAnonymizer.anonymize((CompilationUnit) node));
        result.put(ClassOrInterfaceDeclaration.class, 
            node -> classAnonymizer.anonymize((ClassOrInterfaceDeclaration) node));
        result.put(MethodDeclaration.class, 
            node -> methodAnonymizer.anonymize((MethodDeclaration) node));
        result.put(BlockStmt.class, 
            node -> blockAnonymizer.anonymize((BlockStmt) node));
        result.put(Statement.class, 
            node -> statementAnonymizer.anonymize((Statement) node));
        return result;
    }

    private Function<Node, String> getStrategy(final Node key) throws ExceptionWithErrorCodeAndMessageCode{
        return Optional.ofNullable(strategies.get(key.getClass()))
            .orElseThrow(() -> new CodeAnonymizationStrategyNotFoundException());
    }

    @Override
    public String anonymize(final String code) throws Exception {
        final Node parsedResult = codeParsingService.parse(code);
        return getStrategy(parsedResult).apply(parsedResult);
    }
    

}
