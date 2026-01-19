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
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import swagerzy.Model.composite.Deck;
import swagerzy.Model.DeckManager;
import swagerzy.Model.command.Command;
import swagerzy.Model.command.OpenDeckCommand;
import swagerzy.Model.command.OpenFlashcardCommand;
import swagerzy.Model.composite.CompositeElement;
import swagerzy.Model.composite.Flashcard;
import swagerzy.Model.strategy.RandomStrategy;
import swagerzy.Model.strategy.ReviewStrategy;
import swagerzy.Model.strategy.StandardStrategy;

public class DeckViewController extends Controller implements Initializable {

    @FXML
    private VBox vbox;
    @FXML
    private Label DeckTitle;
    @FXML
    private Label DeckDescription;
    @FXML
    private Button backButton;
    @FXML
    private Button normalStudyBtn;
    @FXML
    private Button randomStudyBtn;
    @FXML
    private Button reviewStudyBtn;
    
    public void changeBackButtonName(){
        Deck current = DeckManager.getInstance().getCurrentDeck();
        
        if (current.getParent() != null)
        {
            backButton.setText("← " + current.getParent().getFront());
        }
        else {
            backButton.setText("← " + "Main Menu");
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        this.deckManager = DeckManager.getInstance();
        
        Deck current = deckManager.getCurrentDeck();

        if (current != null) {
            
            createButtons();
            changeBackButtonName();
            List<Flashcard> allFlashcards = current.getOnlyFlashcards(); 
        
            boolean hasAnyCards = !allFlashcards.isEmpty();

            // At least one card for normal and random study to work
            normalStudyBtn.setDisable(!hasAnyCards);
            randomStudyBtn.setDisable(!hasAnyCards);

            // Review study logic
            ReviewStrategy reviewChecker = new ReviewStrategy();
            List<Flashcard> difficultCards = reviewChecker.getOrderedCards(allFlashcards);

            // Review button only active, when there are difficult cards
            reviewStudyBtn.setDisable(difficultCards.isEmpty());
            
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

    @FXML
    private void goUp(){
        DeckManager.getInstance().goUp();
    }
    
    @FXML
    private void handleNormalStudy() {
        DeckManager.getInstance().setStrategy(new StandardStrategy());
        App.switchView("FlashcardView");
    }

    @FXML
    private void handleRandomStudy() {
        DeckManager.getInstance().setStrategy(new RandomStrategy());
        DeckManager.getInstance().setCurrentIndex(0);
        App.switchView("FlashcardView");
    }
    
    @FXML
    private void handleReviewStudy() {
        DeckManager.getInstance().setStrategy(new ReviewStrategy());
        DeckManager.getInstance().setCurrentIndex(0);
        App.switchView("FlashcardView");
    }
    
    public void refreshView() {
        vbox.getChildren().clear(); // Clearing old buttons
        createButtons();           // Drawing new buttons
        changeBackButtonName();
    }
    
    public void createButtons() {
        Deck current = DeckManager.getInstance().getCurrentDeck();
        DeckTitle.setText(current.getFront());
        DeckDescription.setText(current.getDescription());

        List<CompositeElement> elementList = current.getChildren();

        for (CompositeElement currentElement : elementList) {
            // Box for opening, editing and deleting
            javafx.scene.layout.HBox row = new javafx.scene.layout.HBox(10); 
            row.setAlignment(javafx.geometry.Pos.CENTER_LEFT);

            // Open card or deck button
            Button btn = new Button(currentElement.getType() + ": " + currentElement.getFront());
            btn.getStyleClass().add("deck-list-button");
            btn.setMaxWidth(Double.MAX_VALUE);
            javafx.scene.layout.HBox.setHgrow(btn, javafx.scene.layout.Priority.ALWAYS);

            Command openCommand;
            if (currentElement instanceof Deck) {
                openCommand = new OpenDeckCommand((Deck) currentElement);
            } else {
                openCommand = new OpenFlashcardCommand((Flashcard) currentElement);
            }
            btn.setOnAction(e -> openCommand.execute());
            row.getChildren().add(btn);
            
            // Edit button
            if (currentElement instanceof Flashcard) {
                Button editBtn = new Button("✎");
                editBtn.setStyle("-fx-text-fill: blue; -fx-cursor: hand;");
                editBtn.setOnAction(e -> {
                    deckManager.setCurrentFlashcard((Flashcard) currentElement);
                    App.switchView("CreateFlashcard");
                });
                row.getChildren().add(editBtn); // Only adding the edit button if it's a flashcard
            }

            // Delete button
            Button deleteBtn = new Button("X");
            deleteBtn.setStyle("-fx-text-fill: red; -fx-font-weight: bold; -fx-cursor: hand;");
            deleteBtn.setOnAction(e -> {
                if (currentElement instanceof Flashcard) {
                    // Deleting a file from the project folder
                    DeckManager.getInstance().deleteFlashcard(current, (Flashcard) currentElement);
                } else if (currentElement instanceof Deck) {
                    // If it's a deck, delete the entire deck with its contents
                    DeckManager.getInstance().deleteDeck((Deck) currentElement);
                }
                refreshView();
            });

            row.getChildren().add(deleteBtn);
            vbox.getChildren().add(row);
        }
    }
}