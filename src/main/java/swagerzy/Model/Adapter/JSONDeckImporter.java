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
    private CompositeElement parseElement(JsonObject json, CompositeElement parent) {
        // Pobieramy "front" jako nazwę talii lub pytanie fiszki
        String front = json.get("front").getAsString();

        if (isDeck(json)) {
            // --- TO JEST TALIA ---
            
            // 1. Pobierz opis (zabezpieczenie, jeśli go nie ma w JSON)
            String description = "";
            if (json.has("description")) {
                description = json.get("description").getAsString();
            }

            // 2. Stwórz obiekt Deck przekazując rodzica (parent)
            Deck deck = new Deck(front, description, parent);
            
            // 3. Pobierz dzieci
            JsonArray childrenJson = json.getAsJsonArray("children");
            
            // 4. Rekurencja dla dzieci
            for (JsonElement childElement : childrenJson) {
                JsonObject childObj = childElement.getAsJsonObject();
                
                // WAŻNE: Tutaj przekazujemy 'deck' (tę talię, którą właśnie stworzyliśmy)
                // jako rodzica dla jej dzieci!
                CompositeElement component = parseElement(childObj, deck); 
                
                // Dodajemy dziecko do listy dzieci tej talii
                deck.addChild(component);
            }
            
            return deck;

        } else {
            // --- TO JEST FISZKA ---
            
            String back = "";
            if (json.has("back")) {
                back = json.get("back").getAsString();
            }
            
            // Tworzymy fiszkę przekazując rodzica
            return new TextFlashcard(front, back, parent);
        }
    }

    // Pomocnicza metoda sprawdzająca typ
    private boolean isDeck(JsonObject json) {
        return json.has("children");
    }
}