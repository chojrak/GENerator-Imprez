package com.generatorimprez.GEN.Model;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class PackageDeal {
    static int nextId;
    int idUser;
    String type;
    ArrayList<Integer> subServices;

    static {
        try {
            ResultSet rs = Postgres.Execute("select case when (select max(id) from package_deals) is null then 0 else (select max(id) from package_deals) end +1 id");
            while (rs.next()) nextId = rs.getInt("id");
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    {
        subServices = new ArrayList<Integer>();
    }

    public PackageDeal() {
    }

    public PackageDeal(int idUser, String type) {
        this.idUser = idUser;
        this.type = type;
    }

    public void addSubService(Integer idSubService) {
        subServices.add(idSubService);
    }

    public void addSubService(String SubServiceName) {
        if (!SubServiceName.isEmpty()) subServices.add(SubService.getSubServiceId(SubServiceName));
    }

    public void savePackageDeal() {
        try {
            ResultSet rs = Postgres.Execute("select id from package_deal where user_id = " + this.idUser);
            if (!rs.next()) {
                for (int i : this.subServices)
                    Postgres.Update("insert into package_deals (id, user_id, subservice_id) values(" + nextId + "," + this.idUser + "," + i + ")");
                nextId++;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public static ArrayList<Integer> getPackageDeal(int userId) {
        ArrayList<Integer> l = new ArrayList<Integer>();
        try {
            ResultSet rs = Postgres.Execute("select subservice_id from package_deal where user_id = " + userId);
            while (rs.next()) l.add(rs.getInt("subservice_id"));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return l;
    }

    public void setIdUser(int idUser) {
        this.idUser = idUser;
    }

    public void setType(User user) {
        this.type = (user.isAdmin())? "public" : "private";

    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setType(boolean type) {
        this.type = (type)? "public" : "private";
    }

    public ArrayList<Integer> getSubServices() {
        return subServices; }

    public void setSubServices(ArrayList<Integer> subServices) {
        this.subServices = subServices;
    }



}
