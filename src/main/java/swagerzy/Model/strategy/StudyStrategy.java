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
public interface StudyStrategy {
    List<Flashcard> getOrderedCards(List<Flashcard> cards);
}
