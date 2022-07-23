package tests;

import model.Card;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class CardTest {

    private Card test1;
    private Card test2;
    private Card test3;

    @BeforeEach
    public void runBefore() {
        test1 = new Card("a", "b", false, false, Card.NUM_ATTEMPTS, true);
        test2 = new Card("c", "d", false, false, Card.NUM_ATTEMPTS, true);
        test3 = new Card("e", "f", false, false, Card.NUM_ATTEMPTS, true);
    }

    @Test
    public void testConstructor() {
        assertEquals("a", test1.getQuestion());
        assertEquals("b", test1.getAnswer());
        assertFalse(test1.getComplete());
        assertTrue(test1.getFirstGuess());
        assertEquals(3, test1.getAttempts());
        assertFalse(test1.getStarred());

        assertEquals("c", test2.getQuestion());
        assertEquals("d", test2.getAnswer());
        assertTrue(test1.getFirstGuess());

        assertEquals("e", test3.getQuestion());
        assertEquals("f", test3.getAnswer());
        assertTrue(test1.getFirstGuess());
    }

    @Test
    public void testSetters() {
        assertTrue(test1.getFirstGuess());
        test1.setFirstGuess(false);
        assertFalse(test1.getFirstGuess());
        test1.setStarred(true);
        assertTrue(test1.getStarred());

        assertEquals("a", test1.getQuestion());
        test1.setQuestion("b");
        assertEquals("b", test1.getQuestion());

        assertEquals("b", test1.getAnswer());
        test1.setAnswer("a");
        assertEquals("a", test1.getAnswer());
    }
}
