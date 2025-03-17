package com.sluja.anonym4ai.ui;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

import com.sluja.anonym4ai.ui.button.ActionButton;
import com.sluja.anonym4ai.ui.button.AnonymizationActionButton;
import com.sluja.anonym4ai.ui.button.ClearActionButton;
import com.sluja.anonym4ai.ui.panel.ButtonPanel;
import com.sluja.anonym4ai.ui.panel.CodeSnippetPanel;
import com.sluja.anonym4ai.ui.theme.DarkTheme;

public class MainScreen extends JFrame {

    private final CodeSnippetPanel inputPanel;
    private final CodeSnippetPanel anonymizedPanel;
    private final ButtonPanel buttonsPanel;
    private final ActionButton anonymizeButton;
    private final ActionButton clearButton;

    public MainScreen() {
        DarkTheme.apply();
        
        initMainScreenParameters();
        
        inputPanel = new CodeSnippetPanel("Original Code");
        anonymizedPanel = new CodeSnippetPanel("Anonymized Code");
        anonymizeButton = new AnonymizationActionButton(inputPanel, anonymizedPanel);
        clearButton = new ClearActionButton(inputPanel, anonymizedPanel);    
        buttonsPanel = new ButtonPanel(anonymizeButton, clearButton); 

        configureLayout();
        this.setVisible(true);
    }

    private void initMainScreenParameters() {
        this.setTitle("Anonym4AI");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(960, 540);
        this.setLocationRelativeTo(null);
        this.getContentPane().setBackground(DarkTheme.BACKGROUND);
    }

    private void configureLayout() {
        this.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        configureInputPanel(gbc);
        configureButtonsPanel(gbc);
        configureAnonymizedPanel(gbc);
    }

    private void configureInputPanel(final GridBagConstraints gbc) {
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 0.45;
        gbc.weighty = 1.0;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.insets = new Insets(10, 10, 10, 5);
        this.add(inputPanel, gbc);
    }

    private void configureButtonsPanel(final GridBagConstraints gbc) {
        gbc.gridx = 1;
        gbc.weightx = 0.1;
        gbc.insets = new Insets(10, 5, 10, 5);
        gbc.fill = GridBagConstraints.NONE;
        gbc.anchor = GridBagConstraints.CENTER;
        this.add(buttonsPanel, gbc);
    }

    private void configureAnonymizedPanel(final GridBagConstraints gbc) {
        gbc.gridx = 2;
        gbc.weightx = 0.45;
        gbc.insets = new Insets(10, 5, 10, 10);
        gbc.fill = GridBagConstraints.BOTH;
        this.add(anonymizedPanel, gbc);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(MainScreen::new);
    }
} 