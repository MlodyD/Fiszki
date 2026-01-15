/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package swagerzy.Model.Adapter;

import java.io.File;
import java.util.List;
import swagerzy.Model.Adapter.FileReaders.CSVReader;
import swagerzy.Model.composite.Deck;
import swagerzy.Model.composite.TextFlashcard;

public class CSVDeckImporter implements DeckImporter {

    private CSVReader reader;

    public CSVDeckImporter() {
        this.reader = new CSVReader();
    }

    @Override
    public Deck importDeck(File file) throws Exception {
        List<String> lines = reader.readLines(file);

        String deckName = file.getName().replace(".csv", "");
        Deck deck = new Deck(deckName,"");

        // 3. LOGIKA PARSOWANIA
        int lineNumber = 0;
        for (String line : lines) {
            lineNumber++;
            
            // A. Dzielimy linię po średniku
            // Limit -1 sprawia, że puste stringi też są brane pod uwagę
            String[] parts = line.split(";", -1);

            // B. Sprawdzamy poprawność danych
            if (parts.length < 2) {
                System.err.println("Pominięto linię " + lineNumber + ": Nieprawidłowy format (brak średnika?) -> " + line);
                continue; // Skocz do następnej linii
            }

            // C. Wyciąganie danych i czyszczenie białych znaków (spacji na końcach)
            String front = parts[0].trim();
            String back = parts[1].trim();

            // D. Zabezpieczenie przed pustymi fiszkami
            if (front.isEmpty() && back.isEmpty()) {
                continue;
            }

            // E. Tworzenie obiektu domenowego (Twoja klasa Flashcard)
            TextFlashcard card = new TextFlashcard(front, back);
            
            // F. Dodanie do talii
            deck.addFlashcard(card);
        }

        System.out.println("Zaimportowano talię: " + deckName + " (" + deck.getChildren().size() + " kart)");
        
        return deck;
    }
}
