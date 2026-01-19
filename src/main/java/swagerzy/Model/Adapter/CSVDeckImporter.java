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

        // Parsing logic
        int lineNumber = 0;
        for (String line : lines) {
            lineNumber++;
            
            // Splitting with ";", -1 so that the empty strings are also considered
            String[] parts = line.split(";", -1);

            // CHecking if data is correct
            if (parts.length < 2) {
                continue;
            }

            // Cleaning spaces at the end
            String front = parts[0].trim();
            String back = parts[1].trim();

            // Protection against empty flashcards
            if (front.isEmpty() && back.isEmpty()) {
                continue;
            }

            TextFlashcard card = new TextFlashcard(front, back);
            deck.addFlashcard(card);
        }     
        return deck;
    }
}
