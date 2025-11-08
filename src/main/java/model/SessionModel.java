package model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Model class for session data
 * Represents a saved writing session with metadata
 */
public class SessionModel {

    private String inputText;
    private String outputText;
    private String strategyName;
    private LocalDateTime timestamp;

    public SessionModel() {
        this.timestamp = LocalDateTime.now();
    }

    public SessionModel(String inputText, String outputText, String strategyName) {
        this.inputText = inputText;
        this.outputText = outputText;
        this.strategyName = strategyName;
        this.timestamp = LocalDateTime.now();
    }

    // Getters and setters
    public String getInputText() {
        return inputText;
    }

    public void setInputText(String inputText) {
        this.inputText = inputText;
    }

    public String getOutputText() {
        return outputText;
    }

    public void setOutputText(String outputText) {
        this.outputText = outputText;
    }

    public String getStrategyName() {
        return strategyName;
    }

    public void setStrategyName(String strategyName) {
        this.strategyName = strategyName;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    /**
     * Get formatted timestamp string
     * @return Formatted timestamp
     */
    public String getFormattedTimestamp() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return timestamp.format(formatter);
    }

    @Override
    public String toString() {
        return "Session [" + getFormattedTimestamp() + "] - " + strategyName;
    }
}

