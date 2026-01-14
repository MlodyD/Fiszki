package swagerzy.Model.factories;

import swagerzy.Model.composite.TextFlashcard;

/**
 *
 * @author maciejdaszkiewicz
 */
public class TextFlashcardFactory {
    
    public TextFlashcard CreateFlashcard(String question, String answer){
        return new TextFlashcard(question, answer);
    }
    
}
