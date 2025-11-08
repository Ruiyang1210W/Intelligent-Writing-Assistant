package controller;

import model.ContentModel;
import model.OpenAIClient;
import service.ErrorHandler;
import util.Validator;

import javax.swing.*;

/**
 * Controller for AI text generation - MVC Controller component
 * Uses SwingWorker for async operations to prevent UI freezing
 */
public class AIController {

    private ContentModel model;
    private OpenAIClient client;

    public AIController(ContentModel model) {
        this.model = model;
        this.client = OpenAIClient.getInstance();
    }

    /**
     * Generate text asynchronously using SwingWorker
     * Prevents UI from freezing during API call
     */
    public void generateText() {
        // Validate input
        String input = model.getInputText();
        if (!Validator.isValidInput(input)) {
            ErrorHandler.showError(
                "Invalid Input",
                "Please enter some text before generating."
            );
            return;
        }

        // Set generating state
        model.setGenerating(true);
        model.setOutputText(""); // Clear previous output

        // Create SwingWorker for async operation
        SwingWorker<String, Void> worker = new SwingWorker<String, Void>() {
            @Override
            protected String doInBackground() throws Exception {
                // This runs on a background thread
                return client.generateText(model.getCurrentStrategy(), input);
            }

            @Override
            protected void done() {
                // This runs on the Event Dispatch Thread
                try {
                    String result = get();
                    model.setOutputText(result);
                } catch (Exception e) {
                    ErrorHandler.handleException(e);
                } finally {
                    model.setGenerating(false);
                }
            }
        };

        // Execute the worker
        worker.execute();
    }
}

