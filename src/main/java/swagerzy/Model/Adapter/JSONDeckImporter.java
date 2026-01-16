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

public class JSONDeckImporter implements DeckImporter {

    private Gson gson;

    public JSONDeckImporter() {
        this.gson = new Gson();
    }

    @Override
    public Deck importDeck(File file) throws Exception {
        // 1. Adaptee (Gson) czyta plik i tworzy drzewo obiektów JSON
        try (FileReader reader = new FileReader(file)) {
            
            // Parsujemy plik do obiektu JsonObject
            JsonObject rootObject = gson.fromJson(reader, JsonObject.class);

            // 2. Logika Adaptacji
            if (isDeck(rootObject)) {
                // Rozpoczynamy parsowanie od korzenia.
                // Korzeń importowanej talii nie ma rodzica (dlatego null), 
                // zostanie on ewentualnie dodany do rodzica w kontrolerze po imporcie.
                return (Deck) parseElement(rootObject, null);
            } else {
                throw new IllegalArgumentException("Główny element JSON musi być talią (mieć children)!");
            }
        }
    }

    /**
     * Metoda REKURENCYJNA
     * Teraz przyjmuje parametr 'parent', aby przekazać go do konstruktora dziecka.
     */
    /**
     * Metoda REKURENCYJNA - wersja bezpieczna (odporna na brakujące pola)
     */
    private CompositeElement parseElement(JsonObject json, CompositeElement parent) {
        // Zabezpieczenie przed pustym obiektem
        if (json == null) return null;

        // 1. BEZPIECZNE POBIERANIE PÓL (To naprawia Twój błąd!)
        // Używamy helpera (kod niżej) albo sprawdzamy .has()
        String front = getSafeString(json, "front", "Bez nazwy");
        String description = getSafeString(json, "description", "");
        String back = getSafeString(json, "back", "");

        // SPRAWDZENIE: Czy to Deck czy Fiszka?
        if (isDeck(json)) {
            // --- To jest TALIA ---
            Deck deck = new Deck(front, description, parent);
            
            // Pobieramy tablicę dzieci (może być null, jeśli json jest uszkodzony)
            if (json.has("children")) {
                JsonArray childrenJson = json.getAsJsonArray("children");
                
                for (JsonElement childElement : childrenJson) {
                    JsonObject childObj = childElement.getAsJsonObject();
                    CompositeElement component = parseElement(childObj, deck); // Rekurencja
                    if (component != null) {
                        deck.addChild(component);
                    }
                }
            }
            
            return deck;

        } else {
            // --- To jest FISZKA ---
            return new TextFlashcard(front, back, parent);
        }
    }

    // --- METODA POMOCNICZA ---
    // Zwraca wartość pola lub domyślny tekst, jeśli pola nie ma
    private String getSafeString(JsonObject json, String key, String defaultValue) {
        if (json.has(key) && !json.get(key).isJsonNull()) {
            return json.get(key).getAsString();
        }
        return defaultValue;
    }

    // Pomocnicza metoda sprawdzająca typ
    private boolean isDeck(JsonObject json) {
        return json.has("children");
    }
}