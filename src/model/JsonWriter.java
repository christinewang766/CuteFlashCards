package model;

import model.Deck;
import org.json.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;

// entire class inspired by JsonSerializationDemo
// Represents a writer that writes JSON representation of book to file
public class JsonWriter {
    private static final int TAB = 4;
    private PrintWriter writer;
    private String destination;

    // effects: constructs writer to write to destination file
    public JsonWriter(String destination) {
        this.destination = destination;
    }

    // modifies: this
    // effects: opens writer; throws FileNotFoundException if destination file cannot
    // be opened for writing
    public void open() throws FileNotFoundException {
        writer = new PrintWriter(new File(destination));
    }

    // modifies: this
    // effects: writes JSON representation of book to file
    public void write(Deck wr) {
        JSONObject json = wr.toJson();
        saveToFile(json.toString(TAB));
    }

    // modifies: this
    // effects: closes writer
    public void close() {
        writer.close();
    }

    // modifies: this
    // effects: writes string to file
    private void saveToFile(String json) {
        writer.print(json);
    }
}
