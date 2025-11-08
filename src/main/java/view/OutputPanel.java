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

        setLayout(new BorderLayout(5, 5));
        setBorder(BorderFactory.createTitledBorder("Generated Output"));

        initializeComponents();
    }

    private void initializeComponents() {
        // Text area for output (read-only)
        outputArea = new JTextArea();
        outputArea.setLineWrap(true);
        outputArea.setWrapStyleWord(true);
        outputArea.setFont(new Font("Arial", Font.PLAIN, 14));
        outputArea.setEditable(false);
        outputArea.setBackground(new Color(245, 245, 245));

        JScrollPane scrollPane = new JScrollPane(outputArea);
        add(scrollPane, BorderLayout.CENTER);

        // Bottom panel with copy button and word count
        JPanel bottomPanel = new JPanel(new BorderLayout());

        copyButton = new JButton("Copy to Clipboard");
        copyButton.addActionListener(e -> copyToClipboard());
        copyButton.setEnabled(false);

        wordCountLabel = new JLabel("Words: 0");
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

