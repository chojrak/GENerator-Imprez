package com.generatorimprez.GEN.Model;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class SubService {
    String name;
    float price;
    int tax;
    String type;
    String serviceName;
    String description;


    public SubService() {
    }

    public SubService(String name, float price, int tax, String type) {
        this.name = name;
        this.price = price;
        this.tax = tax;
        this.type = type;
    }

    public SubService(String name, float price, int tax, String type, String serviceName) {
        this.name = name;
        this.price = price;
        this.tax = tax;
        this.type = type;
        this.serviceName = serviceName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public int getTax() {
        return tax;
    }

    public void setTax(int tax) {
        this.tax = tax;
    }

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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

    public static ArrayList<String> getSubServiceNames() {
        ArrayList<String> serviceNames = new ArrayList<String>();
        try {
            ResultSet rs = Postgres.Execute("select distinct name from subservices");
            while (rs.next())
                serviceNames.add(rs.getString("name"));
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return serviceNames;
    }

    public void deleteSubService(){
        Postgres.Update("delete from package_deals p where p.id in (select distinct p.id from package_deals p, subservices s where p.subservice_id = s.id and s.name = '"+this.name+"')");
        Postgres.Update("delete from subservices s where s.name like '"+this.name+"'");
    }

    public void addSubService() {
        int id = 0;
        try {
            ResultSet rs = Postgres.Execute("select s.id from services s where s.name = '"+this.serviceName+"'");
            while (rs.next()) id = rs.getInt("id");
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        Postgres.Update("insert into subservices (name, price, tax, pricing, service_id, description) values ('"+this.name+"', "+this.price+", "+this.tax+", '"+this.type+"', "+id+", '"+this.description+"')");
    }

    public void changePrice() {
        Postgres.Update("update subservices set price = "+this.price+" where name = '"+this.name+"'");
    }

    public void changeTax() {
        Postgres.Update("update subservices  set tax = "+this.tax+" where name = '"+this.name+"'");
    }

    public void changeDescription() {
        Postgres.Update("update subservices set description = '"+this.description+"' where name = '"+this.name+"'");
    }

    public boolean chckSubServiceName() {
        try {
            ResultSet rs = Postgres.Execute("select * from subservices s where s.name = '"+this.name+"'");
            return (rs.next());
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return false;
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
