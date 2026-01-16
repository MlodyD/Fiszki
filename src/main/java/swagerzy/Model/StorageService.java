package swagerzy.Model;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import swagerzy.Model.Adapter.JSONDeckImporter;
import swagerzy.Model.composite.CompositeElement;
import swagerzy.Model.composite.Deck;

public class StorageService {
    
    private static final String DIR_NAME = ".los_peces";
    private static final String FILE_NAME = "library.json";

    private File getLibraryFile() {
        String userHome = System.getProperty("user.home");
        File dir = new File(userHome, DIR_NAME);
        if (!dir.exists()) dir.mkdirs();
        return new File(dir, FILE_NAME);
    }

    public void saveLibrary(List<Deck> topLevelDecks) {
        File file = getLibraryFile();
        Gson gson = new GsonBuilder().setPrettyPrinting().create();

        Deck virtualRoot = new Deck("LIBRARY_ROOT", "Storage Container", null);

        for (Deck d : topLevelDecks) {
            virtualRoot.addChild(d);
        }

        try (FileWriter writer = new FileWriter(file)) {
            gson.toJson(virtualRoot, writer);
            System.out.println("Zapisano bibliotekę (" + topLevelDecks.size() + " talii) do pliku.");
        } catch (IOException e) {
            System.err.println("Błąd zapisu: " + e.getMessage());
        }
    }

    /**
     * WCZYTYWANIE: Zwraca Listę Talii (dla DeckManagera)
     */
    public List<Deck> loadLibrary() {
        File file = getLibraryFile();

        // Domyślnie pusta lista
        if (!file.exists()) {
            return new ArrayList<>();
        }

        try {
            JSONDeckImporter importer = new JSONDeckImporter();
            
            // 1. Wczytujemy Wirtualny Root z pliku
            Deck loadedRoot = importer.importDeck(file);
            
            // 2. Wyciągamy z niego "wnętrzności" (czyli Twoje talie)
            List<Deck> resultList = new ArrayList<>();
            
            for (CompositeElement child : loadedRoot.getChildren()) {
                if (child instanceof Deck) {
                    Deck d = (Deck) child;
                    // Resetujemy rodzica na null, bo w DeckManagerze
                    // talie najwyższego poziomu nie mają rodzica.
                    d.setParent((Deck) null); 
                    resultList.add(d);
                }
            }
            
            return resultList;

        } catch (Exception e) {
            System.err.println("Błąd wczytywania: " + e.getMessage());
            return new ArrayList<>(); // Zwracamy pustą listę w razie błędu
        }
    }
}