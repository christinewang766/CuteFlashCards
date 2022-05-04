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
        test1 = new Card("a", "b");
        test2 = new Card("c", "d");
        test3 = new Card("e", "f");
    }

    @Test
    public void testConstructor() {
        assertEquals("a", test1.getQuestion());
        assertEquals("b", test1.getAnswer());
        assertFalse(test1.getCorrect());
        assertTrue(test1.getFirstGuess());

        assertEquals("c", test2.getQuestion());
        assertEquals("d", test2.getAnswer());
        assertFalse(test2.getCorrect());
        assertTrue(test1.getFirstGuess());

        assertEquals("e", test3.getQuestion());
        assertEquals("f", test3.getAnswer());
        assertFalse(test3.getCorrect());
        assertTrue(test1.getFirstGuess());
    }

    @Test
    public void testSetters() {
        test1.setCorrect(true);
        assertTrue(test1.getCorrect());
        test1.setCorrect(false);
        assertFalse(test1.getCorrect());
        assertTrue(test1.getFirstGuess());
        test1.setFirstGuessFalse();
        assertFalse(test1.getFirstGuess());

        assertEquals("a", test1.getQuestion());
        test1.setQuestion("b");
        assertEquals("b", test1.getQuestion());

        assertEquals("b", test1.getAnswer());
        test1.setAnswer("a");
        assertEquals("a", test1.getAnswer());
    }
}
