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
    private Deck deck;


    @BeforeEach
    public void runBefore() {
        deck = new Deck("Tester Title");
        test1 = new Card("a", "b", false, false, Card.NUM_ATTEMPTS, true);
        test2 = new Card("c", "d", false, false, Card.NUM_ATTEMPTS, true);
        test3 = new Card("e", "f", false, false, Card.NUM_ATTEMPTS, true);
        deck.getFlashCards().add(test1);
        deck.getFlashCards().add(test2);
        deck.getFlashCards().add(test3);
    }

    @Test
    public void testConstructor() {
        assertNull(deck.getCurrentCard());
        assertEquals(3, deck.getFlashCards().size());
        assertEquals(0, deck.getCompletedFlashCards().size());
    }

    @Test
    public void testGetNextCard() {
        assertTrue(deck.hasMoreCards());
        assertEquals(test1, deck.getNextCard());
        assertEquals(test2, deck.getNextCard());
        assertEquals(test3, deck.getNextCard());
        assertFalse(deck.hasMoreCards());
    }

    @Test
    public void testCheckAnswer() {
        deck.getNextCard();
        assertFalse(deck.checkAnswer("a"));
        assertTrue(deck.checkAnswer("B"));
        assertTrue(deck.checkAnswer("b"));
        assertFalse(deck.checkAnswer("d"));

        deck.getNextCard();
        assertFalse(deck.checkAnswer("c"));
        assertTrue(deck.checkAnswer("D"));
        assertTrue(deck.checkAnswer("d"));
        assertFalse(deck.checkAnswer("C"));
    }

    @Test
    public void testSubmitAnswer() {
        deck.getNextCard();
        // question = a, answer = b
        assertTrue(deck.getCurrentCard().getFirstGuess());
        assertEquals("Wrong!", deck.submitAnswer("d"));
        assertEquals(3, deck.getCurrentCard().getAttempts());
        assertFalse(deck.getCurrentCard().getFirstGuess());
        // list is :
//        question = c, answer = d, 3 tries
//        question = e, answer = f, 3 tries
//        question = a, answer = b, 3 tries


        deck.getNextCard();
        // question = c, answer = d
        assertTrue(deck.getCurrentCard().getFirstGuess());
        assertEquals("Correct!", deck.submitAnswer("d"));
        assertTrue(deck.getCurrentCard().getFirstGuess());
        // list is :
//        question = e, answer = f, 3 tries
//        question = a, answer = b, 3 tries

        deck.getNextCard();
        // question = e, answer = f
        assertTrue(deck.getCurrentCard().getFirstGuess());
        assertEquals("Correct!", deck.submitAnswer("f"));
        assertTrue(deck.getCurrentCard().getFirstGuess());
        // list is :
//        question = a, answer = b, 3 tries

        deck.getNextCard();
        // question = a, answer = b, 3 tries
        assertFalse(deck.getCurrentCard().getFirstGuess());
        assertEquals("Correct! but not first guess. Try again!", deck.submitAnswer("b"));

        deck.getNextCard();
        // question = a, answer = b, 2 tries
        assertFalse(deck.getCurrentCard().getFirstGuess());
        assertEquals(2, deck.getCurrentCard().getAttempts());
        assertEquals("Correct! but not first guess. Try again!", deck.submitAnswer("b"));
        deck.getNextCard();
        // question = a, answer = b, 1 tries
        assertFalse(deck.getCurrentCard().getFirstGuess());
        assertEquals(1, deck.getCurrentCard().getAttempts());
        assertEquals("Correct!", deck.submitAnswer("b"));
        assertFalse(deck.hasMoreCards());

        // only 2/3 cards were guessed right on the first run through
        assertEquals(2, deck.countCorrect());


        String message = "You answered 2/3 flashcards correctly on the first try.\n"
                + "Your score is 66.7%.";
        assertEquals(message, deck.summary());
    }

    @Test
    public void testReset() {
        testSubmitAnswer();
    }
}
