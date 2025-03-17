package com.sluja.anonym4ai.ui.panel;

import java.awt.BorderLayout;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import com.sluja.anonym4ai.ui.theme.DarkTheme;

public class CodeSnippetPanel extends JPanel {
    private final String title;
    private JTextArea textArea;

    public CodeSnippetPanel(final String title) {
        this.title = title;
        initPanel();
        initCodeSnippetArea();
    }

    private void initPanel() {
        this.setLayout(new BorderLayout());
        DarkTheme.applyToPanel(this);
        this.setBorder(DarkTheme.createTitledBorder(title));
    }

    private void initCodeSnippetArea() {
        textArea = new JTextArea();
        textArea.setLineWrap(true);
        textArea.setWrapStyleWord(true);
        DarkTheme.applyToCodeComponent(textArea);
        final JScrollPane scrollPane = new JScrollPane(textArea);
        DarkTheme.applyToScrollPane(scrollPane);
        this.add(scrollPane, BorderLayout.CENTER);
    }
    
    public String getText() {
        return textArea.getText();
    }
    
    public void setText(final String text) {
        textArea.setText(text);
    }
    
    public void clear() {
        textArea.setText("");
    }
    
    public JTextArea getTextArea() {
        return textArea;
    }
} 