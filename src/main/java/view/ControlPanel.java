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

        setLayout(new BorderLayout(12, 12));
        setBackground(UITheme.PANEL_BG);
        setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createMatteBorder(1, 0, 0, 0, UITheme.BORDER_COLOR),
            BorderFactory.createEmptyBorder(
                UITheme.PADDING_LARGE, 0, UITheme.PADDING_SMALL, 0
            )
        ));

        initializeComponents();
    }

    private void initializeComponents() {
        // Left panel - Strategy selection
        JPanel leftPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, UITheme.PADDING_MEDIUM, 0));
        leftPanel.setBackground(UITheme.PANEL_BG);

        JLabel strategyLabel = new JLabel("Writing Mode:");
        strategyLabel.setFont(UITheme.NORMAL_FONT);
        strategyLabel.setForeground(UITheme.TEXT_COLOR);

        strategyComboBox = new JComboBox<>(new String[]{
            "Professional",
            "Creative",
            "Academic"
        });
        strategyComboBox.setFont(UITheme.NORMAL_FONT);
        strategyComboBox.setBackground(Color.WHITE);
        strategyComboBox.setPreferredSize(new Dimension(140, 32));
        strategyComboBox.addActionListener(e -> updateStrategy());

        leftPanel.add(strategyLabel);
        leftPanel.add(strategyComboBox);

        // Center panel - Buttons
        JPanel centerPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, UITheme.PADDING_MEDIUM, 0));
        centerPanel.setBackground(UITheme.PANEL_BG);

        generateButton = new JButton("Generate");
        generateButton.setPreferredSize(new Dimension(140, 40));
        generateButton.addActionListener(e -> controller.generateText());
        UITheme.styleButton(generateButton, true);  // Primary button

        clearButton = new JButton("Clear");
        clearButton.setPreferredSize(new Dimension(100, 40));
        clearButton.addActionListener(e -> clearContent());
        UITheme.styleButton(clearButton, false);

        centerPanel.add(generateButton);
        centerPanel.add(clearButton);

        // Right panel - Progress bar
        JPanel rightPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, UITheme.PADDING_MEDIUM, 0));
        rightPanel.setBackground(UITheme.PANEL_BG);

        progressBar = new JProgressBar();
        progressBar.setIndeterminate(true);
        progressBar.setVisible(false);
        progressBar.setPreferredSize(new Dimension(180, 30));
        progressBar.setForeground(UITheme.PRIMARY);
        progressBar.setBorder(BorderFactory.createLineBorder(UITheme.BORDER_COLOR, 1));

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

