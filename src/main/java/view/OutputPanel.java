package view;

import model.ContentModel;

import javax.swing.*;
import java.awt.*;
import java.awt.datatransfer.StringSelection;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

/**
 * Panel for displaying AI-generated output
 * Implements Observer Pattern to listen to model changes
 */
public class OutputPanel extends JPanel implements PropertyChangeListener {

    private ContentModel model;
    private JTextArea outputArea;
    private JButton copyButton;
    private JLabel wordCountLabel;

    public OutputPanel(ContentModel model) {
        this.model = model;
        this.model.addPropertyChangeListener(this);

        setLayout(new BorderLayout(8, 8));
        setBorder(UITheme.createTitledBorder("Generated Output"));
        setBackground(UITheme.PANEL_BG);

        initializeComponents();
    }

    private void initializeComponents() {
        // Text area for output (read-only)
        outputArea = new JTextArea();
        outputArea.setLineWrap(true);
        outputArea.setWrapStyleWord(true);
        outputArea.setFont(UITheme.TEXT_AREA_FONT);
        outputArea.setEditable(false);
        outputArea.setBackground(UITheme.TEXT_AREA_BG);
        outputArea.setForeground(UITheme.TEXT_COLOR);
        outputArea.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(UITheme.BORDER_COLOR, 1),
            BorderFactory.createEmptyBorder(UITheme.PADDING_MEDIUM, UITheme.PADDING_MEDIUM,
                                           UITheme.PADDING_MEDIUM, UITheme.PADDING_MEDIUM)
        ));

        JScrollPane scrollPane = new JScrollPane(outputArea);
        scrollPane.setBorder(null);
        scrollPane.getViewport().setBackground(UITheme.TEXT_AREA_BG);
        add(scrollPane, BorderLayout.CENTER);

        // Bottom panel with copy button and word count
        JPanel bottomPanel = new JPanel(new BorderLayout());
        bottomPanel.setBackground(UITheme.PANEL_BG);
        bottomPanel.setBorder(BorderFactory.createEmptyBorder(
            UITheme.PADDING_SMALL, 0, 0, 0
        ));

        copyButton = new JButton("Copy to Clipboard");
        copyButton.addActionListener(e -> copyToClipboard());
        copyButton.setEnabled(false);
        UITheme.styleButton(copyButton, false);

        wordCountLabel = new JLabel("Words: 0");
        wordCountLabel.setFont(UITheme.LABEL_FONT);
        wordCountLabel.setForeground(UITheme.TEXT_COLOR);
        wordCountLabel.setHorizontalAlignment(SwingConstants.RIGHT);

        bottomPanel.add(copyButton, BorderLayout.WEST);
        bottomPanel.add(wordCountLabel, BorderLayout.EAST);

        add(bottomPanel, BorderLayout.SOUTH);
    }

    /**
     * Copy output text to clipboard
     */
    private void copyToClipboard() {
        String text = outputArea.getText();
        if (!text.isEmpty()) {
            Toolkit.getDefaultToolkit()
                   .getSystemClipboard()
                   .setContents(new StringSelection(text), null);
            JOptionPane.showMessageDialog(
                this,
                "Text copied to clipboard!",
                "Success",
                JOptionPane.INFORMATION_MESSAGE
            );
        }
    }

    /**
     * Update word count label
     */
    private void updateWordCount() {
        String text = outputArea.getText().trim();
        int wordCount = text.isEmpty() ? 0 : text.split("\\s+").length;
        wordCountLabel.setText("Words: " + wordCount);
    }

    /**
     * Observer Pattern - respond to model changes
     */
    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        if ("outputText".equals(evt.getPropertyName())) {
            String newText = (String) evt.getNewValue();
            outputArea.setText(newText);
            updateWordCount();
            copyButton.setEnabled(!newText.isEmpty());
        }
    }
}

