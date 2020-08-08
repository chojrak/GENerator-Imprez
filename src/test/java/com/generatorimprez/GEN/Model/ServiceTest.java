package com.generatorimprez.GEN.Model;

import org.junit.Test;
import static org.junit.Assert.assertEquals;

public class ServiceTest {

    @Test
    public void addService() {
        String serviceName = "qwertyuiop[]asdfghjkl;\\zxcvbnm,./QWERTYUIOP{}ASDFGHJKL:|ZXCVBNM<>?1234567890-=!@#$%^&*()_+~`";
        Service test = new Service();
        test.setName(serviceName);
        test.setChoice("test");

        assertEquals(false, test.chckServiceName());
        test.addService();
        assertEquals(true, test.chckServiceName());
        test.deleteService();
        assertEquals(false, test.chckServiceName());
    }
}
