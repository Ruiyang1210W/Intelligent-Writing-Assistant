import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import model.ContentModel;
import model.ProfessionalStrategy;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import static org.junit.jupiter.api.Assertions.*;

/**
 * JUnit tests for ContentModel (Observer Pattern)
 */
public class ContentModelTest {

    private ContentModel model;

    @BeforeEach
    public void setUp() {
        model = new ContentModel();
    }

    @Test
    public void testInitialState() {
        assertEquals("", model.getInputText());
        assertEquals("", model.getOutputText());
        assertFalse(model.isGenerating());
        assertNotNull(model.getCurrentStrategy());
    }

    @Test
    public void testSetInputText() {
        model.setInputText("Test input");
        assertEquals("Test input", model.getInputText());
    }

    @Test
    public void testSetOutputText() {
        model.setOutputText("Test output");
        assertEquals("Test output", model.getOutputText());
    }

    @Test
    public void testSetGenerating() {
        model.setGenerating(true);
        assertTrue(model.isGenerating());

        model.setGenerating(false);
        assertFalse(model.isGenerating());
    }

    @Test
    public void testClear() {
        model.setInputText("Input");
        model.setOutputText("Output");

        model.clear();

        assertEquals("", model.getInputText());
        assertEquals("", model.getOutputText());
    }

    @Test
    public void testPropertyChangeListener() {
        final boolean[] listenerCalled = {false};

        model.addPropertyChangeListener(new PropertyChangeListener() {
            @Override
            public void propertyChange(PropertyChangeEvent evt) {
                if ("inputText".equals(evt.getPropertyName())) {
                    listenerCalled[0] = true;
                }
            }
        });

        model.setInputText("New text");
        assertTrue(listenerCalled[0]);
    }

    @Test
    public void testSetStrategy() {
        ProfessionalStrategy strategy = new ProfessionalStrategy();
        model.setCurrentStrategy(strategy);
        assertEquals(strategy, model.getCurrentStrategy());
    }
}
