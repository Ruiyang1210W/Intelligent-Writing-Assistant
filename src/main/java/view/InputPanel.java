package view;

import model.ContentModel;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

/**
 * Panel for user input
 * Implements Observer Pattern to listen to model changes
 */
public class InputPanel extends JPanel implements PropertyChangeListener {

    private ContentModel model;
    private JTextArea inputArea;
    private JLabel charCountLabel;

    public InputPanel(ContentModel model) {
        this.model = model;
        this.model.addPropertyChangeListener(this);

        setLayout(new BorderLayout(5, 5));
        setBorder(BorderFactory.createTitledBorder("Input"));

        initializeComponents();
    }

    private void initializeComponents() {
        // Text area for input
        inputArea = new JTextArea();
        inputArea.setLineWrap(true);
        inputArea.setWrapStyleWord(true);
        inputArea.setFont(new Font("Arial", Font.PLAIN, 14));

        // Listen to text changes and update model
        inputArea.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                updateModel();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                updateModel();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                updateModel();
            }

            private void updateModel() {
                model.setInputText(inputArea.getText());
                updateCharCount();
            }
        });

        JScrollPane scrollPane = new JScrollPane(inputArea);
        add(scrollPane, BorderLayout.CENTER);

        // Character count label
        charCountLabel = new JLabel("Characters: 0");
        charCountLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        add(charCountLabel, BorderLayout.SOUTH);
    }

    /**
     * Update character count label
     */
    private void updateCharCount() {
        int count = inputArea.getText().length();
        charCountLabel.setText("Characters: " + count);
    }

    /**
     * Observer Pattern - respond to model changes
     */
    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        if ("inputText".equals(evt.getPropertyName())) {
            String newText = (String) evt.getNewValue();
            if (!inputArea.getText().equals(newText)) {
                inputArea.setText(newText);
                updateCharCount();
            }
        }
    }
}

