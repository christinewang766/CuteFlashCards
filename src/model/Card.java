package model;

import org.json.JSONObject;

/*
base for flashcards
 */
public class Card implements Writable {
    private String question;
    private String answer;
    private Boolean firstGuess;
    private Boolean complete;
    private Boolean starred;
    private int attempts;

    public static final int NUM_ATTEMPTS = 3;

    public Card(String question, String answer, Boolean complete, Boolean starred, int attempts, Boolean firstGuess) {
        this.attempts = attempts;
        this.question = question;
        this.answer = answer;
        this.starred = starred;
        this.complete = complete;
        this.firstGuess = firstGuess;
    }

    @Override
    // inspired by JsonSerializationDemo
    // effects: converts cat to Json
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("question", question);
        json.put("answer", answer);
        json.put("attempts", attempts);
        json.put("starred", starred);
        json.put("complete", complete);
        json.put("firstGuess", firstGuess);
        return json;
    }

    // setters
    public void setQuestion(String question) {
        this.question = question;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public  void setComplete(Boolean complete) { this.complete = complete; }

    public void setFirstGuess(Boolean firstGuess) { this.firstGuess = firstGuess; }

    public void setStarred(Boolean starred) { this.starred = starred; }

    public void setAttempts(int setting) { this.attempts = setting; }

    // getters
    public String getQuestion() {
        return this.question;
    }

    public String getAnswer() {
        return this.answer;
    }

    public Boolean getComplete() { return this.complete; }

    public Boolean getFirstGuess() { return this.firstGuess; }

    public Boolean getStarred() {
        return this.starred;
    }

    public int getAttempts() { return this.attempts; }


}
