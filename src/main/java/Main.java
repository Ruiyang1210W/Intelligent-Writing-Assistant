import view.MainFrame;

import javax.swing.*;

/**
 * Main entry point for the Intelligent Writing Assistant application
 */
public class Main {

    public static void main(String[] args) {
        // Set system look and feel for better UI appearance
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            System.err.println("Failed to set system look and feel: " + e.getMessage());
        }

        // Launch the application on the Event Dispatch Thread (EDT)
        // This is required for Swing applications to ensure thread safety
        SwingUtilities.invokeLater(() -> {
            MainFrame frame = new MainFrame();
            frame.setVisible(true);
        });
    }
}
