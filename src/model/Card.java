package model;

/*
base for flashcards
 */
public class Card {
    private String question;
    private String answer;
    private Boolean correct;
    private Boolean firstGuess;
    private int attempts;

    public static final int NUM_ATTEMPTS = 2;

    public Card(String question, String answer) {
        this.attempts = NUM_ATTEMPTS;
        this.question = question;
        this.answer = answer;
        this.correct = false;
        this.firstGuess = true;
    }

    // setters
    public void setQuestion(String question) {
        this.question = question;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public void setCorrect(Boolean setting) {
        this.correct = setting;
    }

    public void setFirstGuessFalse() {
        this.firstGuess = false;
    }

    public void setAttempts(int setting) {
        this.attempts = setting;
    }

    // getters
    public String getQuestion() {
        return this.question;
    }

    public String getAnswer() {
        return this.answer;
    }

    public Boolean getFirstGuess() {
        return this.firstGuess;
    }

    public Boolean getCorrect() {
        return this.correct;
    }

    public int getAttempts() {
        return this.attempts;
    }

}
