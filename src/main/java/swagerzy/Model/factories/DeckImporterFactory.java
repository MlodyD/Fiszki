package swagerzy.Model.factories;

import java.io.File;
import swagerzy.Model.Adapter.CSVDeckImporter;
import swagerzy.Model.Adapter.DeckImporter;
import swagerzy.Model.Adapter.JSONDeckImporter;

public class DeckImporterFactory {
    
    public static DeckImporter getImporter(File file) {
        
        String fileName = file.getName().toLowerCase();

        if (fileName.endsWith(".csv")) {
            return new CSVDeckImporter();
        } 
        else if (fileName.endsWith(".json")) {
            return new JSONDeckImporter();
        } 
        else if (fileName.endsWith(".txt")) {
            // csv to to samo
            return new CSVDeckImporter();
        }
        throw new IllegalArgumentException("Nieobsługiwany format pliku: " + fileName);
    }
}