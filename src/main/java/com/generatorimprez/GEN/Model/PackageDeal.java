package com.generatorimprez.GEN.Model;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class PackageDeal {
    static int nextId;
    int idUser;
    int packageDealId;
    String type;
    ArrayList<Integer> subServices;
    int liczbaOsob;
    float price;
    float tax;

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
        this.packageDealId = nextId;
        try {
            ResultSet rs = Postgres.Execute("select id from package_deals where user_id = " + this.idUser);
            if (!rs.next()) {
                for (int i : this.subServices)
                    Postgres.Update("insert into package_deals (id, offer_type, user_id, subservice_id) values(" + nextId + ", '" + this.type + "' ," + this.idUser + "," + i + ")");
                nextId++;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public void getPackageDeal(int userId) {
      //  ArrayList<Integer> l = new ArrayList<Integer>();
        try {
            ResultSet rs = Postgres.Execute("select subservice_id, id from package_deals where user_id = " + userId);
            while (rs.next()) {
                this.subServices.add(rs.getInt("subservice_id"));
                this.packageDealId = rs.getInt("id");}
            } catch(SQLException e){
                e.printStackTrace();
            }
     //       return l;
        }

        public void setIdUser ( int idUser){
            this.idUser = idUser;
        }


        public String getType () {
            return type;
        }

        public void setType (String username){
            this.type = (User.isAdmin(username)) ? "public" : "private";
        }

        public void setType ( boolean type){
            this.type = (type) ? "public" : "private";
        }

        public ArrayList<Integer> getSubServices () {
            return subServices;
        }

        public int getLiczbaOsob () {
            return liczbaOsob;
        }

        public void setLiczbaOsob ( int liczbaOsob){
            this.liczbaOsob = liczbaOsob;
        }

        public void setSubServices (ArrayList < Integer > subServices) {
            this.subServices = subServices;
        }

        public static int getNextId () {
            return nextId;
        }

        public int getIdUser () {
            return idUser;
        }

        public float getPrice () {
            return price;
        }

        public static boolean chckPackageDeal ( int userId){
            try {
                ResultSet rs = Postgres.Execute("select * from package_deals where user_id = " + userId);
                return (rs.next());
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return false;
        }

        public static void deletePackageDeal ( int userId){
            Postgres.Update("delete from package_deals where user_id = " + userId);
        }


        public int getPackageDealId () {
            return packageDealId;
        }

        public void setPackageDealId ( int packageDealId){
            this.packageDealId = packageDealId;
        }

    }