package model;

/**
 * Factory Pattern Implementation
 * Creates strategy instances based on strategy type
 */
public class StrategyFactory {

    /**
     * Strategy types enum for type-safe creation
     */
    public enum StrategyType {
        PROFESSIONAL,
        CREATIVE,
        ACADEMIC
    }

    /**
     * Create a strategy instance based on type
     * @param type The strategy type to create
     * @return An instance of the requested strategy
     */
    public static AIStrategy createStrategy(StrategyType type) {
        switch (type) {
            case PROFESSIONAL:
                return new ProfessionalStrategy();
            case CREATIVE:
                return new CreativeStrategy();
            case ACADEMIC:
                return new AcademicStrategy();
            default:
                return new ProfessionalStrategy();
        }
    }

    /**
     * Create a strategy instance based on string name
     * @param strategyName The strategy name (case-insensitive)
     * @return An instance of the requested strategy, or Professional if not found
     */
    public static AIStrategy createStrategy(String strategyName) {
        if (strategyName == null) {
            return new ProfessionalStrategy();
        }

        switch (strategyName.toUpperCase()) {
            case "CREATIVE":
                return new CreativeStrategy();
            case "ACADEMIC":
                return new AcademicStrategy();
            default:
                return new ProfessionalStrategy();
        }
    }

    /**
     * Get all available strategy names
     * @return Array of strategy names
     */
    public static String[] getAllStrategyNames() {
        return new String[]{"Professional", "Creative", "Academic"};
    }
}
