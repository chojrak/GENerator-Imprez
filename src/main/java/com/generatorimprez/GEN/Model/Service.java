package com.generatorimprez.GEN.Model;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class Service {
    int id;
    String name;
    String choice;
    public static ArrayList<Service> multi;
    public static ArrayList<Service> one;

    static {
        multi = new ArrayList<Service>();
        one = new ArrayList<Service>();
    }

    public Service() {
    }

    public Service(String name, String choice, int id) {
        this.name = name;
        this.choice = choice;
        this.id = id;
    }

    public String getChoice() {
        return choice;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setChoice(String choice) {
        this.choice = choice;
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
        multi = new ArrayList<Service>();
        one = new ArrayList<Service>();
        ArrayList<Service> services = new ArrayList<Service>();
        try {
            ResultSet rs = Postgres.Execute("select * from services");
            while (rs.next()) services.add(new Service(rs.getString("name"), rs.getString("choice"), rs.getInt("id")));
            for (Service s : services) {
                if (s.getChoice().equals("multi")) multi.add(s);
                else if (s.getChoice().equals("one")) one.add(s);
            }
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

            ResultSet rs = Postgres.Execute("select * from subservices where service_id =" + this.id);
            while (rs.next())
                subServices.add(new SubService(rs.getString("name"), rs.getFloat("price"), rs.getInt("tax"), rs.getString("pricing")));
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return subServices;
    }

    public void deleteService() {
        Postgres.Update("delete from services where name = '"+this.name+"'");
    }

    public boolean chckSubServices() {
        try {
            ResultSet rs = Postgres.Execute("select s.* from subservices ss, services s where s.id = ss.service_id and s.name like '" + this.name+"'");
            return (rs.next());
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return false;
    }

    public void addService(){
        try {
            ResultSet rs = Postgres.Execute("select s.* from services s where s.name = '" + this.name+"'");
            if (!rs.next()) Postgres.Update("insert into services (name, choice) values ('"+this.name+"', '"+this.choice+"')");
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public boolean chckServiceName(){
        try {
            ResultSet rs = Postgres.Execute("select s.* from services s where s.name = '" + this.name+"'");
            return (rs.next());
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return false;
    }

}
