package swagerzy.Model;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonObject;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import swagerzy.Model.composite.CompositeElement;
import swagerzy.Model.composite.Deck;
import swagerzy.Model.composite.Flashcard;
import swagerzy.Model.composite.ImageFlashcard;
import swagerzy.Model.composite.TextFlashcard;
import swagerzy.Model.memento.StudySessionMemento;

public class StorageService {
    
    private static final String DIR_NAME = ".las_fichas";
    private static final String LIBRARY_FILE = "library.json";
    private static final String SESSION_FILE = "session.json";
    private final Gson gson;

    public StorageService() {
        // Initializing our JSON engine
        this.gson = getGson();
    }
    
    // Returns a path to the application folder in users home folder
    private File getAppDataDir() {
        String userHome = System.getProperty("user.home");
        File dir = new File(userHome, DIR_NAME);
        if (!dir.exists()) {
            dir.mkdirs();
        }
        return dir;
    }

    // Logic of a library (Composite)
    public void saveLibrary(List<Deck> topLevelDecks) {
        File file = new File(getAppDataDir(), LIBRARY_FILE);
        
        // Creating a virtual box to save all decks in one file
        Deck virtualRoot = new Deck("LIBRARY_ROOT", "Storage Container", null);
        for (Deck d : topLevelDecks) {
            virtualRoot.addChild(d);
        }

        try (FileWriter writer = new FileWriter(file)) {
            gson.toJson(virtualRoot, writer);
        } catch (IOException e) {
        }
    }

    public List<Deck> loadLibrary() {
        File file = new File(getAppDataDir(), LIBRARY_FILE);

        if (!file.exists()) {
            return new ArrayList<>();
        }

        try (FileReader reader = new FileReader(file)){
 
            Deck loadedRoot = gson.fromJson(reader, Deck.class);
            
            List<Deck> resultList = new ArrayList<>();
            if (loadedRoot != null && loadedRoot.getChildren() != null) {
                for (CompositeElement child : loadedRoot.getChildren()) {
                    if (child instanceof Deck) {
                        Deck d = (Deck) child;
                        d.setParent(null); // Main decks don't have parents
                        resultList.add(d);
                    }
                }
            }
            return resultList;
        } catch (Exception e) {
            return new ArrayList<>();
        }
    }

    // Session logic (Memento)
    public void saveSessionMemento(StudySessionMemento memento) {
        File file = new File(getAppDataDir(), SESSION_FILE);
        try (FileWriter writer = new FileWriter(file)) {
            gson.toJson(memento, writer);
        } catch (IOException e) {
        }
    }

    public StudySessionMemento loadSessionMemento() {
        File file = new File(getAppDataDir(), SESSION_FILE);
        if (!file.exists()) {
            return null;
        }
        
        try (FileReader reader = new FileReader(file)) {
            return gson.fromJson(reader, StudySessionMemento.class);
        } catch (IOException e) {
            return null;
        }
    }
    
    public static Gson getGson() {
        GsonBuilder builder = new GsonBuilder();

        // Creating an adapter, that will recognize the class (flashcard or a deck)
        JsonDeserializer<CompositeElement> deserializer = (json, typeOfT, context) -> {
            JsonObject jsonObject = json.getAsJsonObject();

            // Checking if it's a deck
            if (jsonObject.has("children") || jsonObject.has("name") && !jsonObject.has("type")) {
                return context.deserialize(json, Deck.class);
            }

            // Getting the flashcardType field for flashcards
            String type = "TEXT";
            if (jsonObject.has("flashcardType") && !jsonObject.get("flashcardType").isJsonNull()) {
                type = jsonObject.get("flashcardType").getAsString();
            }

            // Decision on the type
            if ("IMAGE".equals(type)) {
                return context.deserialize(json, ImageFlashcard.class);
            } else if ("DECK".equals(type)) {
                return context.deserialize(json, Deck.class);
            } else {
                return context.deserialize(json, TextFlashcard.class);
            }
        };

        builder.registerTypeAdapter(CompositeElement.class, deserializer);
        builder.registerTypeAdapter(Flashcard.class, deserializer); 

        return builder.setPrettyPrinting().create();
    }
}