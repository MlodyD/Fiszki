package swagerzy.Model.Adapter.FileReaders;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

// Adaptee class, it can read a file and returns Strings, not decks
public class CSVReader {
    
    public List<String> readLines(File file) throws Exception {
        List<String> lines = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = br.readLine()) != null) {
                lines.add(line);
            }
        }
        return lines;
    }
}