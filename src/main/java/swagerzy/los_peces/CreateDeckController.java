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
import swagerzy.Model.factories.DeckFactory;
import swagerzy.Model.DeckManager;

/**
 * FXML Controller class
 *
 * @author maciejdaszkiewicz
 */
public class CreateDeckController extends Controller implements Initializable {

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        this.deckManager = DeckManager.getInstance();
    }    
    
    @FXML
    private TextField deckNameField;

    @FXML
    private TextArea deckDescriptionField;
    
    @FXML
    private void createDeck() throws IOException {
        
        String name = deckNameField.getText();
        String description = deckDescriptionField.getText();

        // Walidacja (czy nie puste)
        if (name == null || name.trim().isEmpty()) {
            System.out.println("Error. El deck necesita un nombre");
            return; // przerwij
        }
        
        this.deckManager.createDeck(name, description);
        App.setRoot("MainMenu");
    }
    
}
