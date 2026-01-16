package swagerzy.Model;

import swagerzy.Model.factories.DeckFactory;
import swagerzy.Model.composite.Deck;
import java.util.ArrayList;
import java.util.List;
import swagerzy.Model.composite.Flashcard;
import swagerzy.Model.factories.TextFlashcardFactory;
import swagerzy.los_peces.App;

public class DeckManager {
    private static DeckManager instance;
    private List<Deck> decks;
    private Deck currentDeck;
    private Flashcard currentFlashcard;
    
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
        Deck deck = factory.CreateDeck(deckName, deckDescription, currentDeck);
        
        if (currentDeck == null){
            decks.add(deck);
        }
        else {
            currentDeck.addChild(deck);
        }
    }
    
    public void createTextFlashcard(String question, String answer){
        TextFlashcardFactory factory = new TextFlashcardFactory();
        Flashcard card = factory.CreateFlashcard(question, answer, currentDeck);
        this.currentDeck.addChild(card);
    }
    
    public List<Deck> getDecks(){
        return this.decks;
    }
    
    public void setDecks(List<Deck> newDecks) {
        this.decks = newDecks;
    }
    
    public void goUp(){
        if (currentFlashcard != null){
            currentFlashcard = null;
            goToView();
        }
        else{
            if (currentDeck.getParent() != null){
                this.currentDeck = (Deck) currentDeck.getParent();
                goToView();
            }
            else{
                currentDeck = null;
                goToView();
            }
        }
            
    }
    
    public void goToView(){
        if (this.currentDeck == null){
            App.switchView("MainMenu");
        }
        else {
            App.switchView("DeckView");
        }
    }

    public Deck getCurrentDeck() {
        return currentDeck;
    }

    public void setCurrentDeck(Deck currentDeck) {
        this.currentDeck = currentDeck;
    }

    public Flashcard getCurrentFlashcard() {
        return currentFlashcard;
    }

    public void setCurrentFlashcard(Flashcard currentFlashcard) {
        this.currentFlashcard = currentFlashcard;
    }
    
}
