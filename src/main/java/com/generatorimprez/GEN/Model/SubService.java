package com.generatorimprez.GEN.Model;

import java.sql.ResultSet;
import java.sql.SQLException;

public class SubService {
    String name;
    float price;
    int tax;
    String type;


    public SubService() {
    }

    public SubService(String name, float price, int tax, String type) {
        this.name = name;
        this.price = price;
        this.tax = tax;
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public float getTax() {
        return tax;
    }

    public void setTax(int tax) {
        this.tax = tax;
    }


    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public static int getSubServiceId (String subServiceName)
    {
        int id = 0;
        try {
            ResultSet rs = Postgres.Execute("select id from subservices where name like '"+subServiceName+"'");
            while (rs.next()) id = rs.getInt("id");
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return id;
    }

    @Override
    public String toString() {
        return name;
    }

    /*   public static String getServiceName(int serviceId) {
        String name = "";
        try {
        ResultSet rs = Postgres.Execute("select name from subservices where id = "+serviceId);
        while(rs.next()) name = rs.getString("name"); }
        catch (SQLException e){
            e.printStackTrace();
        }
        return name;
    }*/
}
