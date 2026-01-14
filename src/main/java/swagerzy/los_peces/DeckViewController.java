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
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.VBox;
import swagerzy.Model.composite.Deck;
import swagerzy.Model.DeckManager;
import swagerzy.Model.command.Command;
import swagerzy.Model.command.OpenDeckCommand;
import swagerzy.Model.command.OpenFlashcardCommand;
import swagerzy.Model.composite.CompositeElement;
import swagerzy.Model.composite.Flashcard;

public class DeckViewController extends Controller implements Initializable {

    @FXML
    private VBox vbox;
    @FXML
    private ScrollPane sp;
    @FXML
    private Label DeckTitle;
    @FXML
    private Label DeckDescription;

    // ..

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        this.deckManager = DeckManager.getInstance();
        
        Deck current = deckManager.getCurrentDeck();

        if (current != null) {
            DeckTitle.setText(current.getFront());
            DeckDescription.setText(current.getDescription());

            // 4. Dopiero teraz bezpiecznie pobieramy elementy
            List<CompositeElement> elementList = current.getChildren();

            for (CompositeElement currentElement : elementList) {
                Button btn = new Button(currentElement.getType() + currentElement.getFront());
                btn.getStyleClass().add("deck-list-button");
                
                Command openCommand;
                
                if (currentElement instanceof Deck){
                    openCommand = new OpenDeckCommand((Deck) currentElement);
                }
                else {
                    openCommand = new OpenFlashcardCommand((Flashcard) currentElement);
                }

            // 2. W akcji przycisku po prostu wywołujesz execute()
            btn.setOnAction(e -> {
                openCommand.execute();
            });

                vbox.getChildren().add(btn);
            }
        } else {
            App.switchView("MainMenu");
        }
    }
    
    @FXML
    private void goToCreateDeck() throws IOException {
        App.setRoot("CreateDeck");
    }
    
    @FXML
    private void goToCreateFlashcard() throws IOException {
        App.switchView("CreateFlashcard");
    }

}
