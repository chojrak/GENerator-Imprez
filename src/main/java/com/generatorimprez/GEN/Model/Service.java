package com.generatorimprez.GEN.Model;

import org.springframework.http.converter.json.GsonBuilderUtils;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

public class Service {
    String name;
    String choice;
    public static ArrayList <Service> multi;
    public static ArrayList <Service> one;

    static {
        multi = new ArrayList <Service>();
        one = new ArrayList <Service>();
    }



    public Service(String name, String choice) {
        this.name = name;
        this.choice = choice;
    }

    public String getChoice() {
        return choice;
    }

    public String getName() {
        return name;
    }

    public static ArrayList<String> getServiceNames() {
        ArrayList<String> serviceNames = new ArrayList<String>();
        try {
            ResultSet rs = Postgres.Execute("select distinct name from services");
            while (rs.next())
                serviceNames.add(rs.getString("name"));
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return serviceNames;
    }

    public static int getServiceId(String serviceName) {
        int id = 0;
        try {
            ResultSet rs = Postgres.Execute("select id from services where name like '" + serviceName + "'");
            while (rs.next()) id = rs.getInt("id");
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return id;
    }

    public static void getServices() {
        multi = new ArrayList <Service>();
        one = new ArrayList <Service>();
        ArrayList<Service> services = new ArrayList<Service> ();
        try {
            ResultSet rs = Postgres.Execute("select * from services");
            while (rs.next()) services.add(new Service(rs.getString("name"), rs.getString("choice")));
            for (Service s : services) {if (s.getChoice().equals("multi")) multi.add(s);
            else if(s.getChoice().equals("one")) one.add(s);}
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public static ArrayList<SubService> getSubServices(String serviceName) {
        int id = getServiceId(serviceName);
        ArrayList<SubService> subServices = new ArrayList<SubService>();
        try {

            ResultSet rs = Postgres.Execute("select * from subservices where service_id =" + id);
            while (rs.next())
                subServices.add(new SubService(rs.getString("name"), rs.getFloat("price"), rs.getInt("tax"), rs.getString("pricing")));
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return subServices;
    }

    public ArrayList<SubService> getSubServices() {
        ArrayList<SubService> subServices = new ArrayList<SubService>();
        try {

            ResultSet rs = Postgres.Execute("select * from subservices where service_id =" + getServiceId(this.name));
            while (rs.next())
                subServices.add(new SubService(rs.getString("name"), rs.getFloat("price"), rs.getInt("tax"), rs.getString("pricing")));
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return subServices;
    }

    public static void main(String[] args) {
        for (SubService s : getSubServices("lokal")) System.out.println(s.getName());
    }
}
