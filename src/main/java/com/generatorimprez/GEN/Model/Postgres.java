package com.generatorimprez.GEN.Model;

import javax.swing.*;
import java.io.*;
import java.sql.*;
import java.util.HashMap;

public class Postgres implements Serializable {
    private static char[] url;
    private static char[] user;
    private static char[] password;
    private static HashMap<String, char[]> SQLdata;

    static {
        SQLdata = new HashMap<String, char[]>();
        try {
            FileInputStream fis = new FileInputStream("src\\main\\resources\\static\\data.jpg");
            ObjectInputStream ois = new ObjectInputStream(fis);
            SQLdata = (HashMap<String, char[]>) ois.readObject();

            ois.close();
            fis.close();
            url = SQLdata.get("url");
            user = SQLdata.get("user");
            password = SQLdata.get("password");

        } catch (FileNotFoundException e) {
            newDatabase();

        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }


    public Postgres(String user, String password) {
        this.url = encodeText("jdbc:postgresql:generator_imprez");
        this.user = encodeText(user);
        this.password = encodeText(password);
        SQLdata.put("url", this.url);
        SQLdata.put("user", this.user);
        SQLdata.put("password", this.password);
        saveToFile(SQLdata);
    }

    public static ResultSet Execute(String query) {
        try {

            Connection con = DriverManager.getConnection(decodeText(url), decodeText(user), decodeText(password));
            Statement stm = con.createStatement();
            ResultSet rs = stm.executeQuery(query);
            return rs;
        } catch (
                SQLException throwables) {
            throwables.printStackTrace();
        }
        return null;
    }

    public static void Update(String query) {
        try {
            Connection con = DriverManager.getConnection(decodeText(url), decodeText(user), decodeText(password));
            Statement stm = con.createStatement();
            stm.executeUpdate(query);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

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

    public static void saveToFile(HashMap<String, char[]> data) {
        try {
            FileOutputStream fos = new FileOutputStream("src\\main\\resources\\static\\data.jpg");
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(data);
            oos.close();
            fos.close();

        } catch (Exception ex) {
        }
    }

    public static void newDatabase() {
        String user = JOptionPane.showInputDialog("Podaj login, domyślny dla PostgreSQL to postgres");
        String password = JOptionPane.showInputDialog("Podaj hasło");
        try {
            Connection con = DriverManager.getConnection("jdbc:postgresql:", user, password);
            Statement stm = con.createStatement();
            stm.executeUpdate("create database generator_imprez;");
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        new Postgres(user, password);
        Update("create table users ( id_user serial, username varchar(400), reminder varchar(4000), answer varchar(4000), password varchar(400), admin int, primary key (id_user) );");
    }



}


