package swagerzy.Model.factories;

import swagerzy.Model.composite.CompositeElement;
import swagerzy.Model.composite.TextFlashcard;

/**
 *
 * @author maciejdaszkiewicz
 */
public class TextFlashcardFactory {
    
    public TextFlashcard CreateFlashcard(String question, String answer){
        return new TextFlashcard(question, answer);
    }
    
    public TextFlashcard CreateFlashcard(String question, String answer, CompositeElement parent){
        return new TextFlashcard(question, answer, parent);
    }
    
}
