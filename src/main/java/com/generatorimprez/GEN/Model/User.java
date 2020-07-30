package com.generatorimprez.GEN.Model;


import org.bouncycastle.util.encoders.Base64;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;


public class User {
    String username;
    String password;
    String password2;
    String reminder;
    String answer;
    boolean admin;

    public User() {
    }

    public User(String username, String password, String reminder, String answer, boolean admin) {
        this.username = username;
        this.password = encodePass(password);
        this.reminder = reminder;
        this.answer = answer;
        this.admin = admin;
    }

    public static String encodePass(String pass) {
        String sha256hex = "";
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(
                    pass.getBytes(StandardCharsets.UTF_8));
            sha256hex = new String(Base64.encode(hash));
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return sha256hex;
    }

    public static boolean chckUsername(String username) {
        try {
            ResultSet rs = Postgres.Execute("select username from users where username like '" + username + "'");
            return (rs.next());
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return false;
    }

    public static void addUserToDB(User user) {
        StringBuilder sb = new StringBuilder();
        sb.append("insert into users (username, reminder, answer, password, admin) values ('");
        sb.append(user.getUsername());
        sb.append("', '");
        sb.append(user.getReminder());
        sb.append("', '");
        sb.append(user.getAnswer());
        sb.append("', '");
        sb.append(user.getPassword());
        sb.append("', 0)");
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

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = encodePass(password);
    }

    public void setReminder(String reminder) {
        this.reminder = reminder;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public void setPassword2(String password2) {
        this.password2 = encodePass(password2);
    }

    public String getPassword2() {
        return password2;
    }

    public static ArrayList<User> getAllUsers() {
        ArrayList<User> allUsers = new ArrayList<User>();
        try {
            ResultSet rs = Postgres.Execute("select * from users");
            while (rs.next()) {
                allUsers.add(new User(rs.getString("username"), "", "", "", false));
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return allUsers;
    }

    public boolean isAdmin () {
        int admin = 0;
        try {
            ResultSet rs = Postgres.Execute("select admin from users where username like '"+this.username+"'");
            while (rs.next()) {
                admin = rs.getInt("admin");
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return (admin==1);
    }

    public boolean chckuser (User user) {
        try {
            ResultSet rs = Postgres.Execute("select * from users where username like '"+user.username+"' and password like '"+user.password+"'");
            return (rs.next());
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return false;
    }


    public static void main(String[] args) {
        ArrayList<User> allUsers = getAllUsers();
        for (User o : allUsers) System.out.println(o.getUsername());
    }
}
