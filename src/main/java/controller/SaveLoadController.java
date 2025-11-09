package controller;

import model.ContentModel;
import model.SessionModel;
import model.StrategyFactory;
import org.json.JSONObject;
import service.ErrorHandler;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDateTime;

/**
 * Controller for save/load operations - MVC Controller component
 * Implements file persistence with JSON format
 */
public class SaveLoadController {

    private ContentModel model;
    private static final String DEFAULT_EXTENSION = ".json";
    File defaultDirectory = new File("saves");

    public SaveLoadController(ContentModel model) {
        this.model = model;
    }

    /**
     * Save current session to a JSON file
     */
    public void saveSession() {
        // Create file chooser
        JFileChooser fileChooser = new JFileChooser(defaultDirectory);
        fileChooser.setDialogTitle("Save Session");
        fileChooser.setFileFilter(new FileNameExtensionFilter("JSON Files", "json"));
        fileChooser.setSelectedFile(new File("session_" + System.currentTimeMillis() + DEFAULT_EXTENSION));

        int userSelection = fileChooser.showSaveDialog(null);

        if (userSelection == JFileChooser.APPROVE_OPTION) {
            File fileToSave = fileChooser.getSelectedFile();

            // Add extension if not present
            if (!fileToSave.getName().endsWith(DEFAULT_EXTENSION)) {
                fileToSave = new File(fileToSave.getAbsolutePath() + DEFAULT_EXTENSION);
            }

            try {
                // Create session model
                SessionModel session = new SessionModel(
                        model.getInputText(),
                        model.getOutputText(),
                        model.getCurrentStrategy().getStrategyName()
                );

                // Convert to JSON
                JSONObject json = sessionToJson(session);

                // Write to file
                Files.writeString(Paths.get(fileToSave.getAbsolutePath()), json.toString(2));

                JOptionPane.showMessageDialog(
                        null,
                        "Session saved successfully!",
                        "Success",
                        JOptionPane.INFORMATION_MESSAGE
                );

            } catch (Exception e) {
                ErrorHandler.handleException(e);
            }
        }
    }

    /**
     * Load session from a JSON file
     */
    public void loadSession(JFrame parent) {
        // Create file chooser
        JFileChooser fileChooser = new JFileChooser(defaultDirectory);
        fileChooser.setDialogTitle("Load Session");
        fileChooser.setFileFilter(new FileNameExtensionFilter("JSON Files", "json"));

        int userSelection = fileChooser.showOpenDialog(parent);

        if (userSelection == JFileChooser.APPROVE_OPTION) {
            File fileToLoad = fileChooser.getSelectedFile();

            try {
                // Read file
                String content = Files.readString(Paths.get(fileToLoad.getAbsolutePath()));

                // Parse JSON
                JSONObject json = new JSONObject(content);
                SessionModel session = jsonToSession(json);

                // Update model
                model.setInputText(session.getInputText());
                model.setOutputText(session.getOutputText());
                model.setCurrentStrategy(StrategyFactory.createStrategy(session.getStrategyName()));

                JOptionPane.showMessageDialog(
                        parent,
                        "Session loaded successfully!",
                        "Success",
                        JOptionPane.INFORMATION_MESSAGE
                );

            } catch (Exception e) {
                ErrorHandler.showError("Load Error", "Failed to load session: " + e.getMessage());
            }
        }
    }
    public void loadSession (JFrame parent, File file){
        try {
            // Read file
            String content = Files.readString(Paths.get(file.getAbsolutePath()));

            // Parse JSON
            JSONObject json = new JSONObject(content);
            SessionModel session = jsonToSession(json);

            // Update model
            model.setInputText(session.getInputText());
            model.setOutputText(session.getOutputText());
            model.setCurrentStrategy(StrategyFactory.createStrategy(session.getStrategyName()));

            JOptionPane.showMessageDialog(
                    parent,
                    "Session loaded successfully!",
                    "Success",
                    JOptionPane.INFORMATION_MESSAGE
            );

        } catch (Exception e) {
            ErrorHandler.showError("Load Error", "Failed to load session: " + e.getMessage());
        }
    }

    /**
     * Convert SessionModel to JSON
     */
    private JSONObject sessionToJson(SessionModel session) {
        JSONObject json = new JSONObject();
        json.put("inputText", session.getInputText());
        json.put("outputText", session.getOutputText());
        json.put("strategyName", session.getStrategyName());
        json.put("timestamp", session.getTimestamp().toString());
        json.put("version", "1.0");
        return json;
    }

    /**
     * Convert JSON to SessionModel
     */
    public SessionModel jsonToSession(JSONObject json) {
        SessionModel session = new SessionModel();
        session.setInputText(json.getString("inputText"));
        session.setOutputText(json.getString("outputText"));
        session.setStrategyName(json.getString("strategyName"));

        if (json.has("timestamp")) {
            session.setTimestamp(LocalDateTime.parse(json.getString("timestamp")));
        }

        return session;
    }
}

