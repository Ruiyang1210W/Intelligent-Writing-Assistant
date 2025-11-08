import org.junit.jupiter.api.Test;
import util.Validator;

import static org.junit.jupiter.api.Assertions.*;

/**
 * JUnit tests for Validator utility class
 */
public class ValidatorTest {

    @Test
    public void testValidInput() {
        assertTrue(Validator.isValidInput("This is a valid input text."));
    }

    @Test
    public void testEmptyInput() {
        assertFalse(Validator.isValidInput(""));
        assertFalse(Validator.isValidInput("   "));
    }

    @Test
    public void testNullInput() {
        assertFalse(Validator.isValidInput(null));
    }

    @Test
    public void testTooShortInput() {
        assertFalse(Validator.isValidInput("Hi"));
    }

    @Test
    public void testMinimumLengthInput() {
        assertTrue(Validator.isValidInput("Hello"));
    }

    @Test
    public void testValidApiKey() {
        assertTrue(Validator.isValidApiKey("sk-1234567890abcdefghijklmnop"));
    }

    @Test
    public void testInvalidApiKey() {
        assertFalse(Validator.isValidApiKey("invalid-key"));
        assertFalse(Validator.isValidApiKey("sk-short"));
        assertFalse(Validator.isValidApiKey(null));
    }

    @Test
    public void testValidFileName() {
        assertTrue(Validator.isValidFileName("session_123.json"));
        assertTrue(Validator.isValidFileName("myfile.txt"));
    }

    @Test
    public void testInvalidFileName() {
        assertFalse(Validator.isValidFileName("file<name>.txt"));
        assertFalse(Validator.isValidFileName("file:name.txt"));
        assertFalse(Validator.isValidFileName(""));
        assertFalse(Validator.isValidFileName(null));
    }
}

