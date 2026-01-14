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
import swagerzy.Model.composite.CompositeElement;

public class DeckViewController extends Controller implements Initializable {

    @FXML
    private VBox vbox;
    @FXML
    private ScrollPane sp;
    @FXML
    private Label DeckTitle;

    // ..

    @Override
public void initialize(URL url, ResourceBundle rb) {
    // 1. Czyścimy widok (na wszelki wypadek)
    vbox.getChildren().clear();

    this.deckManager = DeckManager.getInstance();
    
    // 2. Pobieramy talię
    Deck current = deckManager.getCurrentDeck();
    
    // 3. Najpierw sprawdzamy czy talia istnieje!
    if (current != null) {
        DeckTitle.setText(current.getFront()); // Ustawiamy tytuł
        
        // 4. Dopiero teraz bezpiecznie pobieramy elementy
        List<CompositeElement> elementList = current.getChildren();

        for (CompositeElement currentElement : elementList) {
            Button btn = new Button(currentElement.getFront());
            btn.getStyleClass().add("deck-list-button");
            
            // Tu dodaj akcję przycisku, np. edycja karty
            btn.setOnAction(e -> { 
                System.out.println("Kliknięto element: " + currentElement.getFront());
            });

            vbox.getChildren().add(btn);
        }
    } else {
        // Obsługa błędu - np. gdy nie wybrano talii
        DeckTitle.setText("Błąd: Nie wybrano talii");
        System.err.println("DeckManager zwrócił null!");
    }
}
    
    @FXML
    private void goToCreateDeck() throws IOException {
        App.setRoot("CreateElement");
    }

}
