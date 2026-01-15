package swagerzy.Model.Adapter.FileReaders;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

// To jest klasa "Adaptee" - ona umie czytać plik, ale nie zwraca Talii, tylko Stringi
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