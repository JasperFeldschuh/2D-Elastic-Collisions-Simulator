module com.example.collisions_simulation {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires org.kordamp.bootstrapfx.core;

    opens com.example.collisions_simulation to javafx.fxml;
    exports com.example.collisions_simulation;
}