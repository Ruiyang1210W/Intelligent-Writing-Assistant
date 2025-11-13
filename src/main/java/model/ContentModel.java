package model;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

/**
 * Model class for content data
 * Uses Observer Pattern (PropertyChangeSupport) for notifying views of changes
 */
public class ContentModel {

    // Observer Pattern implementation
    private final PropertyChangeSupport support;

    // Model data
    private String inputText;
    private String outputText;
    private AIStrategy currentStrategy;
    private boolean isGenerating;

    public ContentModel() {
        this.support = new PropertyChangeSupport(this);
        this.inputText = "";
        this.outputText = "";
        this.currentStrategy = new ProfessionalStrategy(); // Default strategy
        this.isGenerating = false;
    }

    // Observer Pattern methods
    public void addPropertyChangeListener(PropertyChangeListener listener) {
        support.addPropertyChangeListener(listener);
    }

    public void removePropertyChangeListener(PropertyChangeListener listener) {
        support.removePropertyChangeListener(listener);
    }

    // Getters and setters with property change notifications

    public String getInputText() {
        return inputText;
    }

    public void setInputText(String inputText) {
        String oldValue = this.inputText;
        this.inputText = inputText;
        support.firePropertyChange("inputText", oldValue, inputText);
    }

    public void appendInputText(String inputText){
        String oldValue = this.inputText;
        support.firePropertyChange("inputText", oldValue, oldValue + " " + inputText);
    }

    public String getOutputText() {
        return outputText;
    }

    public void setOutputText(String outputText) {
        String oldValue = this.outputText;
        this.outputText = outputText;
        support.firePropertyChange("outputText", oldValue, outputText);
    }

    public AIStrategy getCurrentStrategy() {
        return currentStrategy;
    }

    public void setCurrentStrategy(AIStrategy strategy) {
        AIStrategy oldValue = this.currentStrategy;
        this.currentStrategy = strategy;
        support.firePropertyChange("currentStrategy", oldValue, strategy);
    }

    public boolean isGenerating() {
        return isGenerating;
    }

    public void setGenerating(boolean generating) {
        boolean oldValue = this.isGenerating;
        this.isGenerating = generating;
        support.firePropertyChange("isGenerating", oldValue, generating);
    }

    /**
     * Clear all content
     */
    public void clear() {
        setInputText("");
        setOutputText("");
    }
}

