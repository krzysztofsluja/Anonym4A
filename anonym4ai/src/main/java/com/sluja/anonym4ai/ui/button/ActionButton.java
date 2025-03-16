package com.sluja.anonym4ai.ui.button;

import java.awt.Dimension;

import javax.swing.JButton;

import com.sluja.anonym4ai.ui.theme.DarkTheme;

public abstract class ActionButton extends JButton {
    private static final Dimension BUTTON_SIZE = new Dimension(120, 40); 
    
    public ActionButton(final String text) {
        super(text);
        configureButtonAppearance();
    }
    
    private void configureButtonAppearance() {
        setPreferredSize(BUTTON_SIZE);
        setMinimumSize(BUTTON_SIZE);
        setMaximumSize(BUTTON_SIZE);
        setAlignmentX(CENTER_ALIGNMENT);
        DarkTheme.applyToActionButton(this);
    }

    public abstract void performAction();
}
