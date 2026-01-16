/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package swagerzy.Model.composite;

import com.google.gson.annotations.SerializedName;

/**
 *
 * @author maciejdaszkiewicz
 */
public class TextFlashcard extends Flashcard {
    
    @SerializedName("front")
    private String question;
    
    @SerializedName("back")
    private String anwser;
    
    public TextFlashcard(String q, String a){
        this.question = q;
        this.anwser = a;
    }
    
    public TextFlashcard(String q, String a, CompositeElement parent){
        this.question = q;
        this.anwser = a;
        this.parent = parent;
    }
    
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
    
    public String getType(){
        return "Pez: ";
    }

}
