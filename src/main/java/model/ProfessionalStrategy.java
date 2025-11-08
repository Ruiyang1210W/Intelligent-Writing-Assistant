package model;

/**
 * Professional Writing Strategy
 * Focuses on formal, clear, and business-appropriate content
 */
public class ProfessionalStrategy implements AIStrategy {

    @Override
    public String getSystemPrompt() {
        return "You are a professional writing assistant. Generate clear, " +
               "formal, and business-appropriate content. Use professional " +
               "language, maintain a respectful tone, and ensure the writing " +
               "is polished and suitable for workplace communication like " +
               "cover letters and professional emails.";
    }

    @Override
    public String getStrategyName() {
        return "Professional";
    }

    @Override
    public String enhancePrompt(String userInput) {
        return "Write in a professional, formal, and business-appropriate style:\n\n" + userInput;
    }
}
