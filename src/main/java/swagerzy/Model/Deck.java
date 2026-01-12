package swagerzy.Model;

import java.util.List;

public class Deck {
    private int deckId;
    private String deckName;
    private List<Flashcard> flashcards;

    
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
