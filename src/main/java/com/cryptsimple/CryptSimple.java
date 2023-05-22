package com.cryptsimple;

import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.PasswordField;
import javafx.scene.image.Image;
import javafx.scene.input.DragEvent;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;


import javax.crypto.Cipher;
import java.io.File;
import java.io.IOException;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CryptSimple extends Application {

    Logger logger = LoggerFactory.getLogger(CryptSimple.class);

    @FXML
    MenuItem exitCmd = new MenuItem();
    @FXML
    BorderPane borderPane = new BorderPane();
    @FXML
    Label lblDragMessage = new Label();
    @FXML
    PasswordField keyValue = new PasswordField();
    @FXML
    Label lblStatus = new Label();

    String delimiter = "//";
    String encryptedExt = "SE";
    private String pwdValue;
    private int cryptType = -1;

    File inputFile;

    public String getPwdValue() {
        return pwdValue;
    }

    public void setPwdValue(String pwdValue) {
        this.pwdValue = pwdValue;
    }

    private void updateStatusMessage() {
        if(Crypto.isCryptStatus()) {
            if(cryptType == 0) {
                lblStatus.setText("Decryption Complete");
                logger.debug("Decryption Complete " + inputFile);
            } else if(cryptType == 1) {
                lblStatus.setText("Encryption Complete");
                logger.debug("Encryption Complete " + inputFile);
            } else {
                lblStatus.setText("Wrong Key Phrase!");
            }
        }
    }

    @FXML
    private void exitApplilcation() {
        System.exit(0);
    }

    @FXML
    private void handleDragOver(DragEvent event) {
        if(event.getDragboard().hasFiles()) {
            event.acceptTransferModes(TransferMode.ANY);
        }
    }
    @FXML
    private void clearText() {
        keyValue.setText("");
        setPwdValue("");
    }

    @FXML
    private void handleDrop(DragEvent event)  {
        List<File> files = event.getDragboard().getFiles();

        String dirPath = files.get(0).getParent();
        File fileName = files.get(0);
        String fileNameOnly = fileName.getName();

        lblDragMessage.setText(files.get(0).toString());
        inputFile = new File(files.get(0).getAbsolutePath());

        setPwdValue(keyValue.getText());
        logger.debug("Key Size: " + keyValue.getLength());

        File encryptedFileL = new File(dirPath + delimiter + fileNameOnly + "." + encryptedExt);
        File decryptedFileL = new File(dirPath + delimiter + fileNameOnly.replace("."+encryptedExt,  ""));

        if(getPwdValue().length() >= 15) {
            String key = getPwdValue();

            logger.debug("Key : " + key);

            try {
                if(!inputFile.toString().endsWith(encryptedExt)) {
                    cryptType = 1;
                    logger.debug("Encrypting Initiated...");
                    Crypto.fileProcessor(Cipher.ENCRYPT_MODE, key, inputFile, encryptedFileL);
                    updateStatusMessage();
                } else if (inputFile.toString().endsWith(encryptedExt)) {
                    cryptType = 0;
                    logger.debug("Decryption started...");
                    Crypto.fileProcessor(Cipher.DECRYPT_MODE, key, inputFile, decryptedFileL);
                    updateStatusMessage();
                } else {
                    lblStatus.setText("Nothing to do with this file...");
                }

            } catch (Exception e) {
                logger.error("Error with Crypto Class " + e);
                e.printStackTrace();
            }

        } else {
            lblStatus.setText("Key must be at least 16 characters!");
        } // End if-statement

    } // End of handleDrop


    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(CryptSimple.class.getResource("main.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 400, 400);
        stage.setTitle("Crypt Simple");
        stage.getIcons().add(new Image("/key.png"));
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}