package swagerzy.Model.composite;

public class ImageFlashcard extends Flashcard {

    public ImageFlashcard(String imagePath, String answer) {
        this.front = imagePath;
        this.back = answer;
        this.flashcardType = "IMAGE";
    }

    @Override
    public String getFront() {
        return "[Imagen]";
    }
    
    @Override
    public String getType() {
        return "Ficha de imagen"; 
    }

    public String getImagePath() {
        return front;
    }
    
    public void setImagePath(String imagePath) {
        this.front = imagePath;
    }

    public String getAnswer() {
        return back;
    }
    
    @Override
    public void setBack(String back) {
        this.back = back;
    }
}
