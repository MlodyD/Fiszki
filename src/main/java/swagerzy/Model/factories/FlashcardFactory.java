package swagerzy.Model.factories;

import swagerzy.Model.composite.CompositeElement;
import swagerzy.Model.composite.ImageFlashcard;
import swagerzy.Model.composite.TextFlashcard;

/**
 *
 * @author maciejdaszkiewicz
 */
/**
 * Factory class responsible for creating different types of Flashcards.
 * Encapsulates instantiation logic to decouple the UI from specific card subclasses.
 */
public class FlashcardFactory {
    
    public TextFlashcard createTextFlashcard(String question, String answer) {
        return new TextFlashcard(question, answer);
    }
    
    public TextFlashcard createTextFlashcard(String question, String answer, CompositeElement parent) {
        TextFlashcard tf = new TextFlashcard(question, answer, parent);
        return tf;
    }
    
    /**
     * Creates a new instance of an Image-based flashcard.
     * @param imagePath Path to the image file.
     * @param answer The translation or answer for the card.
     * @return A fully initialized ImageFlashcard.
     */
    public ImageFlashcard createImageFlashcard(String imagePath, String answer) {
        return new ImageFlashcard(imagePath, answer);
    }
    
    public ImageFlashcard createImageFlashcard(String imagePath, String answer, CompositeElement parent) {
        ImageFlashcard ifc = new ImageFlashcard(imagePath, answer);
        ifc.setParent(parent);
        return ifc;
    }
    
}
