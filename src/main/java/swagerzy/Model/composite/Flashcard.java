package swagerzy.Model.composite;

import swagerzy.Model.composite.CompositeElement;

public abstract class Flashcard implements CompositeElement {
     
    protected transient final String id = java.util.UUID.randomUUID().toString();
    protected int level; 
    protected transient CompositeElement parent;
    
    public String getId(){
        return this.id;
    }
    
    public Flashcard(){
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
