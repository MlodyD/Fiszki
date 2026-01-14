package swagerzy.Model.composite;

import swagerzy.Model.composite.CompositeElement;

public abstract class Flashcard implements CompositeElement {
     
    private String id;
    private int level; 
    private CompositeElement parent;
    
    public String getId(){
        return this.id;
    }
    
    public Flashcard(){
        this.id = java.util.UUID.randomUUID().toString();
    }
    
    
    public int getLevel() {
        return this.level;
    }

    public void setLevel(int level) {
        this.level = level;
    }
    
    @Override
    public CompositeElement getParent(){
        return this.parent;
    }
}
