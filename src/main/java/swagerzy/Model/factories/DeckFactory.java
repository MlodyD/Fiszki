package swagerzy.Model.factories;

import swagerzy.Model.composite.Deck;

/**
 *
 * @author maciejdaszkiewicz
 */
public class DeckFactory {
    
    public Deck CreateDeck(String deckName, String deckDescription){
        return new Deck(deckName, deckDescription);
    }
    
}
