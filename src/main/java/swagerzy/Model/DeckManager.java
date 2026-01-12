package swagerzy.Model;

import java.util.ArrayList;
import java.util.List;

public class DeckManager {
    private static DeckManager instance;
    private List<Deck> decks;
    
    private DeckManager() {
        this.decks = new ArrayList<Deck>();
    }
    
    // 3. Publiczna statyczna metoda dostępu
    public static DeckManager getInstance() {
        if (instance == null) {
            instance = new DeckManager();
        }
        return instance;
    }
    
    public void createDeck(String deckName, String deckDescription){
        //Deck deck = new Deck(deckName, deckDescription, decks.size());
        Deck deck = new Deck(deckName, deckDescription);
        decks.add(deck);
    }
    
    public List<Deck> getDecks(){
        return this.decks;
    }
}
