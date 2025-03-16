package com.sluja.anonym4ai.ui.theme;

import java.awt.Color;
import java.awt.Font;

import javax.swing.AbstractButton;
import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.UIManager;
import javax.swing.border.Border;
import javax.swing.plaf.ColorUIResource;
import javax.swing.text.JTextComponent;

public class DarkTheme {
    
    public static final Color BACKGROUND = new Color(43, 43, 43);
    public static final Color FOREGROUND = new Color(220, 220, 220);
    public static final Color SELECTION_BG = new Color(75, 110, 175);
    public static final Color SELECTION_FG = Color.WHITE;
    public static final Color TEXTAREA_BG = new Color(60, 63, 65);
    public static final Color BUTTON_BG = new Color(80, 80, 80);
    public static final Color BORDER_COLOR = new Color(100, 100, 100);
    
    // Font settings
    public static final Font CODE_FONT = new Font(Font.MONOSPACED, Font.PLAIN, 12);
    
    public static void apply() {
        try {
            // Set basic UI defaults
            UIManager.put("Panel.background", BACKGROUND);
            UIManager.put("Panel.foreground", FOREGROUND);
            UIManager.put("TextField.background", TEXTAREA_BG);
            UIManager.put("TextField.foreground", FOREGROUND);
            UIManager.put("TextField.caretForeground", FOREGROUND);
            UIManager.put("TextArea.background", TEXTAREA_BG);
            UIManager.put("TextArea.foreground", FOREGROUND); 
            UIManager.put("TextArea.caretForeground", FOREGROUND);
            
            // Button settings
            UIManager.put("Button.background", BUTTON_BG);
            UIManager.put("Button.foreground", FOREGROUND);
            UIManager.put("Button.select", SELECTION_BG);
            
            // Selection colors
            UIManager.put("TextArea.selectionBackground", SELECTION_BG);
            UIManager.put("TextArea.selectionForeground", SELECTION_FG);
            
            // TitledBorder settings
            UIManager.put("TitledBorder.titleColor", FOREGROUND);
            
            // Scrollbar and scroll pane
            UIManager.put("ScrollPane.background", BACKGROUND);
            UIManager.put("ScrollBar.thumb", new ColorUIResource(100, 100, 100));
            UIManager.put("ScrollBar.track", new ColorUIResource(60, 60, 60));
            
            // Option pane for dialogs
            UIManager.put("OptionPane.background", BACKGROUND);
            UIManager.put("OptionPane.messageForeground", FOREGROUND);
            
        } catch (final NullPointerException e) {
            System.out.println("Error applying dark theme: " + e.getMessage());
        }
    }
    
    public static Border createTitledBorder(String title) {
        return BorderFactory.createTitledBorder(
            BorderFactory.createLineBorder(BORDER_COLOR),
            title,
            javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION,
            javax.swing.border.TitledBorder.DEFAULT_POSITION,
            null,
            FOREGROUND
        );
    }
    
    
    public static void applyToCodeComponent(JTextComponent textComponent) {
        textComponent.setFont(CODE_FONT);
        textComponent.setBackground(TEXTAREA_BG);
        textComponent.setForeground(FOREGROUND);
        textComponent.setCaretColor(FOREGROUND);
        textComponent.setSelectionColor(SELECTION_BG);
        textComponent.setSelectedTextColor(SELECTION_FG);
    }
    
    
    public static void applyToScrollPane(JScrollPane scrollPane) {
        scrollPane.setBackground(BACKGROUND);
        scrollPane.getViewport().setBackground(TEXTAREA_BG);
    }
    
    public static void applyToActionButton(AbstractButton button) {
        button.setBackground(BUTTON_BG);
        button.setForeground(FOREGROUND);
        button.setFocusPainted(false);
        button.setBorderPainted(true);
    }
    
    // Apply theme to panels
    public static void applyToPanel(JPanel panel) {
        panel.setBackground(BACKGROUND);
        panel.setForeground(FOREGROUND);
    }
} 