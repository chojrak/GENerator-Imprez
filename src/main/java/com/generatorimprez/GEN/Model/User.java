package com.generatorimprez.GEN.Model;



import org.bouncycastle.util.encoders.Base64;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class User {
    String username;
    String password;
    String reminder;
    String answer;
    boolean admin;

    public User(String username, String password, String reminder, String answer, boolean admin) {
        this.username = username;
        this.password = encodePass(password);
        this.reminder = reminder;
        this.answer = answer;
        this.admin = admin;
        addUserToDB(username, encodePass(password), reminder, answer, admin);
    }

    public static String encodePass(String pass) {
        String sha256hex = "";
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(
                    pass.getBytes(StandardCharsets.UTF_8));
            sha256hex = new String (Base64.encode(hash));
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return sha256hex;
    }

    public static void addUserToDB(String username, String password, String reminder, String answer, boolean admin) {
        StringBuilder sb = new StringBuilder();
        sb.append("insert into users (username, reminder, answer, password, admin) values ('");
        sb.append(username);
        sb.append("', '");
        sb.append(reminder);
        sb.append("', '");
        sb.append(answer);
        sb.append("', '");
        sb.append(password);
        sb.append("', ");
        int i = admin ? 1 : 0;
        Postgres.Update(sb.toString());
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getReminder() {
        return reminder;
    }

    public String getAnswer() {
        return answer;
    }
}
