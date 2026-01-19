/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package swagerzy.Model.composite;

/**
 *
 * @author maciejdaszkiewicz
 */
public class TextFlashcard extends Flashcard {
    
    public TextFlashcard(String q, String a){
        this.front = q;
        this.back = a;
        this.flashcardType = "TEXT";
    }
    
    public TextFlashcard(String q, String a, CompositeElement parent){
        this.front = q;
        this.back = a;
        this.parent = parent;
        this.flashcardType = "TEXT";
    }
    
    @Override
    public String getFront() {
        return front;
    }
    
    public String getQuestion() {
        return front;
    }

    public void setQuestion(String question) {
        this.front = question;
    }

    public String getAnswer() {
        return back;
    }

    public void setAnswer(String answer) {
        this.back = answer;
    }
    
    public String getType(){
        return "Ficha ";
    }

}
