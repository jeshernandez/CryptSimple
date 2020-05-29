package com.cryptsimple.controller;

import com.cryptsimple.Crypto;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.PasswordField;
import javafx.scene.input.DragEvent;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.BorderPane;
import org.apache.log4j.Logger;

import javax.crypto.Cipher;
import java.io.File;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class MainController {
    final Logger logger = Logger.getLogger(MainController.class);

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

    public void initialize(URL url, ResourceBundle arg) {
        logger.debug("Initilizing MainController...");
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
    private void handleDrop(DragEvent event) {
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

        if(getPwdValue().length() == 16) {
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

        } // End if-statement

    } // End of handleDrop


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
    private void clearText() {
        keyValue.setText("");
        setPwdValue("");
    }

    public String getPwdValue() {
        return pwdValue;
    }

    public void setPwdValue(String pwdValue) {
        this.pwdValue = pwdValue;
    }

} // End of MainController class
