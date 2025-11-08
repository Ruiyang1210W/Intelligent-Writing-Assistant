package model;

/**
 * Academic Writing Strategy
 * Focuses on scholarly, research-oriented, and formal academic content
 */
public class AcademicStrategy implements AIStrategy {

    @Override
    public String getSystemPrompt() {
        return "You are an academic writing assistant. Generate scholarly, " +
               "well-researched, and formal academic content. Use precise " +
               "terminology, maintain objectivity, cite concepts appropriately, " +
               "and structure content suitable for essays, research papers, and " +
               "statements of purpose.";
    }

    @Override
    public String getStrategyName() {
        return "Academic";
    }

    @Override
    public String enhancePrompt(String userInput) {
        return "Write in an academic, scholarly, and formal research style:\n\n" + userInput;
    }
}
