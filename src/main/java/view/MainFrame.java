package view;

import controller.AIController;
import controller.SaveLoadController;
import controller.SpeechConverter;
import model.ContentModel;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Comparator;

/**
 * Main application window - MVC View component
 * Coordinates all UI panels and controllers
 */
public class MainFrame extends JFrame {

    private ContentModel model;
    private AIController aiController;
    private SaveLoadController saveLoadController;
    private SpeechConverter speechConverter;

    private InputPanel inputPanel;
    private OutputPanel outputPanel;
    private ControlPanel controlPanel;

    public MainFrame() throws IOException {
        // Initialize model and controllers
        this.model = new ContentModel();
        this.aiController = new AIController(model);
        this.saveLoadController = new SaveLoadController(model);
        this.speechConverter = new SpeechConverter(model);

        // Setup frame
        setTitle("Intelligent Writing Assistant");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1200, 750);
        setLocationRelativeTo(null); // Center on screen
        setLayout(new BorderLayout(10, 10));

        // Set background color
        getContentPane().setBackground(UITheme.BACKGROUND);

        // Create menu bar
        createMenuBar();

        // Initialize panels
        initializePanels();

        // Add panels to frame
        layoutPanels();
    }

    /**
     * Create and setup menu bar
     */
    private void createMenuBar() {
        JMenuBar menuBar = new JMenuBar();

        // File menu
        JMenu fileMenu = new JMenu("File");
        JMenuItem saveItem = new JMenuItem("Save Session");
        JMenuItem loadItem = new JMenuItem("Load Session");
        JMenuItem exitItem = new JMenuItem("Exit");

        saveItem.addActionListener(e -> saveLoadController.saveSession());
        loadItem.addActionListener(e -> saveLoadController.loadSession(this));
        exitItem.addActionListener(e -> System.exit(0));

        //add most recent sessions up to 3
        JMenu recentSessions = new JMenu("Recent Sessions");
        File folder = new File("saves");
        File[] files = folder.listFiles();
        Arrays.sort(files, Comparator.comparingLong(File::lastModified));
        for(int i = 0; i < files.length && i <= 3; i++){
            JMenuItem recent = new JMenuItem(files[i].getName());
            int finalI = i;
            recent.addActionListener(e -> saveLoadController.loadSession(this, files[finalI]));
            recentSessions.add(recent);
        }
        recentSessions.addSeparator();
        recentSessions.add(loadItem);

        fileMenu.add(saveItem);
        fileMenu.add(recentSessions);
        fileMenu.addSeparator();
        fileMenu.add(exitItem);

        // Help menu
        JMenu helpMenu = new JMenu("Help");
        JMenuItem aboutItem = new JMenuItem("About");
        aboutItem.addActionListener(e -> showAboutDialog());
        helpMenu.add(aboutItem);

        menuBar.add(fileMenu);
        menuBar.add(helpMenu);

        setJMenuBar(menuBar);
    }

    /**
     * Initialize all panels
     */
    private void initializePanels() {
        inputPanel = new InputPanel(model, speechConverter);
        outputPanel = new OutputPanel(model);
        controlPanel = new ControlPanel(model, aiController);
    }

    /**
     * Layout panels in the frame
     */
    private void layoutPanels() {
        // Create split pane for input and output
        JSplitPane splitPane = new JSplitPane(
            JSplitPane.HORIZONTAL_SPLIT,
            inputPanel,
            outputPanel
        );
        splitPane.setDividerLocation(550);
        splitPane.setResizeWeight(0.5);
        splitPane.setDividerSize(8);
        splitPane.setBorder(null);

        // Add components
        add(splitPane, BorderLayout.CENTER);
        add(controlPanel, BorderLayout.SOUTH);

        // Add padding
        ((JComponent) getContentPane()).setBorder(
            BorderFactory.createEmptyBorder(
                UITheme.PADDING_LARGE,
                UITheme.PADDING_LARGE,
                UITheme.PADDING_LARGE,
                UITheme.PADDING_LARGE
            )
        );
    }

    /**
     * Show about dialog
     */
    private void showAboutDialog() {
        String message = "Intelligent Writing Assistant\n\n" +
                        "An AI-powered writing tool with multiple modes:\n" +
                        "- Creative\n" +
                        "- Professional\n" +
                        "- Academic\n\n" +
                        "Powered by OpenAI GPT-3.5\n" +
                        "Version 1.0";

        JOptionPane.showMessageDialog(
            this,
            message,
            "About",
            JOptionPane.INFORMATION_MESSAGE
        );
    }

    /**
     * Launch the application
     */
    public static void main(String[] args) {
        // Use system look and feel
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Launch on Event Dispatch Thread
        SwingUtilities.invokeLater(() -> {
            MainFrame frame = null;
            try {
                frame = new MainFrame();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            frame.setVisible(true);
        });
    }
}

