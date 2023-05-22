module com.example.demo {
    requires javafx.controls;
    requires javafx.fxml;

    requires com.dlsc.formsfx;
    requires org.kordamp.bootstrapfx.core;
    requires slf4j.api;

    opens com.cryptsimple to javafx.fxml;
    exports com.cryptsimple;
}