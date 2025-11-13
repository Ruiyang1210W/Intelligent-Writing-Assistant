import edu.cmu.sphinx.api.LiveSpeechRecognizer;
import edu.cmu.sphinx.result.WordResult;
import view.MainFrame;

import javax.swing.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import edu.cmu.sphinx.api.Configuration;
import edu.cmu.sphinx.api.SpeechResult;
import edu.cmu.sphinx.api.StreamSpeechRecognizer;

/**
 * Main entry point for the Intelligent Writing Assistant application
 */
public class Main {

    public static void main(String[] args) throws IOException {



        // Set system look and feel for better UI appearance

        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            System.err.println("Failed to set system look and feel: " + e.getMessage());
        }

        // Launch the application on the Event Dispatch Thread (EDT)
        // This is required for Swing applications to ensure thread safety
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
