import org.junit.jupiter.api.Test;
import model.*;

import static org.junit.jupiter.api.Assertions.*;

/**
 * JUnit tests for Strategy Pattern implementation
 */
public class StrategyTest {

    @Test
    public void testCreativeStrategyName() {
        AIStrategy strategy = new CreativeStrategy();
        assertEquals("Creative", strategy.getStrategyName());
    }

    @Test
    public void testProfessionalStrategyName() {
        AIStrategy strategy = new ProfessionalStrategy();
        assertEquals("Professional", strategy.getStrategyName());
    }

    @Test
    public void testAcademicStrategyName() {
        AIStrategy strategy = new AcademicStrategy();
        assertEquals("Academic", strategy.getStrategyName());
    }

    @Test
    public void testCreativeStrategyPromptEnhancement() {
        AIStrategy strategy = new CreativeStrategy();
        String userInput = "Write a story";
        String enhanced = strategy.enhancePrompt(userInput);

        assertNotNull(enhanced);
        assertTrue(enhanced.contains(userInput));
        assertTrue(enhanced.toLowerCase().contains("creative"));
    }

    @Test
    public void testProfessionalStrategyPromptEnhancement() {
        AIStrategy strategy = new ProfessionalStrategy();
        String userInput = "Write an email";
        String enhanced = strategy.enhancePrompt(userInput);

        assertNotNull(enhanced);
        assertTrue(enhanced.contains(userInput));
        assertTrue(enhanced.toLowerCase().contains("professional"));
    }

    @Test
    public void testStrategyFactoryCreatesCorrectType() {
        AIStrategy creative = StrategyFactory.createStrategy("Creative");
        assertTrue(creative instanceof CreativeStrategy);

        AIStrategy professional = StrategyFactory.createStrategy("Professional");
        assertTrue(professional instanceof ProfessionalStrategy);

        AIStrategy academic = StrategyFactory.createStrategy("Academic");
        assertTrue(academic instanceof AcademicStrategy);
    }

    @Test
    public void testStrategyFactoryWithInvalidName() {
        AIStrategy strategy = StrategyFactory.createStrategy("Invalid");
        assertTrue(strategy instanceof ProfessionalStrategy); // Default
    }

    @Test
    public void testStrategyFactoryWithNull() {
        AIStrategy strategy = StrategyFactory.createStrategy((String) null);
        assertTrue(strategy instanceof ProfessionalStrategy); // Default
    }

    @Test
    public void testStrategySystemPromptNotEmpty() {
        AIStrategy creative = new CreativeStrategy();
        assertNotNull(creative.getSystemPrompt());
        assertFalse(creative.getSystemPrompt().isEmpty());

        AIStrategy professional = new ProfessionalStrategy();
        assertNotNull(professional.getSystemPrompt());
        assertFalse(professional.getSystemPrompt().isEmpty());
    }
}

