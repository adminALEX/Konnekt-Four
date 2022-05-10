module com.example.connect4game {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires org.kordamp.bootstrapfx.core;

    opens com.example.connect4game to javafx.fxml;
    exports com.example.connect4game;
}