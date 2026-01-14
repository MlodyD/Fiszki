package swagerzy.Model.factories;

import swagerzy.Model.composite.TextFlashcard;

/**
 *
 * @author maciejdaszkiewicz
 */
public class TextFlashcardFactory {
    
    public TextFlashcard CreateCompositeElement(){
        TextFlashcard flashcard = new TextFlashcard();
        return flashcard;
    }
    
}
