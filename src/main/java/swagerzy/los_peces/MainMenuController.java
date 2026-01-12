/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package swagerzy.los_peces;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.VBox;

/**
 * FXML Controller class
 *
 * @author maciejdaszkiewicz
 */
public class MainMenuController implements Initializable {

    @FXML
    private VBox vbox; // Upewnij się, że masz to fx:id w SceneBuilderze!
    @FXML
    private ScrollPane sp; // To też się przyda do debugowania

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // Tu wrzucasz kod testowy:
        System.out.println("--- START TESTU ---");

        // 1. Sprawdzamy czy VBox w ogóle rośnie
        vbox.heightProperty().addListener((obs, oldVal, newVal) -> {
            System.out.println("VBox height changed: " + newVal);
        });

        // 2. Dodajemy na chama 50 przycisków, żeby wymusić przewijanie
        for (int i = 1; i <= 50; i++) {
            Button btn = new Button("Fiszka nr " + i);
            
            System.out.println("dodano przycisk: " + i);
            btn.setMinHeight(50); // Dajemy im wysokość, żeby na pewno zajęły dużo miejsca
            vbox.getChildren().add(btn);
        }
        
        System.out.println("--- KONIEC TESTU ---");
    }
    
    @FXML
    private void goToCreateDeck() throws IOException {
        App.setRoot("CreateDeck");
    }

}
