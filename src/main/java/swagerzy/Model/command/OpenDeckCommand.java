package swagerzy.Model.command;

import swagerzy.Model.DeckManager;
import swagerzy.Model.composite.Deck;
import swagerzy.las_fichas.App;

/**
 *
 * @author maciejdaszkiewicz
 */
public class OpenDeckCommand implements Command {
    private Deck deck;

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