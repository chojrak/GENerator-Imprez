package com.generatorimprez.GEN.Model;

public class PromoCode {
    String code;
    String type;
    int amount;
    int value;

    public PromoCode() {
    }

    public PromoCode(String code, String type, int value) {
        this.code = code;
        this.type = type;
        this.value = value;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }
}
