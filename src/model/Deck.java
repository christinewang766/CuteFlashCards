package model;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static model.Card.NUM_ATTEMPTS;

public class Deck {

    private ArrayList<Card> flashCards;
    private ArrayList<Card> completedFlashCards;
    private ArrayList<Card> starredFlashCards;
    private int nextCard;
    private Card currentCard;
    private String title;

    public Deck(String title) {
        this.title = title;
        this.flashCards = new ArrayList<>();
        this.completedFlashCards = new ArrayList<>();
        this.starredFlashCards = new ArrayList<>();
        this.nextCard = 0;
        this.currentCard = null;
    }

    // effects: returns whether current card is the last
    public boolean hasMoreCards() {
        return (this.nextCard < this.flashCards.size());
    }

    // requires: hasMoreCards()
    // modifies: this
    // effects: returns next card in flashcards
    public Card getNextCard() {
        this.currentCard = flashCards.get(this.nextCard);
        this.nextCard += 1;
        return this.currentCard;
    }

    // modifies: this
    // effects: checks the answer to the current card
    public boolean checkAnswer(String answer) {
        if (this.currentCard.getAnswer().equalsIgnoreCase(answer)
                && !this.currentCard.getComplete()) {
            return true;
        }
        return false;
    }

    // requires: card should be in flashCards
    // effects: determines if the num of attempts left should go
    // up or down depending on correctness of answer and if user
    // should repeat the card again
    public String submitAnswer(String answer) {
        if (checkAnswer(answer) ) {
            int attempts = this.currentCard.getAttempts();
            if (attempts == 1 || this.currentCard.getFirstGuess()) {
                this.currentCard.setComplete(true);
                this.completedFlashCards.add(this.currentCard);
            }
            this.currentCard.setAttempts(attempts - 1);
            return ("Correct!");
        } else {
            // adds card to the back of the list
            this.currentCard.setFirstGuess(false);
            Card wrongCard = new Card(this.currentCard.getQuestion(), this.currentCard.getAnswer(), false,
                    this.currentCard.getStarred(), NUM_ATTEMPTS, this.currentCard.getFirstGuess());
            wrongCard.setFirstGuess(false);
            addFlashCard(wrongCard);
            return ("Wrong!");
        }
    }

    private void getUnfinishedFlashcards() {

    }

    // effects: shuffles the deck
    public void shuffle() {
        Collections.shuffle(this.flashCards);
    }


    // effects: totals up the number of cards the user correctly
    // guessed on the FIRST try
    public int countCorrect() {
        int numCorrect = 0;

        for (Card card : this.completedFlashCards) {
            if (card.getFirstGuess()) {
                numCorrect++;
            }
        }
        return numCorrect;
    }

    // effects: summary of user's accuracy
    public String summary() {
        DecimalFormat oneDecimal = new DecimalFormat("#.#");

        double percentageCorrect = 100 * (double) this.countCorrect() / (double) this.completedFlashCards.size();

        return "You answered " + this.countCorrect() + "/" + this.completedFlashCards.size()
                + " flashcards correctly on the first try.\n"
                + "Your score is " + oneDecimal.format(percentageCorrect) + "%.";
    }

    // modifies: this
    // effects: fresh batch of cards with no history
    public void resetCards() {
        deleteAllPrevious();
        for (Card card : this.completedFlashCards) {
            addFlashCard(card);
            this.completedFlashCards.remove(card);
        }
        for (Card card : this.flashCards) {
            card.setComplete(false);
            card.setAttempts(NUM_ATTEMPTS);
            card.setFirstGuess(true);
        }
    }

    // modifies: this
    // effects: star all the cards that were wrong
    public void starAllWrong() {
        for (Card card : this.completedFlashCards) {
            if (!card.getFirstGuess()) {
                starredFlashCards.add(card);
            }
        }
    }

    // requires: card object should not be null
    // modifies: this
    // effects: adds a card object to a List of Card
    public void addFlashCard(Card card) {
        this.flashCards.add(card);
    }

    // requires: card object should not be null
    // modifies: this
    // effects: adds a card object to a List of Card
    public void addCompleteCard(Card card) {
        this.completedFlashCards.add(card);
    }

    // effects: iterates through flashcards to delete all cards
    // that have already been attempted
    private void deleteAllPrevious() {
        int indexBookMark = flashCards.indexOf(this.currentCard);
        for(int i = 0; i < indexBookMark; i++) {
            flashCards.remove(0);
        }
    }

    // effects: returns an unmodifiable list of flashcards in this deck
    public List<Card> getUnmodFlashCards() {
        return Collections.unmodifiableList(this.flashCards);
    }

    // effects: returns an unmodifiable list of completed cards in this deck
    public List<Card> getUnmodCompletedCards() {
        return Collections.unmodifiableList(this.completedFlashCards);
    }

    // getters
    public ArrayList<Card> getFlashCards() {
        return this.flashCards;
    }

    public ArrayList<Card> getCompletedFlashCards() {
        return this.completedFlashCards;
    }

    public Card getCurrentCard() {
        return this.currentCard;
    }

}
