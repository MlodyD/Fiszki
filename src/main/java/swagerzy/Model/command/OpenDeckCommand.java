package swagerzy.Model.command;

import java.io.IOException;
import swagerzy.Model.DeckManager;
import swagerzy.Model.composite.Deck;
import swagerzy.los_peces.App;

/**
 *
 * @author maciejdaszkiewicz
 */
public class OpenDeckCommand implements Command {
    private Deck deck; // To jest Twoja "referencja"

    public OpenDeckCommand(Deck deck) {
        this.deck = deck;
    }

    @Override
    public void execute(){
        DeckManager manager = DeckManager.getInstance();
        manager.setCurrentDeck(deck);
        
        App.switchView("DeckView");
    }
}