package model;
/**
 * Strategy Pattern Interface
 * Defines the contract for different writing strategies
 */
public interface AIStrategy {
    /**
     * Generate a system prompt based on the strategy
     * @return System prompt that defines the AI's behavior
     */
    String getSystemPrompt();

    /**
     * Get the display name of this strategy
     * @return Human-readable strategy name
     */
    String getStrategyName();

    /**
     * Enhance the user's input prompt with strategy-specific instructions
     * @param userInput Original user input
     * @return Enhanced prompt with strategy-specific context
     */
    String enhancePrompt(String userInput);
}
