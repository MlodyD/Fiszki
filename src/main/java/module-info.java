module swagerzy.los_peces {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.base;

    opens swagerzy.los_peces to javafx.fxml;
    exports swagerzy.los_peces;
}
