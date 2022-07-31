package model;

import org.json.JSONArray;
import org.json.JSONObject;

import java.lang.reflect.Array;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static model.Card.NUM_ATTEMPTS;

public class Deck implements Writable {

    private ArrayList<Card> deckCards;
    private ArrayList<Card> flashCards;
    private ArrayList<Card> completedFlashCards;
    private ArrayList<Card> starredFlashCards;
    private int nextCard;
    private Card currentCard;
    private String title;

    public Deck(String title) {
        this.title = title;
        this.deckCards = new ArrayList<>();
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
        return this.currentCard.getAnswer().equalsIgnoreCase(answer)
                && !this.currentCard.getComplete();
    }

    // requires: card should be in flashCards
    // effects: determines if the num of attempts left should go
    // up or down depending on correctness of answer and if user
    // should repeat the card again
    public String submitAnswer(String answer) {
        if (checkAnswer(answer)) {
            if ((this.currentCard.getAttempts() == 1) || (this.currentCard.getFirstGuess())) {
                this.currentCard.setComplete(true);
                this.completedFlashCards.add(this.currentCard);
                System.out.println("Correct!");
                return ("Correct!");
            } else {
                this.currentCard.setFirstGuess(false);
                Card tryAgain = new Card(this.currentCard.getQuestion(), this.currentCard.getAnswer(), false,
                        this.currentCard.getStarred(), this.currentCard.getAttempts()-1, false);
                addFlashCard(tryAgain);
                System.out.println("Correct! but not first guess. Try again!");
                return ("Correct! but not first guess. Try again!");
            }
        } else {
            // adds card to the back of the list
            this.currentCard.setFirstGuess(false);
            Card wrongCard = new Card(this.currentCard.getQuestion(), this.currentCard.getAnswer(), false,
                    this.currentCard.getStarred(), NUM_ATTEMPTS, false);
            addFlashCard(wrongCard);
            System.out.println("Wrong!");
            return ("Wrong!");
        }
    }

    // effects: shuffles the deck
    public void shuffle(List<Card> cards) {
        Collections.shuffle(cards);
    }

    // requires: only called when there are no "completed" cards
    // effects: filters out the not-starred flashcards
    public ArrayList<Card> starredOnly() {
        for (Card card : this.flashCards) {
            if (card.getStarred()) {
                starredFlashCards.add(card);
            }
        }
        return this.starredFlashCards;
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

        System.out.println("You answered " + this.countCorrect() + "/" + this.completedFlashCards.size()
                + " flashcards correctly on the first try.\n"
                + "Your score is " + oneDecimal.format(percentageCorrect) + "%.");

        return "You answered " + this.countCorrect() + "/" + this.completedFlashCards.size()
                + " flashcards correctly on the first try.\n"
                + "Your score is " + oneDecimal.format(percentageCorrect) + "%.";
    }

    // modifies: this
    // effects: fresh batch of cards with no history
    public void resetCards() {
        this.flashCards.clear();
        for (Card card : this.completedFlashCards) {
            addFlashCard(card);
        }
        this.completedFlashCards.clear();
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


    @Override
    // inspired by JsonSerializationDemo
    // effects: converts book to Json
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("title", title);
        json.put("deck", deckToJson());
        return json;
    }

    // inspired by JsonSerializationDemo
    // effects: returns things in this deck as a JSON array
    private JSONArray deckToJson() {
        JSONArray jsonArray = new JSONArray();

        for (Card t : getUnfinishedFlashcards()) {
            jsonArray.put(t.toJson());
        }
        for (Card t : completedFlashCards) {
            jsonArray.put(t.toJson());
        }
        ArrayList<Card> combined = new ArrayList<>();
        combined.addAll(getUnfinishedFlashcards());
        combined.addAll(getCompletedFlashCards());

        ArrayList<String> combinedQuestions = new ArrayList<>();
        ArrayList<String> deckCardQuestions = new ArrayList<>();
        for (Card card : combined) {
            combinedQuestions.add(card.getQuestion());
        }
        for (Card deckCard : getCards()) {
            deckCardQuestions.add(deckCard.getQuestion());
            }

        for (String deckCardQuestion : deckCardQuestions) {
            if (!combinedQuestions.contains(deckCardQuestion)) {
                for (Card card : getCards())
                    if (deckCardQuestion == card.getQuestion()) {
                        jsonArray.put(card.toJson());
                    }
            }
        }

//        for (Card deckCard : this.deckCards) {
//            if (!getUnfinishedFlashcards().contains(deckCard)
//                    && !completedFlashCards.contains(deckCard)) {
//                jsonArray.put(deckCard.toJson());
//            }
//        }
        return jsonArray;
    }

    // getters

    // effects: deletes the cards marked complete from flashcards
    public ArrayList<Card> getUnfinishedFlashcards() {
        ArrayList<Card> copy = new ArrayList<>();
        ArrayList<Card> returnUnfinished = new ArrayList<>();
        for (Card card : this.flashCards) {
            copy.add(card);
        }
        int indexBookMark = copy.indexOf(this.currentCard);
        for (Card card : copy) {
            if (copy.indexOf(card) > indexBookMark) {
                returnUnfinished.add(card);
            }
        }
        if (!this.currentCard.getComplete()) {
            returnUnfinished.add(this.currentCard);
        }
        return returnUnfinished;
    }

    public int getCountOfUnfinishedFlashcards() {
        int i = 1;
        ArrayList<Card> copy = new ArrayList<>();
        for (Card card : this.flashCards) {
            copy.add(card);
        }
        int indexBookMark = copy.indexOf(this.currentCard);
        for (Card card : copy) {
            if (copy.indexOf(card) > indexBookMark) {
                i++;
            }
        }
        return i;
    }

    public ArrayList<Card> getFlashCards() {
        return this.flashCards;
    }

    public ArrayList<Card> getCompletedFlashCards() {
        return this.completedFlashCards;
    }

    public ArrayList<Card> getCards() {
        return this.deckCards;
    }

    public void setCards(ArrayList<Card> cards) {
        this.deckCards = cards;
    }

    public void setFlashCards(ArrayList<Card> cards) {
        this.flashCards = cards;
    }

    public Card getCurrentCard() {
        return this.currentCard;
    }

    public String getTitle() { return this.title; }
    public void setTitle(String title) {
        this.title = title;
    }

    public int getNextCardNum() {
        return this.nextCard;
    }
}
