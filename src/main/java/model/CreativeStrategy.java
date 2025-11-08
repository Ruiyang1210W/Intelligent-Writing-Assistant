package model;

/**
 * Creative Writing Strategy
 * Focuses on imaginative, expressive, and engaging content
 */
public class CreativeStrategy implements AIStrategy {

    @Override
    public String getSystemPrompt() {
        return "You are a creative writing assistant. Generate imaginative, " +
               "engaging, and expressive content. Use vivid descriptions, " +
               "metaphors, and storytelling techniques. Make the writing " +
               "captivating and human-sounding with natural flow.";
    }

    @Override
    public String getStrategyName() {
        return "Creative";
    }

    @Override
    public String enhancePrompt(String userInput) {
        return "Write in a creative, engaging, and imaginative style:\n\n" + userInput;
    }
}
