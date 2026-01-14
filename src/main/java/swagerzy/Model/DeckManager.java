package swagerzy.Model;

import swagerzy.Model.factories.DeckFactory;
import swagerzy.Model.composite.Deck;
import swagerzy.Model.composite.CompositeElement;
import java.util.ArrayList;
import java.util.List;

public class DeckManager {
    private static DeckManager instance;
    private List<Deck> decks;
    private Deck currentDeck;
    
    private DeckManager() {
        this.decks = new ArrayList<>();
    }
    
    public static DeckManager getInstance() {
        if (instance == null) {
            instance = new DeckManager();
        }
        return instance;
    }
    
    //logic
    
    public void createDeck(String deckName, String deckDescription){
        DeckFactory factory = new DeckFactory();
        Deck deck = factory.CreateDeck(deckName, deckDescription);
        decks.add(deck);
    }
    
    public List<Deck> getDecks(){
        return this.decks;
    }

    public Deck getCurrentDeck() {
        return currentDeck;
    }

    public void setCurrentDeck(Deck currentDeck) {
        this.currentDeck = currentDeck;
    }
    
}
