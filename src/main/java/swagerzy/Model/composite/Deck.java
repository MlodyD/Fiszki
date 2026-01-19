package swagerzy.Model.composite;

import com.google.gson.annotations.SerializedName;
import java.util.ArrayList;
import java.util.List;

/**
 * Composite class representing a collection of flashcards or sub-decks.
 */
public class Deck implements CompositeElement {
    
    protected transient final String id = java.util.UUID.randomUUID().toString();
    
    @SerializedName("front")
    private String deckName;
    
    @SerializedName("description")
    private String deckDescription;
    
    private List<CompositeElement> children;
    private transient CompositeElement parent;

    
    public Deck(String deckName, String deckDescription){
        this.deckName = deckName;
        this.deckDescription = deckDescription;
        this.children = new ArrayList<>();
    }
    
    public Deck(String deckName, String deckDescription, CompositeElement parent){
        this.deckName = deckName;
        this.deckDescription = deckDescription;
        this.children = new ArrayList<>();
        this.parent = parent;
    }
    
    public Deck(){
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

    /**
     * Returns the list of children elements.
     * @return List of CompositeElement children.
     */
    public List<CompositeElement> getChildren() {
        return children;
    }

    public void setFlashcards(List<CompositeElement> flashcards) {
        this.children = flashcards;
    }
    
    public String getDescription(){
        return this.deckDescription;
    }
    
    public void setDescription(String desc){
        this.deckDescription = desc;
    }
    
    public String getType(){
        return "Mazo ";
    }
    
    public void addChild(CompositeElement child){
        this.children.add(child);
    }
    
    public void addFlashcard(Flashcard flashcard){
        this.children.add(flashcard);
    }
    
    public CompositeElement getParent(){
        return this.parent;
    }
    
    public void setParent(CompositeElement element){
        this.parent = element;
    }
    
    public List<Flashcard> getOnlyFlashcards() {
        List<Flashcard> flashcards = new ArrayList<>();
        for (Object child : children) { // List from the Composite pattern
            if (child instanceof Flashcard && !(child instanceof Deck)) {
                flashcards.add((Flashcard) child);
            }
        }
        return flashcards;
    }
    
    public void removeChild(CompositeElement child) {
        children.remove(child);
    }
}
