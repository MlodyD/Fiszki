package swagerzy.Model;

import java.io.File;
import swagerzy.Model.factories.DeckFactory;
import swagerzy.Model.composite.Deck;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import swagerzy.Model.composite.CompositeElement;
import swagerzy.Model.composite.Flashcard;
import swagerzy.Model.composite.ImageFlashcard;
import swagerzy.Model.composite.TextFlashcard;
import swagerzy.Model.factories.FlashcardFactory;
import swagerzy.Model.memento.StudySessionMemento;
import swagerzy.Model.observer.StudyObserver;
import swagerzy.Model.strategy.StandardStrategy;
import swagerzy.Model.strategy.StudyStrategy;
import swagerzy.las_fichas.App;

public class DeckManager {
    private static DeckManager instance;
    private List<Deck> decks;
    private Deck currentDeck;
    private Flashcard currentFlashcard;
    private StudyStrategy currentStrategy = new StandardStrategy();
    private List<StudyObserver> observers = new ArrayList<>();
    private Map<String, Integer> allDecksProgress = new HashMap<>();
    private int currentIndex = 0;
    
    private DeckManager() {
        this.decks = new ArrayList<>();
    }
    
    public static DeckManager getInstance() {
        if (instance == null) {
            instance = new DeckManager();
        }
        return instance;
    }
    
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
        FlashcardFactory factory = new FlashcardFactory();
        Flashcard card = factory.createTextFlashcard(question, answer, currentDeck);
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

    public void setCurrentDeck(Deck deck) {
        if (this.currentDeck != null) {
            allDecksProgress.put(this.currentDeck.getFront(), this.currentIndex);
        }
        this.currentDeck = deck;
        this.currentIndex = allDecksProgress.getOrDefault(deck != null ? deck.getFront() : "", 0);
    }

    public Flashcard getCurrentFlashcard() {
        return currentFlashcard;
    }

    public void setCurrentFlashcard(Flashcard currentFlashcard) {
        this.currentFlashcard = currentFlashcard;
    }
    
    public void setStrategy(StudyStrategy strategy) {
        this.currentStrategy = strategy;
    }

    public List<Flashcard> getCardsForStudy(List<Flashcard> rawCards) {
        return currentStrategy.getOrderedCards(rawCards);
    }
    
    public List<Flashcard> getCardsForCurrentSession() {
        List<Flashcard> cards = currentDeck.getOnlyFlashcards(); // Getting flashcards from a deck (Composite)
        return currentStrategy.getOrderedCards(cards);
    }
    
    public void addObserver(StudyObserver observer) {
        observers.add(observer);
    }

    public void removeObserver(StudyObserver observer) {
        observers.remove(observer);
    }

    public void notifyObservers(int currentIndex, int total) {
        for (StudyObserver observer : observers) {
            observer.updateProgress(currentIndex, total);
        }
    }
    
    public int getCurrentIndex() {
        return currentIndex;
    }

    public void setCurrentIndex(int currentIndex) {
        this.currentIndex = currentIndex;
    }
    
    public void updateCurrentDeckProgress() {
        if (this.currentDeck != null) {
            allDecksProgress.put(this.currentDeck.getFront(), this.currentIndex);
        }
    }
    
    public StudySessionMemento createMemento() {
        updateCurrentDeckProgress();
        return new StudySessionMemento(allDecksProgress);
    }

    public void setMemento(StudySessionMemento memento) {
        if (memento != null) {
            this.allDecksProgress = new HashMap<>(memento.getAllProgress());

            // Setting an index
            if (this.currentDeck != null) {
                this.currentIndex = memento.getIndexForDeck(this.currentDeck.getFront());
            }
        }
    }
    
    public void deleteDeck(Deck deck) {
        if (decks.contains(deck)) {
            decks.remove(deck);
        } else {
            // Looking for a deck in its parent if it has it
            for (Deck d : decks) {
                if (findAndRemoveFromParent(d, deck)) break;
            }
        }
        saveLibrary();
    }

    private boolean findAndRemoveFromParent(Deck parent, Deck target) {
        if (parent.getChildren().contains(target)) {
            parent.removeChild(target);
            return true;
        }
        for (CompositeElement child : parent.getChildren()) {
            if (child instanceof Deck && findAndRemoveFromParent((Deck) child, target)) {
                return true;
            }
        }
        return false;
    }
    
    public void saveLibrary() {
        StorageService storage = new StorageService();
        storage.saveLibrary(this.decks); 
    }
    
    public void deleteFlashcard(Deck parent, Flashcard flashcard) {
        if (parent != null && flashcard != null) {
            // If it's an image flashcard, delete the file from the folder
            if (flashcard instanceof ImageFlashcard) {
                ImageFlashcard ifc = (ImageFlashcard) flashcard;
                String path = ifc.getImagePath();

                if (path != null && !path.isEmpty()) {
                    File file = new File(path);
                    if (file.exists()) {
                        file.delete();
                    }
                }
            }

            // Delete a flashcard from the Composite module
            parent.removeChild(flashcard);

            // Save changes in json file
            saveLibrary();
        }
    }
    
    public void updateFlashcard(Flashcard card, String newFront, String newBack, String newImagePath) {
        if (card instanceof TextFlashcard) {
            card.setFront(newFront);
            ((TextFlashcard) card).setAnswer(newBack);
        } else if (card instanceof ImageFlashcard) {
            card.setFront(newFront);
            ((ImageFlashcard) card).setBack(newBack);
            ((ImageFlashcard) card).setImagePath(newImagePath);
        }
        saveLibrary(); // Save changes in json file
    }
}
