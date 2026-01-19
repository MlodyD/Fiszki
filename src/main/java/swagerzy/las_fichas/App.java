package swagerzy.las_fichas;

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
import swagerzy.Model.memento.StudySessionMemento;

/**
 * Main application class for Las Fichas.
 * Handles the JavaFX stage and scene switching.
 */
public class App extends Application {

    private static Scene scene;

    @Override
    public void start(Stage stage) throws IOException {

        // Service initialization
        StorageService storage = new StorageService();
        DeckManager dm = DeckManager.getInstance();

        // Loading a library (Composite)
        List<Deck> loadedDecks = storage.loadLibrary();
        dm.setDecks(loadedDecks);

        // Loading a session (Memento)
        StudySessionMemento memento = storage.loadSessionMemento();
        if (memento != null) {
            dm.setMemento(memento);
        }

        // Setting the principal state
        dm.setCurrentDeck(null);

        // Loading the view
        FXMLLoader loader = new FXMLLoader(getClass().getResource("TitlePage.fxml"));
        Parent root = loader.load();

        scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("La App de Fichas");
        stage.setMaximized(true);
        stage.show();
    }
    
    /**
     * Changes the root FXML document for the current stage.
     * @param fxml The name of the FXML file.
     * @throws IOException If the FXML file is not found.
     */
    public static void setRoot(String fxml) throws IOException {
        scene.setRoot(loadFXML(fxml));
    }
    
    /**
     * Switches the view to a specific FXML and refreshes the scene.
     * @param viewName The name of the view to switch to.
     */
    public static void switchView(String viewName) {
        try {
            setRoot(viewName);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    private static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("/swagerzy/las_fichas/" + fxml + ".fxml"));
        return fxmlLoader.load();
    }
    
    @Override
    public void stop() {
        DeckManager dm = DeckManager.getInstance();
        StorageService storage = new StorageService();

        // Synchronizing the progress with map
        dm.updateCurrentDeckProgress();

        // Creating and saving memento
        StudySessionMemento memento = dm.createMemento();
        if (memento != null) {
            storage.saveSessionMemento(memento);
        }

        storage.saveLibrary(dm.getDecks());
    }
    
    /**
     * Application entry point.
     * @param args Command line arguments.
     */
    public static void main(String[] args) {
        launch();
    }

}