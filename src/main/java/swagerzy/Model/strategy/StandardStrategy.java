/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package swagerzy.Model.strategy;

import java.util.ArrayList;
import java.util.List;
import swagerzy.Model.composite.Flashcard;

/**
 *
 * @author igawl
 */
public class StandardStrategy implements StudyStrategy {
    @Override
    public List<Flashcard> getOrderedCards(List<Flashcard> cards) {
        return new ArrayList<>(cards); // original order
    }
}
