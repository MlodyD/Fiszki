/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package swagerzy.Model.command;

import swagerzy.Model.DeckManager;
import swagerzy.Model.composite.Deck;
import swagerzy.Model.composite.Flashcard;
import swagerzy.los_peces.App;

/**
 *
 * @author maciejdaszkiewicz
 */
public class OpenFlashcardCommand implements Command{
    private Flashcard flashcard;
    
    public OpenFlashcardCommand(Flashcard flashcard) {
        this.flashcard = flashcard;
    }

    @Override
    public void execute(){
        //TODO
        DeckManager manager = DeckManager.getInstance();
        //manager.setCurrentDeck(flashcard);
        
        App.switchView("DeckView");
    }
}
