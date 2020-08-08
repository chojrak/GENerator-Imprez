package com.generatorimprez.GEN.Model;

import org.junit.Test;
import static org.junit.Assert.assertEquals;
public class UserTest {

    @Test
    public void Decryption() {
        String password = "qwertyuiop[]asdfghjkl;\\zxcvbnm,./QWERTYUIOP{}ASDFGHJKL:\"|\'ZXCVBNM<>?1234567890-=!@#$%^&*()_+~`";
        String obcject1 = User.encodePass(password);
        String obcject2 = User.encodePass(password);
        assertEquals(obcject1, obcject2);
    }
}
