package com.cryptsimple;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;
import java.io.*;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;

public class Crypto extends CryptSimple {
    private static FileInputStream inputStream = null;
    private static FileOutputStream outputStream = null;
    private static int myCipher;
    private static String myKey;
    private static File myInputFile;
    private static File myOutputFile;
    private static boolean cryptStatus;
    static Logger logger = LoggerFactory.getLogger(Crypto.class);

    public static void fileProcessor(int cipherMode, String key, File inputFile, File outputFile) {
        cryptStatus = false;
        setMyCipher(cipherMode);
        setMyKey(key);
        setMyInputFile(inputFile);
        setMyOutputFile(outputFile);

        initCrypto();


    } // end of fileProcessor

    private static void initCrypto()  {
        try {
            Key secretKey = new SecretKeySpec(getMyKey().getBytes(), "AES");
            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
            cipher.init(getMyCipher(), secretKey);

            inputStream = new FileInputStream(getMyInputFile());
            byte[] inputBytes = new byte[(int) getMyInputFile().length()];
            inputStream.read(inputBytes);

            byte[] outputBytes = cipher.doFinal(inputBytes);

            outputStream = new FileOutputStream(getMyOutputFile());
            outputStream.write(outputBytes);

            inputStream.close();
            outputStream.close();

            cryptStatus = true;
            deleteFile();

        } catch (NoSuchPaddingException e) {
            logger.error("Missing Padding " + e);
        } catch (NoSuchAlgorithmException e1) {
            logger.error("Missing algorithm " + e1);
        } catch (InvalidKeyException e2) {
            logger.error("Invalid Key " + e2);
        } catch (FileNotFoundException e3) {
            logger.error("File not found " + e3);
        } catch (IOException e4) {
            logger.error("Input/Output " +e4);
        } catch (IllegalBlockSizeException e5) {
            logger.error("Illegal Block Size " + e5);
        } catch (BadPaddingException e6) {
            logger.error("Bad Padding Exception " + e6);
        } finally {
            if(inputStream != null && outputStream != null) {
                try {
                    inputStream.close();
                    outputStream.close();
                } catch (IOException e) {
                    logger.error("Failed to close stream " + e);
                }

            }
        }
    } // end of initCrypto


    private static void deleteFile() {
        File deleteFileName = getMyInputFile();
        if(deleteFileName.delete()) {
            logger.debug("Input File Deleted");
        }
    }

    public static int getMyCipher() {
        return myCipher;
    }

    public static void setMyCipher(int myCipher) {
        Crypto.myCipher = myCipher;
    }

    public static String getMyKey() {
        return myKey;
    }

    public static void setMyKey(String myKey) {
        Crypto.myKey = myKey;
    }

    public static File getMyInputFile() {
        return myInputFile;
    }

    public static void setMyInputFile(File myInputFile) {
        Crypto.myInputFile = myInputFile;
    }

    public static boolean isCryptStatus() {
        return cryptStatus;
    }

    public static void setCryptStatus(boolean cryptStatus) {
        Crypto.cryptStatus = cryptStatus;
    }

    public static File getMyOutputFile() {
        return myOutputFile;
    }

    public static void setMyOutputFile(File myOutputFile) {
        Crypto.myOutputFile = myOutputFile;
    }
}
