package model;

import service.OpenAIService;
import io.github.cdimascio.dotenv.Dotenv;

/**
 * Singleton Pattern Implementation
 * Ensures only one instance of OpenAI client exists throughout the application
 * Thread-safe implementation using Bill Pugh Singleton Design
 */
public class OpenAIClient {

    // Private constructor prevents instantiation
    private OpenAIClient() {}

    /**
     * Bill Pugh Singleton Design - Thread-safe without synchronization
     * The inner static class is loaded only when getInstance() is called
     */
    private static class SingletonHelper {
        private static final OpenAIClient INSTANCE = new OpenAIClient();
    }

    /**
     * Global access point to get the singleton instance
     * @return The single instance of OpenAIClient
     */
    public static OpenAIClient getInstance() {
        return SingletonHelper.INSTANCE;
    }

    /**
     * Get or create the OpenAI service with API key from environment
     * @return OpenAIService instance configured with API key
     * @throws IllegalStateException if API key is not found
     */
    public OpenAIService getService() {
        Dotenv dotenv = Dotenv.configure().ignoreIfMissing().load();
        String apiKey = dotenv.get("OPENAI_API_KEY");

        if (apiKey == null || apiKey.trim().isEmpty()) {
            throw new IllegalStateException(
                "OPENAI_API_KEY not found in .env file. " +
                "Please create a .env file with your API key."
            );
        }

        return new OpenAIService(apiKey);
    }

    /**
     * Generate text using the current strategy
     * @param strategy The writing strategy to use
     * @param userInput The user's input text
     * @return Generated text from OpenAI
     * @throws Exception if API call fails
     */
    public String generateText(AIStrategy strategy, String userInput) throws Exception {
        OpenAIService service = getService();
        String enhancedPrompt = strategy.enhancePrompt(userInput);
        return service.generateText(enhancedPrompt);
    }
}
