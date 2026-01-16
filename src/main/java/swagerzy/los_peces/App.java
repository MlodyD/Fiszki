package swagerzy.los_peces;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.List;
import swagerzy.Model.DeckManager;
import swagerzy.Model.StorageService;
import swagerzy.Model.composite.Deck;

/**
 * JavaFX App
 */
public class App extends Application {

    private static Scene scene;

    @Override
    public void start(Stage stage) throws IOException {

        
        // --- WCZYTYWANIE ---
        StorageService storage = new StorageService();
        List<Deck> loadedDecks = storage.loadLibrary();
        
        // Wrzucamy listę do Managera
        DeckManager.getInstance().setDecks(loadedDecks);
        
        // Ustawiamy widok na menu główne (currentDeck = null)
        DeckManager.getInstance().setCurrentDeck(null);
        
        // Twój loadFXML zwraca Parent/root
        FXMLLoader loader = new FXMLLoader(getClass().getResource("TitlePage.fxml"));
        
        Parent root = loader.load();

        scene = new Scene(root, 1600, 1200);
        stage.setScene(scene);
        stage.setTitle("La App de Peces");
        stage.show();
    }
    
    public static void setRoot(String fxml) throws IOException {
        scene.setRoot(loadFXML(fxml));
    }
    
    public static void switchView(String viewName) {
        try {
            setRoot(viewName);
        } catch (IOException e) {
            System.err.println("Błąd ładowania widoku: " + viewName);
            e.printStackTrace(); // <--- DODAJ TĘ LINIĘ !!!
        }
    }


    private static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("/swagerzy/los_peces/" + fxml + ".fxml"));
        return fxmlLoader.load();
    }
    
    @Override
    public void stop() {
        System.out.println("Zamykanie i zapisywanie...");
        
        // Pobieramy listę z Managera
        List<Deck> allDecks = DeckManager.getInstance().getDecks();
        
        // Zapisujemy listę
        StorageService storage = new StorageService();
        storage.saveLibrary(allDecks);
    }



    public static void main(String[] args) {
        launch();
    }

}