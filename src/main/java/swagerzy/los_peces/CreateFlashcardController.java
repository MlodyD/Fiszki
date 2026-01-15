/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package swagerzy.los_peces;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.Initializable;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import swagerzy.Model.DeckManager;
import swagerzy.Model.composite.Deck;

/**
 * FXML Controller class
 *
 * @author maciejdaszkiewicz
 */
public class CreateFlashcardController extends Controller implements Initializable {
    
    Deck currentDeck;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        this.deckManager = DeckManager.getInstance();
        this.currentDeck = this.deckManager.getCurrentDeck();
    }    
    
    @FXML
    private TextField questionField;

    @FXML
    private TextArea answerField;
    
    @FXML
    private void createFlashcard() throws IOException {
        String question = questionField.getText();
        String answer = answerField.getText();

        // Walidacja (czy nie puste)
        if (question == null || question.trim().isEmpty() || answer == null || answer.trim().isEmpty()) {
            System.out.println("Error. El pez necesita pregunta y respuesta");
            return; // przerwij
        }
        
        this.deckManager.createTextFlashcard(question, answer);
        goBack();
    }
    
    @FXML
    private void goBack(){
        DeckManager.getInstance().goToView();
    }
    
}
