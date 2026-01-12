/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package swagerzy.los_peces;

import swagerzy.Model.DeckManager;

/**
 *
 * @author maciejdaszkiewicz
 */
public abstract class Controller {
    protected DeckManager deckManager;
    
    public void setDeckManager(DeckManager dm) {
        this.deckManager = dm;
    }
    
    public DeckManager getDeckManager(){
        return this.deckManager;
    }
    
}
