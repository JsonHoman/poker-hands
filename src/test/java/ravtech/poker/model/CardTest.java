package ravtech.poker.model;

import com.ravtech.poker.model.Card;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class CardTest {

    @Test
    void testConvertValueToInt() {
        // Test digits
        for (char c = '2'; c <= '9'; c++) {
            int result = Card.convertValueToInt(c);
            int expected = c - '0';
            assertEquals(expected, result);
        }

        // Test special characters
        assertEquals(10, Card.convertValueToInt('T'));
        assertEquals(11, Card.convertValueToInt('J'));
        assertEquals(12, Card.convertValueToInt('Q'));
        assertEquals(13, Card.convertValueToInt('K'));
        assertEquals(14, Card.convertValueToInt('A'));

        // Test invalid character
        try {
            Card.convertValueToInt('X');
            fail("Expected IllegalArgumentException for invalid character");
        } catch (IllegalArgumentException e) {
            // Expected exception
        }
    }

    @Test
    void testConvertValueToStringValid() {
        assertEquals("2", Card.convertValueToString(2));
        assertEquals("9", Card.convertValueToString(9));
        assertEquals("10", Card.convertValueToString(10));
        assertEquals("Jack", Card.convertValueToString(11));
        assertEquals("Queen", Card.convertValueToString(12));
        assertEquals("King", Card.convertValueToString(13));
        assertEquals("Ace", Card.convertValueToString(14));
    }

    @Test
    void testConvertValueToStringInvalid() {
        assertThrows(IllegalArgumentException.class, () -> Card.convertValueToString(1));
        assertThrows(IllegalArgumentException.class, () -> Card.convertValueToString(15));
    }
}
