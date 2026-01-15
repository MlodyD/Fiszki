package swagerzy.los_peces;

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
            new FileChooser.ExtensionFilter("Pliki talii (*.csv, *.json)", "*.csv", "*.json")
        );
        
        
        Stage stage = (Stage) pathField.getScene().getWindow();
        File file = fileChooser.showOpenDialog(stage);

        if (file != null) {
            this.selectedFile = file;
            pathField.setText(file.getAbsolutePath());
            statusLabel.setText("");
        }
    }
    // 2. Akcja przycisku "Importuj"
    @FXML
    private void handleImport() {
        if (selectedFile == null) {
            statusLabel.setText("Tienes que elegir el fichero!");
            return;
        }

        try {
            
            DeckImporter importer = DeckImporterFactory.getImporter(selectedFile);
            Deck newDeck = importer.importDeck(selectedFile);

            // --- DODANIE DO MODELU ---
            DeckManager manager = DeckManager.getInstance();
            Deck currentParent = manager.getCurrentDeck();

            if (currentParent != null) {    //dodawanie do srodka jakiegos decku ale nwm czy to biore pod uwage
                
                currentParent.addChild(newDeck);
                
            } else {
                
                manager.getDecks().add(newDeck); 
                
            }

            System.out.println("Sukces! Zaimportowano: " + newDeck.getFront());
            
            App.setRoot("MainMenu");

        } catch (Exception e) {
            statusLabel.setText("Błąd importu: " + e.getMessage());
            e.printStackTrace();
        }
    }

    // 3. Akcja przycisku "Anuluj"
    @FXML
    private void handleBack() throws IOException {
        DeckManager.getInstance().goToView();
    }
}