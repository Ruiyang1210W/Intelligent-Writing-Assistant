package view;

import controller.AIController;
import model.AIStrategy;
import model.AcademicStrategy;
import model.ContentModel;
import model.CreativeStrategy;
import model.ProfessionalStrategy;

import javax.swing.*;
import java.awt.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

/**
 * Control panel with strategy selection and action buttons
 * Implements Observer Pattern to listen to model changes
 */
public class ControlPanel extends JPanel implements PropertyChangeListener {

    private ContentModel model;
    private AIController controller;

    private JComboBox<String> strategyComboBox;
    private JButton generateButton;
    private JButton clearButton;
    private JProgressBar progressBar;

    public ControlPanel(ContentModel model, AIController controller) {
        this.model = model;
        this.controller = controller;
        this.model.addPropertyChangeListener(this);

        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createEmptyBorder(10, 0, 0, 0));

        initializeComponents();
    }

    private void initializeComponents() {
        // Left panel - Strategy selection
        JPanel leftPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JLabel strategyLabel = new JLabel("Writing Mode:");
        strategyComboBox = new JComboBox<>(new String[]{
            "Professional",
            "Creative",
            "Academic"
        });
        strategyComboBox.addActionListener(e -> updateStrategy());

        leftPanel.add(strategyLabel);
        leftPanel.add(strategyComboBox);

        // Center panel - Buttons
        JPanel centerPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        generateButton = new JButton("Generate");
        generateButton.setFont(new Font("Arial", Font.BOLD, 14));
        generateButton.setPreferredSize(new Dimension(120, 35));
        generateButton.addActionListener(e -> controller.generateText());

        clearButton = new JButton("Clear");
        clearButton.setPreferredSize(new Dimension(100, 35));
        clearButton.addActionListener(e -> clearContent());

        centerPanel.add(generateButton);
        centerPanel.add(clearButton);

        // Right panel - Progress bar
        JPanel rightPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        progressBar = new JProgressBar();
        progressBar.setIndeterminate(true);
        progressBar.setVisible(false);
        progressBar.setPreferredSize(new Dimension(150, 25));

        rightPanel.add(progressBar);

        // Add all panels
        add(leftPanel, BorderLayout.WEST);
        add(centerPanel, BorderLayout.CENTER);
        add(rightPanel, BorderLayout.EAST);
    }

    /**
     * Update strategy based on combo box selection
     */
    private void updateStrategy() {
        String selected = (String) strategyComboBox.getSelectedItem();
        AIStrategy strategy;

        switch (selected) {
            case "Creative":
                strategy = new CreativeStrategy();
                break;
            case "Academic":
                strategy = new AcademicStrategy();
                break;
            default:
                strategy = new ProfessionalStrategy();
                break;
        }

        model.setCurrentStrategy(strategy);
    }

    /**
     * Clear all content
     */
    private void clearContent() {
        int result = JOptionPane.showConfirmDialog(
            this,
            "Clear all content?",
            "Confirm",
            JOptionPane.YES_NO_OPTION
        );

        if (result == JOptionPane.YES_OPTION) {
            model.clear();
        }
    }

    /**
     * Observer Pattern - respond to model changes
     */
    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        if ("isGenerating".equals(evt.getPropertyName())) {
            boolean isGenerating = (boolean) evt.getNewValue();
            generateButton.setEnabled(!isGenerating);
            strategyComboBox.setEnabled(!isGenerating);
            clearButton.setEnabled(!isGenerating);
            progressBar.setVisible(isGenerating);
        }
    }
}

