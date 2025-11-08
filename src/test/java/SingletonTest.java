import org.junit.jupiter.api.Test;
import model.OpenAIClient;

import static org.junit.jupiter.api.Assertions.*;

/**
 * JUnit tests for Singleton Pattern implementation
 */
public class SingletonTest {

    @Test
    public void testSingletonInstanceNotNull() {
        OpenAIClient instance = OpenAIClient.getInstance();
        assertNotNull(instance);
    }

    @Test
    public void testSingletonReturnsSameInstance() {
        OpenAIClient instance1 = OpenAIClient.getInstance();
        OpenAIClient instance2 = OpenAIClient.getInstance();

        assertSame(instance1, instance2);
    }

    @Test
    public void testMultipleCallsReturnSameInstance() {
        OpenAIClient instance1 = OpenAIClient.getInstance();
        OpenAIClient instance2 = OpenAIClient.getInstance();
        OpenAIClient instance3 = OpenAIClient.getInstance();

        assertSame(instance1, instance2);
        assertSame(instance2, instance3);
    }
}
