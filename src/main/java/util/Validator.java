package util;

/**
 * Utility class for input validation
 */
public class Validator {

    private static final int MIN_INPUT_LENGTH = 5;
    private static final int MAX_INPUT_LENGTH = 5000;

    /**
     * Validate user input
     * @param input User input text
     * @return true if valid, false otherwise
     */
    public static boolean isValidInput(String input) {
        if (input == null || input.trim().isEmpty()) {
            return false;
        }

        int length = input.trim().length();
        return length >= MIN_INPUT_LENGTH && length <= MAX_INPUT_LENGTH;
    }

    /**
     * Validate API key format
     * @param apiKey API key string
     * @return true if valid format, false otherwise
     */
    public static boolean isValidApiKey(String apiKey) {
        if (apiKey == null || apiKey.trim().isEmpty()) {
            return false;
        }
        return apiKey.startsWith("sk-") && apiKey.length() > 20;
    }

    /**
     * Validate file name for saving
     * @param fileName File name
     * @return true if valid, false otherwise
     */
    public static boolean isValidFileName(String fileName) {
        if (fileName == null || fileName.trim().isEmpty()) {
            return false;
        }
        // Check for invalid characters
        return !fileName.matches(".*[<>:\"/\\\\|?*].*");
    }
}

