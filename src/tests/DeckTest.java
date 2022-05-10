package tests;

import model.Card;
import model.Deck;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class DeckTest {

    private Card test1;
    private Card test2;
    private Card test3;
    private Deck cards;


    @BeforeEach
    public void runBefore() {
        cards = new Deck("Tester Title");
        test1 = new Card("a", "b", false, false, Card.NUM_ATTEMPTS, true);
        test2 = new Card("c", "d", false, false, Card.NUM_ATTEMPTS, true);
        test3 = new Card("e", "f", false, false, Card.NUM_ATTEMPTS, true);
        cards.getFlashCards().add(test1);
        cards.getFlashCards().add(test2);
        cards.getFlashCards().add(test3);
    }

    @Test
    public void testConstructor() {
        assertNull(cards.getCurrentCard());
        assertEquals(3, cards.getFlashCards().size());
        assertEquals(0, cards.getCompletedFlashCards().size());
    }

    @Test
    public void testGetNextCard() {
        assertTrue(cards.hasMoreCards());
        assertEquals(test1, cards.getNextCard());
        assertEquals(test2, cards.getNextCard());
        assertEquals(test3, cards.getNextCard());
        assertFalse(cards.hasMoreCards());
    }

    @Test
    public void testCheckAnswer() {
        cards.getNextCard();
        assertFalse(cards.checkAnswer("a"));
        assertTrue(cards.checkAnswer("B"));
        assertTrue(cards.checkAnswer("b"));
        assertFalse(cards.checkAnswer("d"));

        cards.getNextCard();
        assertFalse(cards.checkAnswer("c"));
        assertTrue(cards.checkAnswer("D"));
        assertTrue(cards.checkAnswer("d"));
        assertFalse(cards.checkAnswer("C"));
    }

    @Test
    public void testSubmitAnswer() {
        cards.getNextCard();
        assertTrue(cards.getCurrentCard().getFirstGuess());
        assertEquals("Wrong!", cards.submitAnswer("d"));
        assertEquals(2, cards.getCurrentCard().getAttempts());
        assertFalse(cards.getCurrentCard().getFirstGuess());

        cards.getNextCard();
        assertTrue(cards.getCurrentCard().getFirstGuess());
        assertEquals("Correct!", cards.submitAnswer("d"));
        assertTrue(cards.getCurrentCard().getFirstGuess());
        cards.getNextCard();
        assertTrue(cards.getCurrentCard().getFirstGuess());
        assertEquals("Correct!", cards.submitAnswer("f"));
        assertTrue(cards.getCurrentCard().getFirstGuess());

        cards.getNextCard();
        assertFalse(cards.getCurrentCard().getFirstGuess());
        assertEquals("Correct!", cards.submitAnswer("b"));
        assertEquals(1, cards.getCurrentCard().getAttempts());
        assertEquals("Correct!", cards.submitAnswer("b"));
        assertEquals(0, cards.getCurrentCard().getAttempts());
        assertFalse(cards.hasMoreCards());

        // only 2/3 cards were guessed right on the first run through
        assertEquals(2, cards.countCorrect());


        String message = "You answered 2/3 flashcards correctly on the first try.\n"
                + "Your score is 66.7%.";
        assertEquals(message, cards.summary());
    }

    @Test
    public void testReset() {
        testSubmitAnswer();

    }
}
