package com.cryptsimple;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import org.apache.log4j.Logger;

import java.io.IOException;

public class CryptSimple extends Application {
    final static Logger logger = Logger.getLogger(CryptSimple.class);
    @Override
    public void start(Stage stage) {
        logger.debug("Starting CryptSimple...");

        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/main.fxml"));
            Parent root = loader.load();

            Scene scene = new Scene(root,400,400);
            scene.getStylesheets().add(getClass().getResource("/style.css").toExternalForm());

            stage.setScene(scene);
            stage.setTitle("Crypt Simple");
            stage.getIcons().add(new Image("/key.png"));
            stage.show();

        } catch (IOException e) {
            logger.debug("Could not launch FXML app.", e);
        }
    } // End of start method



    public static void main(String[] args) {
        launch(args);
    }
}
