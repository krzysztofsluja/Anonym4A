package com.sluja.anonym4ai.parsing.interfaces;

import com.github.javaparser.ParseProblemException;
import com.github.javaparser.ast.Node;

public interface ICodeParser<T extends Node> {

    T parse(final String code) throws ParseProblemException;
}
