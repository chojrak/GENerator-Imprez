package com.generatorimprez.GEN.Model;


import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class ResetToken {
    String token;
    String expirationDate;
    User user;

    public ResetToken(String token, User user) {
        this.token = token;
        this.user = user;
        Date date = new Date(Calendar.getInstance().getTimeInMillis() + 1800000);
        Format formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        this.expirationDate = formatter.format(date);
        saveTokenInDB();
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(String expirationDate) {
        this.expirationDate = expirationDate;
    }

    public void saveTokenInDB() {
        int id = User.chckIdUser(user.getUsername());
        Postgres.Update("insert into reminder_tokens (token, user_id, expiration_date) values ('" + token + "', " + id + ", '" + expirationDate + "')");
    }

    public static boolean isTokenValid(String token) {
        try {
            ResultSet rs = Postgres.Execute("select case when expiration_date > now() then true else false end chck from reminder_tokens where token like '"+token+"'");
            while (rs.next())
                return (rs.getBoolean("chck"));
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return false;
    }

    public static boolean isTokenMatchingUser (String token, int user_id)
    {
        System.out.println(token);
        System.out.println(user_id);
        try {
            ResultSet rs = Postgres.Execute("select * from reminder_tokens where token like '"+token+"' and user_id ="+user_id);
            return (rs.next());
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return false;
    }



}
