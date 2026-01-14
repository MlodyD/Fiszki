package swagerzy.Model.composite;

import java.util.ArrayList;
import java.util.List;

public class Deck implements CompositeElement {
    
    private String id;
    private String deckName;
    private String deckDescription;
    private List<CompositeElement> children;
    private CompositeElement parent;

    
    public Deck(String deckName, String deckDescription){
        this.deckName = deckName;
        this.deckDescription = deckDescription;
        this.id = java.util.UUID.randomUUID().toString();
        this.children = new ArrayList<>();
    }
    
    public Deck(){
        this.id = java.util.UUID.randomUUID().toString();
        this.children = new ArrayList<>();
    }
    
    public String getId() {
        return this.id;
    }

    public String getFront() {
        return deckName;
    }

    public void setDeckName(String deckName) {
        this.deckName = deckName;
    }

    public List<CompositeElement> getChildren() {
        return children;
    }

    public void setFlashcards(List<CompositeElement> flashcards) {
        this.children = flashcards;
    }
    
    public void addFlashcard(Flashcard flashcard){
        this.children.add(flashcard);
    }
    
    public CompositeElement getParent(){
        return this.parent;
    }
    
}
