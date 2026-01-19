/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package swagerzy.las_fichas;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.ResourceBundle;
import javafx.fxml.Initializable;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import swagerzy.Model.DeckManager;
import swagerzy.Model.composite.Deck;
import swagerzy.Model.composite.Flashcard;
import swagerzy.Model.composite.ImageFlashcard;
import swagerzy.Model.factories.FlashcardFactory;

/**
 * FXML Controller class
 *
 * @author maciejdaszkiewicz
 */
public class CreateFlashcardController extends Controller implements Initializable {
    
    Deck currentDeck;
    private String selectedImagePath;
    private FlashcardFactory factory = new FlashcardFactory();
    private Flashcard editingCard;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        this.deckManager = DeckManager.getInstance();
        this.currentDeck = this.deckManager.getCurrentDeck();
        this.editingCard = this.deckManager.getCurrentFlashcard();

        if (editingCard != null) {
            // Setting all flashcard data
            answerField.setText(editingCard.getBack());
            
            if (editingCard instanceof ImageFlashcard) {
                ImageFlashcard ifc = (ImageFlashcard) editingCard;
                File imgFile = new File(ifc.getImagePath());
                if (imgFile.exists()) {
                    selectedImageFile = imgFile;
                    fileNameLabel.setText(imgFile.getName());
                    imagePreview.setImage(new Image(imgFile.toURI().toString()));
                    questionField.setDisable(true);
                }
            } else {
                questionField.setText(editingCard.getFront());
            }
        }
    }    
    
    @FXML
    private TextField questionField;

    @FXML
    private TextArea answerField;
    
    @FXML
    private Label fileNameLabel;
    
    @FXML
    private ImageView imagePreview;

    private File selectedImageFile;
    
    @FXML
    private void createFlashcard() {
        try {
            String answer = answerField.getText();
            
            if (editingCard != null) {
                // Editing existing flashcard
                editingCard.setBack(answer);
                
                if (editingCard instanceof ImageFlashcard) {
                    if (selectedImageFile != null) {
                        File imageDir = new File("data/images");
                        if (!imageDir.exists()) imageDir.mkdirs();
                        File destFile = new File(imageDir, selectedImageFile.getName());
                        
                        // Copying the file if it doesn't exist yet in our folder
                        if (!selectedImageFile.getAbsolutePath().equals(destFile.getAbsolutePath())) {
                            Files.copy(selectedImageFile.toPath(), destFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
                        }
                        ((ImageFlashcard) editingCard).setImagePath(destFile.getPath());
                    }
                } else {
                    editingCard.setFront(questionField.getText());
                }
                
                // Clearing the edition after saving
                deckManager.setCurrentFlashcard(null);

            } else {
                // Creating a new flashcard
                Flashcard nuevaFicha;
                if (selectedImageFile != null) {
                    File imageDir = new File("data/images");
                    if (!imageDir.exists()) imageDir.mkdirs();
                    File destFile = new File(imageDir, selectedImageFile.getName());
                    Files.copy(selectedImageFile.toPath(), destFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
                    nuevaFicha = factory.createImageFlashcard(destFile.getPath(), answer, currentDeck);
                } else {
                    nuevaFicha = factory.createTextFlashcard(questionField.getText(), answer, currentDeck);
                }
                currentDeck.addChild(nuevaFicha);
            }

            deckManager.saveLibrary();
            goBack();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    @FXML
    private void goBack(){
        DeckManager.getInstance().setCurrentFlashcard(null);
        DeckManager.getInstance().goToView();
    }
    
    @FXML
    private void handleSelectImage() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Seleccionar Imagen para la Ficha");
        fileChooser.getExtensionFilters().addAll(
            new FileChooser.ExtensionFilter("Imágenes", "*.png", "*.jpg", "*.gif")
        );

        selectedImageFile = fileChooser.showOpenDialog(null);

        if (selectedImageFile != null) {
            fileNameLabel.setText(selectedImageFile.getName());
            Image img = new Image(selectedImageFile.toURI().toString());
            imagePreview.setImage(img);

            // Unactivating the text field if the image field in not empty
            questionField.setDisable(true);
            questionField.setText(""); 
        }
    }
    
    @FXML
    private void handleSave() {
        String answer = answerField.getText();
        Flashcard nuevaFicha;

        if (selectedImagePath != null && !selectedImagePath.isEmpty()) {
            nuevaFicha = factory.createImageFlashcard(selectedImagePath, answer, currentDeck);
        } else {
            String question = questionField.getText();
            nuevaFicha = factory.createTextFlashcard(question, answer, currentDeck);
        }

        currentDeck.addChild(nuevaFicha);
        DeckManager.getInstance().saveLibrary();
    }
    
}
