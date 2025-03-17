package com.sluja.anonym4ai.ui.button;

import javax.swing.JOptionPane;

import com.sluja.anonym4ai.ui.panel.CodeSnippetPanel;

public class AnonymizationActionButton extends ActionButton {
    private final CodeSnippetPanel inputPanel;
    private final CodeSnippetPanel outputPanel;

    public AnonymizationActionButton(final CodeSnippetPanel inputPanel, final CodeSnippetPanel outputPanel) {
        super("Anonymize");
        this.inputPanel = inputPanel;
        this.outputPanel = outputPanel;
    }

    @Override
    public void performAction() {
        try {
            final String inputText = inputPanel.getText();
            if (inputText.isEmpty()) {
                JOptionPane.showMessageDialog(null, "Input text is empty.", "Warning", JOptionPane.WARNING_MESSAGE);
                return;
            }
            final String anonymizedText = anonymizeText(inputText);
            outputPanel.setText(anonymizedText);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Error during anonymization: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private String anonymizeText(String text) {
        // Temporary implementation - will be replaced with actual anonymization logic
        return text.replaceAll("\\b\\w+\\b", "var");
    }
}
