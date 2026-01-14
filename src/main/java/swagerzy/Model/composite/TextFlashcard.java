/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package swagerzy.Model.composite;

import swagerzy.Model.composite.Flashcard;

/**
 *
 * @author maciejdaszkiewicz
 */
public class TextFlashcard extends Flashcard {
    
     
    private String id;
    private String question;
    private String anwser;
    
    @Override
    public String getFront() {
        return question;
    }
    
    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getAnwser() {
        return anwser;
    }

    public void setAnwser(String anwser) {
        this.anwser = anwser;
    }

}
