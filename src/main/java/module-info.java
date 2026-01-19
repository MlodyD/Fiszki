module swagerzy.los_peces {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.base;
    requires com.google.gson;
    opens swagerzy.las_fichas to javafx.fxml;
    exports swagerzy.las_fichas;
    
    opens swagerzy.Model.composite to com.google.gson, javafx.base;
    opens swagerzy.Model.memento to com.google.gson;
    opens swagerzy.Model to com.google.gson;
}
