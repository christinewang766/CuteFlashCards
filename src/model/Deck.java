package model;

import java.util.ArrayList;

import static model.Card.NUM_ATTEMPTS;

public class Deck {

    private ArrayList<Card> flashCards;
    private ArrayList<Card> completedFlashCards;
    private ArrayList<Card> starredFlashCards;
    private int nextCard;
    private Card currentCard;

    public Deck() {
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
                && !this.currentCard.getCorrect()) {
            return true;
        }
        return false;
    }

    // requires: card should be in flashCards
    // effects: determines if the num of attempts left should go
    // up or down depending on correctness of answer and if user
    // should repeat the card again
    public String submitAnswer(String answer) {
        if (checkAnswer(answer)) {
            int attempts = this.currentCard.getAttempts();
            if (attempts <= 1 || this.currentCard.getFirstGuess()) {
                this.currentCard.setCorrect(true);
                this.completedFlashCards.add(this.currentCard);
            }
            this.currentCard.setAttempts(attempts - 1);
            return ("Correct!");
        } else {
            this.currentCard.setFirstGuessFalse();
            this.currentCard.setAttempts(NUM_ATTEMPTS);
            // adds card to the back of the list
            Card wrongCard = new Card(this.currentCard.getQuestion(), this.currentCard.getAnswer());
            wrongCard.setFirstGuessFalse();
            this.flashCards.add(wrongCard);
            return ("Wrong!");
        }
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
        double percentageCorrect = this.countCorrect() / this.completedFlashCards.size();
        return "You answered " + countCorrect() + "/" + this.completedFlashCards.size()
                + "flashcards correctly on the first try.\n"
                + "Your score is " + percentageCorrect + "%.";
    }

    // modifies: this
    // effects: fresh batch of cards with no history
    public void resetCards() {
        for (Card card : this.completedFlashCards) {
            this.flashCards.add(card);
            this.completedFlashCards.remove(card);
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
