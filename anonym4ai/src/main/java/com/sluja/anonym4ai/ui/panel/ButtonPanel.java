package com.sluja.anonym4ai.ui.panel;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JPanel;

import com.sluja.anonym4ai.ui.button.ActionButton;

public class ButtonPanel extends JPanel {

    public ButtonPanel(final ActionButton anonymizationButton, final ActionButton clearButton) {
        initPanelParameters();
        addButtonsWithSpacing(anonymizationButton, clearButton);
        addListeners(anonymizationButton, clearButton);
    }

    private void initPanelParameters() {
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        this.setBorder(BorderFactory.createEmptyBorder(10, 5, 10, 5));
    }

    private void addButtonsWithSpacing(final ActionButton anonymizationButton, final ActionButton clearButton) {
        this.add(anonymizationButton);
        this.add(Box.createVerticalStrut(20));
        this.add(clearButton);
    }

    private void addListeners(final ActionButton anonymizationButton, final ActionButton clearButton) {
        anonymizationButton.addActionListener(e -> anonymizationButton.performAction());
        clearButton.addActionListener(e -> clearButton.performAction());
    }
}