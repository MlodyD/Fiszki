/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package swagerzy.Model.memento;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author igawl
 */
public class StudySessionMemento implements Serializable{
    private final Map<String, Integer> deckProgress;

    public StudySessionMemento(Map<String, Integer> progress) {
        this.deckProgress = new HashMap<>(progress);
    }

    public int getIndexForDeck(String deckName) {
        return deckProgress.getOrDefault(deckName, 0);
    }

    public Map<String, Integer> getAllProgress() {
        return new HashMap<>(deckProgress);
    }
}
