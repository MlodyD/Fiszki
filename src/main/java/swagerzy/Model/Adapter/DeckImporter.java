/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package swagerzy.Model.Adapter;

import java.io.File;
import swagerzy.Model.composite.Deck;

/**
 *
 * @author maciejdaszkiewicz
 */
public interface DeckImporter {
    public Deck importDeck(File filePath) throws Exception;
}
