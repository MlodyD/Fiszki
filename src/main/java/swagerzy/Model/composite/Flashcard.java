package swagerzy.Model.composite;

public abstract class Flashcard implements CompositeElement {
     
    protected String id = java.util.UUID.randomUUID().toString();
    protected transient CompositeElement parent;
    protected String flashcardType;
    protected String front;
    protected String back;
    protected int correctCount = 0;
    protected int totalAttempts = 0;

    public Flashcard(){
    }

    public void recordAttempt(boolean isCorrect) {
        this.totalAttempts++;
        if (isCorrect) {
            this.correctCount++;
        }
    }

    public double getSuccessRate() {
        return totalAttempts == 0 ? 0 : (double) correctCount / totalAttempts;
    }

    public String getId(){
        return this.id;
    }

    @Override
    public CompositeElement getParent(){
        return this.parent;
    }
    
    public void setParent(CompositeElement parent) {
        this.parent = parent;
    }
    
    public void setFront(String front) {
        this.front = front;
    }
    
    public String getBack() {
        return this.back;
    }

    public void setBack(String back) {
        this.back = back;
    }

    public int getCorrectCount() {
        return correctCount;
    }

    public int getTotalAttempts() {
        return totalAttempts;
    }
}