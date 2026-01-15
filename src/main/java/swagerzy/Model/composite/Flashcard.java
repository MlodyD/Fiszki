package swagerzy.Model.composite;

import swagerzy.Model.composite.CompositeElement;

public abstract class Flashcard implements CompositeElement {
     
    protected String id;
    protected int level; 
    protected transient CompositeElement parent;
    
    public String getId(){
        return this.id;
    }
    
    public Flashcard(){
        this.id = java.util.UUID.randomUUID().toString();
    }
    
    @Override
    public CompositeElement getParent(){
        return this.parent;
    }
    
    
    public int getLevel() {
        return this.level;
    }

    public void setLevel(int level) {
        this.level = level;
    }
    
}
