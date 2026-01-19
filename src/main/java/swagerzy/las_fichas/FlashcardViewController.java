/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package swagerzy.las_fichas;

import java.io.File;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import javafx.fxml.Initializable;
import swagerzy.Model.DeckManager;

    
import javafx.animation.RotateTransition;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.transform.Rotate;
import javafx.util.Duration;
import swagerzy.Model.StorageService;
import swagerzy.Model.composite.CompositeElement;
import swagerzy.Model.composite.Deck;
import swagerzy.Model.composite.Flashcard;
import swagerzy.Model.composite.ImageFlashcard;
import swagerzy.Model.composite.TextFlashcard;
import swagerzy.Model.observer.StudyObserver;


/**
 * FXML Controller class
 *
 * @author maciejdaszkiewicz
 */
public class FlashcardViewController implements Initializable, StudyObserver {

    private boolean isFrontVisible = true;
    private List<Flashcard> studySessionCards;
    private int currentIndex = 0;
    private boolean isFinished = false;
    
    // UI components from FXML
    @FXML
    private StackPane cardContainer;
    
    @FXML
    private VBox cardFront;

    @FXML
    private VBox cardBack;

    @FXML
    private Label frontText;
    
    @FXML
    private ImageView frontImageView;

    @FXML
    private Label backText;
    
    @FXML
    private Button backButton;

    @FXML 
    private ProgressBar progressBar;
    
    @FXML 
    private Label progressLabel;
    
    @FXML
    private Button nextButton;

    /**
    * Triggers the 3D flip animation for the flashcard.
    */
    public void flipCard() {
        RotateTransition rotator = new RotateTransition(Duration.millis(500), cardContainer);
        rotator.setAxis(Rotate.Y_AXIS);
        if (isFrontVisible) {
            // Flipping from front to back
            rotator.setFromAngle(0);
            rotator.setToAngle(180); 
        } else {
            // Flipping from back to front
            rotator.setFromAngle(180);
            rotator.setToAngle(360);
        }
        RotateTransition firstHalf = new RotateTransition(Duration.millis(250), cardContainer);
        firstHalf.setAxis(Rotate.Y_AXIS);
        firstHalf.setFromAngle(0);
        firstHalf.setToAngle(90);

        firstHalf.setOnFinished(e -> {
            // Toggle visibility at 90 degrees for a 3D effect
            if (isFrontVisible) {
                cardFront.setVisible(false);
                cardBack.setVisible(true);
            } else {
                cardFront.setVisible(true);
                cardBack.setVisible(false);
            }
            isFrontVisible = !isFrontVisible;

            // Second half of the turn
            RotateTransition secondHalf = new RotateTransition(Duration.millis(250), cardContainer);
            secondHalf.setAxis(Rotate.Y_AXIS);
            secondHalf.setFromAngle(90);
            secondHalf.setToAngle(0);
            secondHalf.play();
        });
    
        firstHalf.play();
    }

    public void changeBackButtonName(){
        Deck current = DeckManager.getInstance().getCurrentDeck();

        backButton.setText("← " + current.getFront());
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        changeBackButtonName();
        DeckManager deckManager = DeckManager.getInstance();
        this.studySessionCards = deckManager.getCardsForCurrentSession();
        deckManager.addObserver(this);

        if (studySessionCards != null && !studySessionCards.isEmpty()) {
            this.currentIndex = deckManager.getCurrentIndex(); 
            displayCard(currentIndex); 
            updateProgress(currentIndex + 1, studySessionCards.size());
        }
    } 
    
    @Override
    public void updateProgress(int current, int total) {
        if (total > 0) {
            double progress = (double) current / total;
            progressBar.setProgress(progress);

            // Completion rate
            int percent = (int) (progress * 100);
            progressLabel.setText("Progreso: " + percent + "% | Ficha " + current + " de " + total);
        }
    }
    
    private void displayCard(int index) {
        isFrontVisible = true;
        cardFront.setVisible(true);
        cardBack.setVisible(false);
        cardContainer.setRotate(0);
        CompositeElement element = studySessionCards.get(index);

        // Reset of visibility on start
        frontText.setVisible(false);
        frontImageView.setVisible(false);

        if (element instanceof ImageFlashcard) {
            frontText.setVisible(false);
            frontText.setManaged(false);
            ImageFlashcard ifc = (ImageFlashcard) element;
            frontImageView.setVisible(true);
            frontImageView.setManaged(true);
            // Loading an image
            File file = new File(ifc.getImagePath());
            if(file.exists()) {
                frontImageView.setImage(new Image(file.toURI().toString()));
            }
        } else {
            frontImageView.setVisible(false);
            frontImageView.setManaged(false);
            // If it's not an image we treat it like a text
            frontText.setVisible(true);
            frontText.setManaged(true);
            frontText.setText(element.getFront());
        }

        // Back od the card
        if (element instanceof TextFlashcard) {
            backText.setText(((TextFlashcard) element).getAnswer());
        } else if (element instanceof ImageFlashcard) {
            backText.setText(((ImageFlashcard) element).getAnswer());
        }
    }
    
    /**
    * Saves the current session and returns to the deck view.
    */
    @FXML
    public void goUp(){
        DeckManager dm = DeckManager.getInstance();
        
        if (dm.isPreviewMode()) {
            dm.restoreIndexAfterPreview();
            App.switchView("DeckView");
            return; 
        }

        dm.updateCurrentDeckProgress();
        StorageService storage = new StorageService();
        storage.saveSessionMemento(dm.createMemento()); // Saving to JSON

        App.switchView("DeckView");
    }
    
    @FXML
    private void handleNextCard() {
        DeckManager dm = DeckManager.getInstance();
        
        if (isFinished) {
            restartStudy();
            return;
        }

        if (studySessionCards != null && !studySessionCards.isEmpty()) {
            int lastIndex = studySessionCards.size() - 1;
            int currentIndexInManager = dm.getCurrentIndex();

            // If the current index is lower that the index of the last card, we continue
            if (currentIndexInManager < lastIndex) {
                int nextIndex = currentIndexInManager + 1;
                dm.setCurrentIndex(nextIndex);
                displayCard(nextIndex);
                dm.notifyObservers(nextIndex + 1, studySessionCards.size());
            } 
            // If we are on the last card and click continue, it shows the completion message
            else {
                showCompletionMessage();
            }
        }
    }
    
    private void showCompletionMessage() {
        isFinished = true;

        // Only the front is visible
        cardBack.setVisible(false);
        cardFront.setVisible(true);
        cardContainer.setRotate(0);
        frontImageView.setImage(null);
        frontImageView.setVisible(false);
        frontImageView.setManaged(false);
        backText.setText(""); 

        frontText.setVisible(true);
        frontText.setManaged(true);
        frontText.setText("¡Felicidades!");

        if (!cardFront.getChildren().isEmpty() && cardFront.getChildren().get(0) instanceof Label) {
            ((Label)cardFront.getChildren().get(0)).setText("Has terminado este mazo");
        }

        nextButton.setText("Empezar de nuevo");
    }
    
    private void restartStudy() {
        isFinished = false;
        DeckManager dm = DeckManager.getInstance();

        // Reset of the current index
        dm.setCurrentIndex(0);
        this.currentIndex = 0;
        
        dm.updateCurrentDeckProgress();
        StorageService storage = new StorageService();
        storage.saveSessionMemento(dm.createMemento());

        nextButton.setText("Ficha siguiente");
        ((Label)cardFront.getChildren().get(0)).setText("Pregunta");

        displayCard(0);
        dm.notifyObservers(1, studySessionCards.size());
    }
    
    @FXML
    private void handleCorrect() {
        updateFlashcardStats(true);
    }

    @FXML
    private void handleIncorrect() {
        updateFlashcardStats(false);
    }

    // Handles user clicking "Lo sabia" or "No lo sabia"
    private void updateFlashcardStats(boolean correct) {
        DeckManager dm = DeckManager.getInstance();
        int realIndex = dm.getCurrentIndex(); 

        if (studySessionCards != null && realIndex < studySessionCards.size()) {
            Flashcard current = (Flashcard) studySessionCards.get(realIndex);
            current.recordAttempt(correct);

            // Save to library.json
            dm.saveLibrary();

            handleNextCard();
        }
    }
}
