module net.digitalhand.cryptsimple {
    requires javafx.controls;
    requires javafx.fxml;

    requires com.dlsc.formsfx;
    requires org.kordamp.bootstrapfx.core;
    requires slf4j.api;

    opens net.digitalhand.cryptsimple to javafx.fxml;
    exports net.digitalhand.cryptsimple;
}