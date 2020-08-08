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
        this.url = Pass.encodeText("jdbc:postgresql:generator_imprez");
        this.user = Pass.encodeText(user);
        this.password = Pass.encodeText(password);
        SQLdata.put("url", this.url);
        SQLdata.put("user", this.user);
        SQLdata.put("password", this.password);
        saveToFile(SQLdata);
    }

    public static ResultSet Execute(String query) {
        try {

            Connection con = DriverManager.getConnection(Pass.decodeText(url), Pass.decodeText(user), Pass.decodeText(password));
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
            Connection con = DriverManager.getConnection(Pass.decodeText(url), Pass.decodeText(user), Pass.decodeText(password));
            Statement stm = con.createStatement();
            stm.executeUpdate(query);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
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
        JPanel panel = new JPanel();
        JLabel label = new JLabel("Podaj hasło:");
        JPasswordField passwordField = new JPasswordField(25);
        panel.add(label);
        panel.add(passwordField);
        String user = JOptionPane.showInputDialog(null, "Podaj login, domyślny dla PostgreSQL to postgres", "SQL", JOptionPane.INFORMATION_MESSAGE);
        JOptionPane.showConfirmDialog(null, panel, "SQL", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
        char[] pass = passwordField.getPassword();
        StringBuilder sb = new StringBuilder();
        for (char a : pass) sb.append(a);
        String password = sb.toString();

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
            Update(Pass.decodeText(sql));

        } catch ( IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }

    }

    public static String getUrl() {
        return Pass.decodeText(url);
    }

    public static String getUser() {
        return Pass.decodeText(user);
    }

    public static String getPassword() {
        return Pass.decodeText(password);
    }

    public static void start () {}

 /*   public static void main(String[] args) {

    String pass = User.encodePass("Admin1234");
    char [] s = Pass.encodeText("create table if not exists users (id serial, username varchar(400), reminder varchar(4000), answer varchar(4000), password varchar(400), admin int, primary key (id) );\n" +
            "insert into users(username, reminder, answer, password, admin) values ('admin', 'admin', 'admin', '"+pass+"', 1);\n"+
            "create table if not exists services (id serial, name varchar(400), choice varchar(4000), primary key (id) ); \n" +
            "insert into services(name, choice) values ('alkohol', 'multi'),('animator', 'one'),('katering', 'multi'),('lokal', 'one'),('nocleg', 'one'),('media', 'multi'),('dodatki', 'multi');\n" +
            "create table if not exists subservices ( id serial, name varchar(400), price float, tax int, pricing varchar(2), service_id int, description varchar(4000), primary key (id), foreign key(service_id) references services(id) );\n" +
            "insert into subservices(name, price, tax, pricing, service_id, description) values\n" +
            "('impreza bez alkoholu', 0, 0, 'ps', 1, ''),\n" +
            "('welcome drink', 10, 23, 'pp', 1, 'Welcome drink - alkohol w ograniczonej ilości - w cenie 2 na osobę.'),\n" +
            "('piwo', 30, 23, 'pp', 1, 'Piwo dla wszystkich przez całą imprezę'),\n" +
            "('wino', 50, 23, 'pp', 1, 'Wino dla wszystkich przez całą imprezę.'),\n" +
            "('wódka', 30, 23, 'pp', 1, 'Wódka dla wszystkich przez całą imprezę'),\n" +
            "('open bar', 70, 23, 'pp', 1, 'Open bar dla wszystkich przez całą imprezę'),\n" +
            "('barman', 1200, 23, 'ps', 1, 'Usługi barmańskie maksymalnie do północy. Wymagany własny alkohol lub usługa open bar.'),\n" +
            "('impreza bez animatora', 0, 0, 'ps', 2, ''),\n" +
            "('animator dla dzieci', 600, 23, 'ps', 2, 'Opieka nad grupą maksymalnie 10 dzieci do północy.'),\n" +
            "('DJ', 1000, 23, 'ps', 2, 'Usługa DJ maksymalnie do 3 w nocy.'),\n" +
            "('stand-up', 20000, 23, 'ps', 2, 'Twoją imprezę uświetni show znanego stand-upera. Lista osób z którymi współpracujemy dostępna w zakładce \"o nas\".'),\n" +
            "('koncert', 20000, 23, 'ps', 2, 'Twoją imprezę uświetni show znanego zespołu. Lista zespołów z którymi współpracujemy dostępna w zakładce \"o nas\".'),\n" +
            "('brak jedzenia', 0, 0, 'ps', 3, ''),\n" +
            "('tort', 50, 23, 'pp', 3, 'Wielkość tortu uzależniona jest od listy osób. Istnieje możliwość dodania indywidualnego wzoru lub napisu.'),\n" +
            "('śniadanie', 30, 23, 'pp', 3, 'Śniadanie.'),\n" +
            "('obiad', 40, 23, 'pp', 3, 'Obiad.'),\n" +
            "('kolacja', 40, 23, 'pp', 3, 'Kolacja.'),\n" +
            "('poczęstunek', 20, 23, 'pp', 3, 'Poczęstunek.'),\n" +
            "('grill/ognisko', 30, 23, 'pp', 3, 'Zorganizowane ognisko/grill z kiełbaskami dla wszystkich.'),\n" +
            "('all inclusive', 100, 23, 'pp', 3, 'Pełne wyżywienie.'),\n" +
            "('własny lokal', 0, 0, 'ps', 4, ''),\n" +
            "('sala weselna', 50, 23, 'pp', 4, 'Wynajęcie sali weselnej na całą noc.'),\n" +
            "('pole namiotowe', 10, 23, 'pp', 4, 'Miejsce na polu namiotowym.'),\n" +
            "('hotel', 30, 23, 'pp', 4, 'Sala konferencyjna w hotelu.'),\n" +
            "('restauracja', 50, 23, 'pp', 4, 'Sala w restauracji.'),\n" +
            "('bar', 30, 23, 'pp', 4, 'Impreza zamknięta w barze.'),\n" +
            "('klub', 50, 23, 'pp', 4, 'Loża VIP w klubie.'),\n" +
            "('bez noclegu', 0, 0, 'ps', 5, ''),\n" +
            "('hotel', 150, 23, 'pp', 5, 'Nocleg w hotelu.'),\n" +
            "('hostel', 100, 23, 'pp', 5, 'Nocleg w hostelu.'),\n" +
            "('namiot', 50, 23, 'pp', 5, 'Miejsce na polu namiotowym.'),\n" +
            "('brak', 0, 0, 'ps', 6, ''),\n" +
            "('fotograf', 300, 23, 'ps', 6, 'Zdjęcia profesjonalnego fotografa.'),\n" +
            "('pamiątkowy film', 500, 23, 'ps', 6, 'Pamiątkowy film.'),\n" +
            "('gra terenowa', 2000, 23, 'ps', 7, 'Gra integracyjno-rozrywkowa na świeżym powietrzu.'),\n" +
            "('sala konferencyjna', 10000, 23, 'ps', 5, 'Obiekt konferencyjny do 2 tys. osób.'),\n" +
            "('ochrona', 1500, 23, 'ps', 7, 'Zabezpiecznie imprezy przez agencje ochrony.'),\n" +
            "('fajerwerki', 5000, 23, 'ps', 7, 'Pokaz sztucznych ogni ok. 20 min.'),\n" +
            "('pokaz dronów', 3500, 23, 'ps', 7, 'Pokaz świetlny dronami ok. 30 min.'),\n" +
            "('impreza sportowa', 15000, 23, 'ps', 7, 'Obiekt sportowy oraz obsługa.');\n" +
            "create table if not exists package_deals (id int, offer_type varchar(4000), user_id int, subservice_id int, foreign key(subservice_id) references subservices(id), foreign key(user_id) references users(id));\n" +
            "insert into package_deals (id, offer_type, user_id, subservice_id) values (1, 'public', 1, 6),\n"+
            "(1, 'public', 1, 10),\n"+
            "(1, 'public', 1, 14),\n"+
            "(1, 'public', 1, 16),\n"+
            "(1, 'public', 1, 17),\n"+
            "(1, 'public', 1, 22),\n"+
            "(1, 'public', 1, 29),\n"+
            "(1, 'public', 1, 33),\n"+
            "(2, 'public', 1, 9), \n"+
            "(2, 'public', 1, 14),\n"+
            "(2, 'public', 1, 18),\n"+
            "(2, 'public', 1, 32),\n"+
            "(2, 'public', 1, 33),\n"+
            "(3, 'public', 1, 6), \n"+
            "(3, 'public', 1, 20),\n"+
            "(3, 'public', 1, 26),\n"+
            "(3, 'public', 1, 27),\n"+
            "(3, 'public', 1, 34),\n"+
            "(4, 'public', 1, 7), \n"+
            "(4, 'public', 1, 11),\n"+
            "(4, 'public', 1, 19),\n"+
            "(4, 'public', 1, 20),\n"+
            "(4, 'public', 1, 24),\n"+
            "(4, 'public', 1, 35),\n"+
            "(5, 'public', 1, 18),\n"+
            "(5, 'public', 1, 36),\n"+
            "(5, 'public', 1, 33),\n"+
			"(5, 'public', 1, 37),\n"+
			"(6, 'public', 1, 12),\n"+
			"(6, 'public', 1, 37),\n"+
			"(7, 'public', 1, 9),\n"+
			"(7, 'public', 1, 19),\n"+
			"(7, 'public', 1, 20),\n"+
			"(7, 'public', 1, 30),\n"+
			"(7, 'public', 1, 34),\n"+
			"(7, 'public', 1, 35),\n"+
			"(8, 'public', 1, 35),\n"+
			"(8, 'public', 1, 3),\n"+
			"(8, 'public', 1, 5),\n"+
			"(8, 'public', 1, 19),\n"+
			"(8, 'public', 1, 23),\n"+
			"(8, 'public', 1, 31),\n"+
			"(9, 'public', 1, 5),\n"+
			"(9, 'public', 1, 9),\n"+
			"(9, 'public', 1, 14),\n"+
			"(9, 'public', 1, 16),\n"+
			"(9, 'public', 1, 22),\n"+
			"(9, 'public', 1, 33),\n"+
			"(9, 'public', 1, 34),\n"+
			"(10, 'public', 1, 37),\n"+
			"(10, 'public', 1, 18),\n"+
			"(10, 'public', 1, 38),\n"+
			"(11, 'public', 1, 18),\n"+
			"(11, 'public', 1, 37),\n"+
			"(11, 'public', 1, 33);\n"+
            "create table if not exists promo_codes (id int, code varchar(4000), type varchar(400), value int);\n"+
            "create table if not exists reminder_tokens (id serial, token varchar(4000), user_id int, expiration_date timestamp, primary key (id), foreign key(user_id) references users(id));\n"+
            "create table if not exists final_offers (id serial, promo_code varchar(4000), promo_code_value int, package_deal_id int, number_of_people int, primary key (id));");
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


