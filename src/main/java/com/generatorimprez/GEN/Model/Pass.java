package com.generatorimprez.GEN.Model;

public abstract class Pass {

    public static char[] encodeText(String text) {
        char[] chars = new char[text.length()];
        for (int i = 0; i < text.length(); i++) {
            chars[i] = text.charAt(i);
            chars[i]++;
        }
        return chars;
    }

    public static String decodeText(char[] chars) {
        StringBuilder sb = new StringBuilder();
        for (char a : chars) {
            a--;
            sb.append(a);
        }
        return sb.toString();
    }


}
