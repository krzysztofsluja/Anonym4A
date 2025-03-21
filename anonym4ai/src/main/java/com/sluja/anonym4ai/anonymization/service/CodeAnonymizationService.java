package com.sluja.anonym4ai.anonymization.service;

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
import com.sluja.anonym4ai.anonymization.interfaces.ICodeAnonymizer;
    
public class CodeAnonymizationService implements ICodeAnonymizer{
        
    private final AbstractCodeAnonymizer<ClassOrInterfaceDeclaration, ClassOrInterfaceType> classAnonymizer;
    private final AbstractUnifiedCodeAnonymizer<MethodDeclaration> methodAnonymizer;
    private final AbstractUnifiedCodeAnonymizer<BlockStmt> blockAnonymizer;
    private final AbstractUnifiedCodeAnonymizer<Statement> statementAnonymizer;
    private final AbstractUnifiedCodeAnonymizer<CompilationUnit> compilationUnitCodeAnonymizer;
    
        
    public CodeAnonymizationService() {
        this.classAnonymizer = new ClassCodeAnonymizer();
        this.methodAnonymizer = new MethodCodeAnonymizer();
        this.blockAnonymizer = new BlockCodeAnonymizer();
        this.statementAnonymizer = new StatementCodeAnonymizer();
        this.compilationUnitCodeAnonymizer = new CompilationUnitCodeAnonymizer();
        
    }

    @Override
    public String anonymize(final Node node) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'anonymize'");
    }        
}
