package swagerzy.los_peces;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import swagerzy.Model.DeckManager;

/**
 * JavaFX App
 */
public class App extends Application {

    private static Scene scene;

    @Override
    public void start(Stage stage) throws IOException {

        // Twój loadFXML zwraca Parent/root
        FXMLLoader loader = new FXMLLoader(getClass().getResource("TitlePage.fxml"));
        Parent root = loader.load();

        scene = new Scene(root, 640, 480);
        stage.setScene(scene);
        stage.setTitle("La App de Peces");
        stage.show();
    }

    static void setRoot(String fxml) throws IOException {
        scene.setRoot(loadFXML(fxml));
    }

    private static Parent loadFXML(String fxml) throws IOException {
    FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("/swagerzy/los_peces/" + fxml + ".fxml"));
    return fxmlLoader.load();
}



    public static void main(String[] args) {
        launch();
    }

}