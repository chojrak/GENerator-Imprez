package com.generatorimprez.GEN.Model;

import org.junit.Test;

import static org.junit.Assert.*;

public class PromoCodeTest {

    @Test
    public void promoCode() {
        String promoName = "qwertyuiop[]asdfghjkl;\\zxcvbnm,./QWERTYUIOP{}ASDFGHJKL:|ZXCVBNM<>?1234567890-=!@#$%^&*()_+~`";
        PromoCode promoCode = new PromoCode(promoName, "test", 999999);
        promoCode.setAmount(100);

        assertEquals(false, promoCode.chckPromoCode());
        promoCode.addPromoCodes();
        assertEquals(999999, promoCode.chckPromoCodeValue(promoCode.getCode()));
        assertEquals(true, promoCode.chckPromoCode());
        promoCode.deletePromoCodes();
        assertEquals(false, promoCode.chckPromoCode());


    }
}
