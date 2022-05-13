package model;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

// entire class inspired by JsonSerializationDemo
// Represents a reader that reads book from JSON data stored in file
public class JsonReader {
    private String source;


    // effects: constructs reader to read from source file
    public JsonReader(String source) {
        this.source = source;
    }


    // effects: reads book from file and returns it;
    // throws IOException if an error occurs reading data from file
    public Deck read() throws IOException {
        String jsonData = readFile(source);
        JSONObject jsonObject = new JSONObject(jsonData);
        return parseWorkRoom(jsonObject);
    }


    // effects: reads source file as string and returns it
    private String readFile(String source) throws IOException {
        StringBuilder contentBuilder = new StringBuilder();

        try (Stream<String> stream = Files.lines(Paths.get(source), StandardCharsets.UTF_8)) {
            stream.forEach(s -> contentBuilder.append(s));
        }
        return contentBuilder.toString();
    }


    // effects: parses deck from JSON object and returns it
    private Deck parseWorkRoom(JSONObject jsonObject) {
        String title = jsonObject.getString("title");
        Deck deck = new Deck(title);
        addDeck(deck, jsonObject);
        return deck;
    }


    // modifies: deck
    // effects: parses cards from JSON object and adds them to deck
    private void addDeck(Deck deck, JSONObject jsonObject) {
        JSONArray jsonArray = jsonObject.getJSONArray("deck");
        for (Object json : jsonArray) {
            JSONObject nextCard = (JSONObject) json;
            addCard(deck, nextCard);
        }
    }

    // modifies: deck
    // effects: parses card from JSON object and adds it to deck
    private void addCard(Deck deck, JSONObject jsonObject) {
        String question = jsonObject.getString("question");
        String answer = jsonObject.getString("answer");
        Boolean complete = jsonObject.getBoolean("complete");
        Boolean starred = jsonObject.getBoolean("starred");
        int attempts = jsonObject.getInt("attempts");
        Boolean firstGuess = jsonObject.getBoolean("firstGuess");

        Card card = new Card(question, answer, complete, starred, attempts, firstGuess);
        if(complete) {
            deck.addCompleteCard(card);
        } else {
            deck.addFlashCard(card);
        }
    }
}