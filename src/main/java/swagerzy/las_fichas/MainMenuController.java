/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package swagerzy.las_fichas;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import swagerzy.Model.composite.Deck;
import swagerzy.Model.DeckManager;
import swagerzy.Model.command.Command;
import swagerzy.Model.command.OpenDeckCommand;

public class MainMenuController extends Controller implements Initializable {

    @FXML
    private VBox vbox;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        this.deckManager = DeckManager.getInstance();
        refreshMenu();
    }

    private void refreshMenu() {
        vbox.getChildren().clear(); // Clearing the view
        List<Deck> decksList = DeckManager.getInstance().getDecks();

        for (Deck currentDeck : decksList) {
            HBox row = new HBox(10);
            row.setAlignment(Pos.CENTER_LEFT);

            // Decks buttons
            Button btn = new Button(currentDeck.getType() + ": " + currentDeck.getFront());
            btn.getStyleClass().add("deck-list-button");
            btn.setMaxWidth(Double.MAX_VALUE);
            HBox.setHgrow(btn, Priority.ALWAYS);

            Command openCommand = new OpenDeckCommand(currentDeck);
            btn.setOnAction(e -> openCommand.execute());

            // Delete button
            Button deleteBtn = new Button("X");
            deleteBtn.getStyleClass().add("delete-button");
            deleteBtn.setStyle("-fx-text-fill: red; -fx-font-weight: bold; -fx-cursor: hand;");
            
            deleteBtn.setOnAction(e -> {
                DeckManager.getInstance().deleteDeck(currentDeck);
                refreshMenu();
            });

            row.getChildren().addAll(btn, deleteBtn);
            vbox.getChildren().add(row);
        }
    }
    
    @FXML
    private void goToCreateDeck() throws IOException {
        App.setRoot("CreateDeck");
    }
    
    @FXML
    private void ImportDeck() throws IOException {
        App.setRoot("ImportDeck");
    }

}
