package com.generatorimprez.GEN.Model;

import org.junit.Test;
import static org.junit.Assert.*;
public class PassTest {

    @Test
    public void Decryption() {
        String password = "qwertyuiop[]asdfghjkl;\\zxcvbnm,./QWERTYUIOP{}ASDFGHJKL:\"|\'ZXCVBNM<>?1234567890-=!@#$%^&*()_+~`";
        char [] encodedPass = Pass.encodeText(password);
        String decodedPass = Pass.decodeText(encodedPass);
        assertEquals(password, decodedPass);
    }
}
