package model;

import org.json.JSONObject;

// inspired by JsonSerializationDemo
// returns this as JSON object
public interface Writable {
    JSONObject toJson();
}
