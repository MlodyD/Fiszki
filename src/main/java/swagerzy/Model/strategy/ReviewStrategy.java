/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package swagerzy.Model.strategy;

import java.util.ArrayList;
import java.util.List;
import swagerzy.Model.composite.CompositeElement;
import swagerzy.Model.composite.Flashcard;

/**
 *
 * @author igawl
 */
public class ReviewStrategy implements StudyStrategy {
    @Override
    public List<Flashcard> getOrderedCards(List<Flashcard> allElements) {
        List<Flashcard> difficultCards = new ArrayList<>();
        for (CompositeElement e : allElements) {
            if (e instanceof Flashcard) {
                Flashcard f = (Flashcard) e;
                // Only the difficult flashcards or the ones that haven't been attempted yet
                if (f.getSuccessRate() < 0.5 || f.getTotalAttempts() == 0) {
                    difficultCards.add(f);
                }
            }
        }
        return difficultCards;
    }
}
