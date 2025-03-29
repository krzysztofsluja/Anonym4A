package com.sluja.anonym4ai.anonymization.implementation;

import java.util.Objects;

import org.apache.commons.lang3.StringUtils;

import com.github.javaparser.ast.Node;
import com.github.javaparser.ast.PackageDeclaration;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.expr.MethodCallExpr;
import com.github.javaparser.ast.expr.Name;
import com.github.javaparser.ast.expr.NameExpr;
import com.github.javaparser.ast.expr.SimpleName;
import com.github.javaparser.ast.expr.VariableDeclarationExpr;
import com.github.javaparser.ast.stmt.ReturnStmt;
import com.github.javaparser.ast.stmt.Statement;
import com.github.javaparser.ast.type.ClassOrInterfaceType;
import com.sluja.anonym4ai.anonymization.AbstractCodeAnonymizer;
import com.sluja.anonym4ai.anonymization.AbstractUnifiedCodeAnonymizer;
import com.sluja.anonym4ai.anonymization.interfaces.ICounterReset;
import com.sluja.anonym4ai.anonymization.utils.AnonymizingVisitor;

public class StatementCodeAnonymizer extends AbstractUnifiedCodeAnonymizer<Statement> implements ICounterReset {

    public StatementCodeAnonymizer(final AnonymizingVisitor visitor) {
        super(visitor);
    }

    private int statementCounter = 1;

    @Override
    public String anonymize(final Statement code) {
        resetCounters();
        // visitor.visit(code, null);
        return StringUtils.EMPTY;
    }

    private void anonymizeStatement(final SimpleName code, final Void arg) {
        code.getAllContainedComments().forEach(Node::remove);
        if (isVariableReference(code)) {
            final String originalName = code.getIdentifier();
            final String anonymizedName = getSetting("statement.variable.name") + statementCounter++;
            code.setIdentifier(anonymizedName);
        }
    }

    private boolean isVariableReference(SimpleName name) {
        Node parent = name.getParentNode().orElse(null);
        if (Objects.isNull(parent))
            return false;
        return !(parent instanceof MethodDeclaration ||
                parent instanceof ClassOrInterfaceType ||
                (parent instanceof MethodCallExpr && ((MethodCallExpr) parent).getName() == name) ||
                parent instanceof Name
                        && parent.getParentNode().map(p -> p instanceof PackageDeclaration).orElse(false));
    }

    @Override
    protected void registerVisitorHandlers(final AnonymizingVisitor visitor) {
        visitor.registerHandler(SimpleName.class, this::anonymizeStatement);
    }

    @Override
    public void resetCounters() {
        this.statementCounter = 1;
    }

}
