package service;

import javax.swing.*;
import java.io.PrintWriter;
import java.io.StringWriter;

/**
 * Centralized error handling utility
 * Handles API errors, network errors, and displays user-friendly messages
 */
public class ErrorHandler {

    /**
     * Handle any exception and show appropriate error dialog
     * @param e The exception to handle
     */
    public static void handleException(Exception e) {
        String errorMessage = parseErrorMessage(e);
        String errorTitle = "Error";

        // Categorize the error
        if (e instanceof IllegalStateException) {
            errorTitle = "Configuration Error";
            errorMessage = "API Key not found. Please create a .env file with your OpenAI API key.\n\n" +
                          "Example:\nOPENAI_API_KEY=sk-your-key-here";
        } else if (e.getMessage() != null && e.getMessage().contains("API Error")) {
            errorTitle = "API Error";
            errorMessage = parseApiError(e.getMessage());
        } else if (e instanceof java.net.UnknownHostException ||
                   e instanceof java.net.ConnectException) {
            errorTitle = "Network Error";
            errorMessage = "Unable to connect to OpenAI API.\n" +
                          "Please check your internet connection.";
        }

        showError(errorTitle, errorMessage);

        // Log the full stack trace to console for debugging
        e.printStackTrace();
    }

    /**
     * Show error dialog with custom title and message
     * @param title Dialog title
     * @param message Error message
     */
    public static void showError(String title, String message) {
        SwingUtilities.invokeLater(() -> {
            JOptionPane.showMessageDialog(
                null,
                message,
                title,
                JOptionPane.ERROR_MESSAGE
            );
        });
    }

    /**
     * Parse exception message to user-friendly format
     */
    private static String parseErrorMessage(Exception e) {
        if (e.getMessage() != null) {
            return e.getMessage();
        }
        return "An unexpected error occurred: " + e.getClass().getSimpleName();
    }

    /**
     * Parse API error response for user-friendly message
     */
    private static String parseApiError(String errorMessage) {
        if (errorMessage.contains("401")) {
            return "Invalid API Key. Please check your .env file.";
        } else if (errorMessage.contains("429")) {
            return "Rate limit exceeded. Please wait a moment and try again.";
        } else if (errorMessage.contains("500") || errorMessage.contains("503")) {
            return "OpenAI service is temporarily unavailable. Please try again later.";
        } else if (errorMessage.contains("400")) {
            return "Invalid request. Please check your input and try again.";
        }
        return "API Error: " + errorMessage;
    }

    /**
     * Get full stack trace as string for debugging
     */
    public static String getStackTraceAsString(Exception e) {
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        e.printStackTrace(pw);
        return sw.toString();
    }
}

