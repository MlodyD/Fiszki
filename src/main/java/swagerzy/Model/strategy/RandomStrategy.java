/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package swagerzy.Model.strategy;
import swagerzy.Model.composite.Flashcard;
import java.util.Collections;
import java.util.ArrayList;
import java.util.List;
/**
 *
 * @author igawl
 */
public class RandomStrategy implements StudyStrategy {
    @Override
    public List<Flashcard> getOrderedCards(List<Flashcard> cards) {
        List<Flashcard> shuffled = new ArrayList<>(cards);
        Collections.shuffle(shuffled);
        return shuffled;
    }
}
