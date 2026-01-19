/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package swagerzy.Model.strategy;

import swagerzy.Model.composite.Flashcard;
import java.util.List;

/**
 *
 * @author igawl
 */
/**
 * Strategy interface defining the algorithm for sorting/filtering flashcards.
 */
public interface StudyStrategy {
    /**
    * Defines the algorithm for ordering flashcards in a study session.
    * @param cards The list of flashcards to be processed.
    * @return A list of flashcards ordered according to the specific strategy.
    */
    List<Flashcard> getOrderedCards(List<Flashcard> cards);
}
