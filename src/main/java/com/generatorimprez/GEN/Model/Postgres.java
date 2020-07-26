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

        String user = JOptionPane.showInputDialog(null, "Podaj login, domyślny dla PostgreSQL to postgres", "SQL", JOptionPane.INFORMATION_MESSAGE);
        String password = JOptionPane.showInputDialog(null, "Podaj hasło", "SQL", JOptionPane.INFORMATION_MESSAGE);
        try {
            Connection con = DriverManager.getConnection("jdbc:postgresql:", user, password);
            Statement stm = con.createStatement();
            stm.executeUpdate("create database generator_imprez;");
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        new Postgres(user, password);

        try {
            FileInputStream fis = new FileInputStream("src\\main\\resources\\static\\sql.data");
            ObjectInputStream ois = new ObjectInputStream(fis);
            char[] sql = (char[]) ois.readObject();

            ois.close();
            fis.close();
            Update(decodeText(sql));

        } catch ( IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }

    }

    public static void start () {}

  /*  public static void main(String[] args) {
        char [] s = encodeText("create table users (id serial, username varchar(400), reminder varchar(4000), answer varchar(4000), password varchar(400), admin int, primary key (id) );\n" +
                "create table services (id serial, name varchar(400), choice varchar(4000), primary key (id) ); \n" +
                "insert into services(name, choice) values ('alkohol', 'multi'),('animator', 'one'),('katering', 'multi'),('lokal', 'one'),('nocleg', 'one'),('media', 'multi'),('dodatki', 'multi');\n" +
                "create table subservices ( id serial, name varchar(400), price float, tax int, pricing varchar(2), service_id int, primary key (id), foreign key(service_id) references services(id) );\n" +
                "insert into subservices(name, price, tax, pricing, service_id) values\n" +
                "('impreza bez alkoholu', 0, 0, 'ps', 1),\n" +
                "('welcome drink', 10, 23, 'pp', 1),\n" +
                "('piwo', 30, 23, 'pp', 1),\n" +
                "('wino', 50, 23, 'pp', 1),\n" +
                "('wódka', 30, 23, 'pp', 1),\n" +
                "('open bar', 70, 23, 'pp', 1),\n" +
                "('barman', 1200, 23, 'ps', 1),\n" +
                "('impreza bez animatora', 0, 0, 'ps', 2),\n" +
                "('animator dla dzieci', 600, 23, 'ps', 2),\n" +
                "('DJ', 1000, 23, 'ps', 2),\n" +
                "('stand-up', 20000, 23, 'ps', 2),\n" +
                "('koncert', 20000, 23, 'ps', 2),\n" +
                "('brak jedzenia', 0, 0, 'ps', 3),\n" +
                "('tort', 50, 23, 'pp', 3),\n" +
                "('śniadanie', 30, 23, 'pp', 3),\n" +
                "('obiad', 40, 23, 'pp', 3),\n" +
                "('kolacja', 40, 23, 'pp', 3),\n" +
                "('poczęstunek', 20, 23, 'pp', 3),\n" +
                "('grill/ognisko', 30, 23, 'pp', 3),\n" +
                "('all inclusive', 100, 23, 'pp', 3),\n" +
                "('własny lokal', 0, 0, 'ps', 4),\n" +
                "('sala weselna', 50, 23, 'pp', 4),\n" +
                "('pole namiotowe', 10, 23, 'pp', 4),\n" +
                "('hotel', 30, 23, 'pp', 4),\n" +
                "('restauracja', 50, 23, 'pp', 4),\n" +
                "('bar', 30, 23, 'pp', 4),\n" +
                "('klub', 50, 23, 'pp', 4),\n" +
                "('bez noclegu', 0, 0, 'ps', 5),\n" +
                "('hotel', 150, 23, 'pp', 5),\n" +
                "('hostel', 100, 23, 'pp', 5),\n" +
                "('namiot', 50, 23, 'pp', 5),\n" +
                "('brak', 0, 0, 'ps', 6),\n" +
                "('fotograf', 300, 23, 'ps', 6),\n" +
                "('pamiątkowy film', 500, 23, 'ps', 6);\n" +
                "create table package_deals (id serial, offer_type varchar(4000), user_id int, subservice_id int, primary key (id), foreign key(subservice_id) references subservices(id), foreign key(user_id) references users(id));  ");
        try {
            FileOutputStream fos = new FileOutputStream("src\\main\\resources\\static\\sql.data");
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(s);
            oos.close();
            fos.close();

        } catch (Exception ex) {
        }
    }
*/
   }


