package com.sluja.anonym4ai.ui.button;

import com.sluja.anonym4ai.ui.panel.CodeSnippetPanel;

public class ClearActionButton extends ActionButton {
    private final CodeSnippetPanel inputPanel;
    private final CodeSnippetPanel outputPanel;
    
    public ClearActionButton(final CodeSnippetPanel inputPanel, final CodeSnippetPanel outputPanel) {
        super("Clear");
        this.inputPanel = inputPanel;
        this.outputPanel = outputPanel;
    }
    
    @Override
    public void performAction() {
        inputPanel.clear();
        outputPanel.clear();
    }
}
