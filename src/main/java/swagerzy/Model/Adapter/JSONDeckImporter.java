/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package swagerzy.Model.Adapter;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import java.io.File;
import java.io.FileReader;
import swagerzy.Model.composite.CompositeElement;
import swagerzy.Model.composite.Deck;
import swagerzy.Model.composite.TextFlashcard;

/**
 * Adapter class that converts JSON file structures into the internal Deck hierarchy.
 * Implements the DeckImporter interface to allow polymorphic data loading.
 */
public class JSONDeckImporter implements DeckImporter {

    private Gson gson;

    public JSONDeckImporter() {
        this.gson = new Gson();
    }

    /**
     * Adapts the GSON library output to the application's Composite model.
     * @param file The JSON file to import.
     * @return A structured Deck object.
     * @throws Exception If the JSON format is invalid.
     */
    @Override
    public Deck importDeck(File file) throws Exception {
        // Gson (Adaptee) reads a file and creates a tree of JSON objects
        try (FileReader reader = new FileReader(file)) {
            
            // Parsing the file to the JsonObject
            JsonObject rootObject = gson.fromJson(reader, JsonObject.class);

            if (isDeck(rootObject)) {
                return (Deck) parseElement(rootObject, null);
            } else {
                throw new IllegalArgumentException("Main JSON element has to be a deck (have children)!");
            }
        }
    }

    private CompositeElement parseElement(JsonObject json, CompositeElement parent) {
        // Protection against an empty object
        if (json == null) return null;

        String front = getSafeString(json, "front", "Bez nazwy");
        String description = getSafeString(json, "description", "");
        String back = getSafeString(json, "back", "");

        // Checking if it's a deck or a flashcard
        if (isDeck(json)) {
            // deck
            Deck deck = new Deck(front, description, parent);

            if (json.has("children")) {
                JsonArray childrenJson = json.getAsJsonArray("children");
                
                for (JsonElement childElement : childrenJson) {
                    JsonObject childObj = childElement.getAsJsonObject();
                    CompositeElement component = parseElement(childObj, deck); // recursion
                    if (component != null) {
                        deck.addChild(component);
                    }
                }
            }
            
            return deck;

        } else {
            // flashcard
            return new TextFlashcard(front, back, parent);
        }
    }

    private String getSafeString(JsonObject json, String key, String defaultValue) {
        if (json.has(key) && !json.get(key).isJsonNull()) {
            return json.get(key).getAsString();
        }
        return defaultValue;
    }

    private boolean isDeck(JsonObject json) {
        return json.has("children");
    }
}
