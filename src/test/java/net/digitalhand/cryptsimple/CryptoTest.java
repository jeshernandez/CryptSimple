package net.digitalhand.cryptsimple;

import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import static org.junit.jupiter.api.Assertions.*;

class CryptoTest {
    @Mock
    private CryptSimple cryptSimple = new CryptSimple();

    @Test
    void init_Crypto()  {
        // WHEN
        String delimiter = cryptSimple.delimiter;
        // THEN
        assertTrue(delimiter.equals("//"));
    }

}