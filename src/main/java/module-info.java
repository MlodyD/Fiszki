module swagerzy.los_peces {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.base;
    requires com.google.gson;
    opens swagerzy.los_peces to javafx.fxml;
    exports swagerzy.los_peces;
    
    // --- DODAJ TĘ LINIĘ: ---
    // Otwieramy pakiet z modelem (Deck, Flashcard) dla Gsona, żeby mógł czytać prywatne pola
    opens swagerzy.Model.composite to com.google.gson;
    // -----------------------
}
