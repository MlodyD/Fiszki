package swagerzy.Model;

import java.util.List;

public class Deck {
    
    private int deckId;
    private String id;
    private String deckName;
    private String deckDescription;
    private List<Flashcard> flashcards;

    
    public Deck(String deckName, String deckDescription){
        this.deckName = deckName;
        this.deckDescription = deckDescription;
        this.id = java.util.UUID.randomUUID().toString();
    }
    
    public Deck(String deckName, String deckDescription, int deckId){
        this.deckName = deckName;
        this.deckDescription = deckDescription;
        this.deckId = deckId;
    }
    
    public int getDeckId() {
        return deckId;
    }

    public void setDeckId(int deckId) {
        this.deckId = deckId;
    }
    
    public String getDeckName() {
        return deckName;
    }

    public void setDeckName(String deckName) {
        this.deckName = deckName;
    }

    public List<Flashcard> getFlashcards() {
        return flashcards;
    }

    public void setFlashcards(List<Flashcard> flashcards) {
        this.flashcards = flashcards;
    }
    
    public void addFlashcard(Flashcard flashcard){
        this.flashcards.add(flashcard);
    }
    
}
