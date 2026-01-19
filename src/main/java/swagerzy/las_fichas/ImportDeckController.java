package swagerzy.las_fichas;

import java.io.File;
import java.io.IOException;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import swagerzy.Model.Adapter.DeckImporter;
import swagerzy.Model.DeckManager;
import swagerzy.Model.composite.Deck;
import swagerzy.Model.factories.DeckImporterFactory;

public class ImportDeckController extends Controller {

    @FXML
    private TextField pathField;
    
    @FXML
    private Label statusLabel;

    private File selectedFile;

    @FXML
    private void handleBrowse() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("seleccionar archivo");

        fileChooser.getExtensionFilters().add(
            new FileChooser.ExtensionFilter("Archivos de mazo (*.csv, *.json, *.txt)", "*.csv", "*.json", "*.txt")
        );
        
        Stage stage = (Stage) pathField.getScene().getWindow();
        File file = fileChooser.showOpenDialog(stage);

        if (file != null) {
            this.selectedFile = file;
            pathField.setText(file.getAbsolutePath());
            statusLabel.setText("");
        }
    }
    
    // Clicking on import button
    @FXML
    private void handleImport() {
        if (selectedFile == null) {
            statusLabel.setText("Tienes que elegir el fichero!");
            return;
        }

        try {
            
            DeckImporter importer = DeckImporterFactory.getImporter(selectedFile);
            Deck newDeck = importer.importDeck(selectedFile);

            DeckManager manager = DeckManager.getInstance();
            Deck currentParent = manager.getCurrentDeck();

            if (currentParent != null) {
                
                currentParent.addChild(newDeck);
                
            } else {
                
                manager.getDecks().add(newDeck); 
                
            }
            
            App.setRoot("MainMenu");

        } catch (Exception e) {
            statusLabel.setText("Error de import: " + e.getMessage());
            e.printStackTrace();
        }
    }

    // Clicking on cancelar button
    @FXML
    private void handleBack() throws IOException {
        DeckManager.getInstance().goToView();
    }
}