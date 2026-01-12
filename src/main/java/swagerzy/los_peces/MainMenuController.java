/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package swagerzy.los_peces;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.VBox;
import swagerzy.Model.Deck;
import swagerzy.Model.DeckManager;

public class MainMenuController extends Controller implements Initializable {

    @FXML
    private VBox vbox; // Upewnij się, że masz to fx:id w SceneBuilderze!
    @FXML
    private ScrollPane sp; // To też się przyda do debugowania

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        this.deckManager = DeckManager.getInstance();
        List<Deck> decksList = deckManager.getDecks();
        
        // W pętli for:
        for (Deck currentDeck : decksList) {
            Button btn = new Button(currentDeck.getDeckName());

            // Dodaj TYLKO klasę CSS (nie rób tu żadnego setMaxWidth!)
            btn.getStyleClass().add("deck-list-button");

            // Akcja
            btn.setOnAction(e -> { /* Twoja akcja */ });

            vbox.getChildren().add(btn);
        }
    }
    
    @FXML
    private void goToCreateDeck() throws IOException {
        App.setRoot("CreateDeck");
    }

}
