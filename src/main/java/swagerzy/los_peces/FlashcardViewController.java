/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package swagerzy.los_peces;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.Initializable;
import swagerzy.Model.DeckManager;

    
import javafx.animation.RotateTransition;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.transform.Rotate;
import javafx.util.Duration;
import swagerzy.Model.composite.TextFlashcard;


/**
 * FXML Controller class
 *
 * @author maciejdaszkiewicz
 */
public class FlashcardViewController implements Initializable {

// ... wewnątrz klasy kontrolera

private boolean isFrontVisible = true;

 // To jest ten główny kontener (stos), którym będziemy kręcić
    @FXML
    private StackPane cardContainer;

    // To jest panel przedni (biały) - będziemy go ukrywać/pokazywać
    @FXML
    private VBox cardFront;

    // To jest panel tylny (kolorowy) - będziemy go ukrywać/pokazywać
    @FXML
    private VBox cardBack;

    // To jest ten napis na przodzie, gdzie wstawisz słówko (np. "Hola")
    @FXML
    private Label frontText;

    // To jest ten napis z tyłu, gdzie wstawisz tłumaczenie (np. "Cześć")
    @FXML
    private Label backText;




public void flipCard() {
    // 1. Wybierz, co obracamy (cały kontener albo konkretną stronę)
    // Najlepiej obracać cały StackPane, ale podmieniać zawartość w trakcie
    
    RotateTransition rotator = new RotateTransition(Duration.millis(500), cardContainer);
    rotator.setAxis(Rotate.Y_AXIS); // Obrót w osi Y (jak drzwi)
    
    if (isFrontVisible) {
        // Jesteśmy na przodzie, idziemy na tył
        rotator.setFromAngle(0);
        rotator.setToAngle(180); 
    } else {
        // Jesteśmy na tyle, wracamy na przód
        rotator.setFromAngle(180);
        rotator.setToAngle(360);
    }
    
    // NAJWAŻNIEJSZE: Moment "magii" w połowie animacji
    // Niestety JavaFX nie ma wbudowanego "backface culling" (chowania tyłu) w prostych Node'ach
    // Więc musimy ręcznie podmienić widoczność, gdy karta jest bokiem (90 stopni)
    
    // Prostsza wersja dla Ciebie (nie idealne 3D, ale działa):
    // Zrób to w dwóch krokach sekwencyjnych albo po prostu podmień widoczność:
    
    // Wersja uproszczona (oszukana), ale łatwa do zrobienia:
    RotateTransition firstHalf = new RotateTransition(Duration.millis(250), cardContainer);
    firstHalf.setAxis(Rotate.Y_AXIS);
    firstHalf.setFromAngle(0);
    firstHalf.setToAngle(90);
    
    firstHalf.setOnFinished(e -> {
        // Jesteśmy pod kątem 90 stopni (niewidoczni), podmieniamy treść
        if (isFrontVisible) {
            cardFront.setVisible(false);
            cardBack.setVisible(true);
        } else {
            cardFront.setVisible(true);
            cardBack.setVisible(false);
        }
        isFrontVisible = !isFrontVisible;
        
        // Druga połowa obrotu
        RotateTransition secondHalf = new RotateTransition(Duration.millis(250), cardContainer);
        secondHalf.setAxis(Rotate.Y_AXIS);
        secondHalf.setFromAngle(90);
        secondHalf.setToAngle(0); // Wracamy do 0, bo podmieniliśmy Node'y
        secondHalf.play();
    });
    
    firstHalf.play();
}

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        DeckManager deckManager = DeckManager.getInstance();
        
        TextFlashcard flashcard = (TextFlashcard) deckManager.getCurrentFlashcard();
        
        frontText.setText(flashcard.getFront());
        backText.setText(flashcard.getAnwser());
    }    
    
}
