package com.generatorimprez.GEN.Model;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.TreeMap;

public class FinalOffer extends PackageDeal {
    String promoCode;
    //   String type;
    int promoCodeValue;


    public FinalOffer() {
    }

    public FinalOffer(PackageDeal packageDeal) {
        super.subServices = packageDeal.getSubServices();
        super.idUser = packageDeal.getIdUser();
        super.packageDealId = packageDeal.getPackageDealId();
        //   super.type = packageDeal.getType();
    }


    public FinalOffer(PackageDeal packageDeal, int liczbaOsob, String promoCode) {
        super.subServices = packageDeal.getSubServices();
        super.idUser = packageDeal.getIdUser();
        //      super.type = packageDeal.getType();
        super.liczbaOsob = liczbaOsob;
        this.promoCode = promoCode;
    }

    public String getPromoCode() {
        return promoCode;
    }

    public void setPromoCode(String promoCode) {
        try {
            ResultSet rs = Postgres.Execute("select * from promo_codes where code = '" + promoCode + "'");
            while (rs.next()) {
                this.promoCode = promoCode;
                //   this.type = rs.getString("type");
                this.promoCodeValue = rs.getInt("value");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public TreeMap<String, TreeMap<String, Float>> getPrices() {

        TreeMap<String, TreeMap<String, Float>> names = new TreeMap<String, TreeMap<String, Float>>();
        StringBuilder sb = new StringBuilder();
        sb.append("(");
        for (int a : this.subServices) {
            sb.append(a);
            sb.append(", ");
        }
        sb.deleteCharAt(sb.lastIndexOf(","));
        sb.append(")");

        try {
            ResultSet rs = Postgres.Execute("select s.name serwis, sum(case when ss.pricing = 'pp' then ss.price * " + this.liczbaOsob + " else ss.price end) price, sum(case when ss.pricing = 'pp' then price * " + this.liczbaOsob + "*ss.tax /100 else price* ss.tax/100 end) tax from subservices ss, services s where s.id = ss.service_id and ss.id in" + sb.toString() + " group by s.name");
            while (rs.next()) {
                TreeMap<String, Float> prices = new TreeMap<String, Float>();
                float p = rs.getFloat("price");
                float t = rs.getFloat("tax");
                prices.put(" +", p);
                prices.put("VAT = ", t);
                prices.put("ZŁ", p + t);
                if(p > 0) names.put(rs.getString("serwis"), prices);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return names;
    }


    public float getTax() {
        float tax = 0;
        StringBuilder sb = new StringBuilder();
        sb.append("(");
        for (int a : this.subServices) {
            sb.append(a);
            sb.append(", ");
        }
        sb.deleteCharAt(sb.lastIndexOf(","));
        sb.append(")");

        try {
            ResultSet rs = Postgres.Execute("select round(sum(case when ss.pricing = 'pp' then ss.price * " + this.liczbaOsob + " * ss.tax / 100 else ss.price * ss.tax end), 2) price from subservices ss, services s where s.id = ss.service_id and ss.id in" + sb.toString());
            while (rs.next()) tax = rs.getFloat("price");
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return tax;

    }

    public ArrayList<String> getServiceList() {
        ArrayList<String> lista = new ArrayList<String>();
        StringBuilder sb = new StringBuilder();
        sb.append("(");
        for (int a : this.subServices) {
            sb.append(a);
            sb.append(", ");
        }
        sb.deleteCharAt(sb.lastIndexOf(","));
        sb.append(")");

        try {
            ResultSet rs = Postgres.Execute("select distinct s.name from subservices ss, services s where s.id = ss.service_id and ss.id in" + sb.toString());
            while (rs.next()) lista.add(rs.getString("name"));
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return lista;

    }

    public TreeMap<String, String> getDescriptions() {
        TreeMap<String, String> descriptions = new TreeMap<String, String>();
        StringBuilder sb = new StringBuilder();
        sb.append("with lista as (select s.name serviceName, ss.description, row_number()over(partition by s.name order by ss.description) miejsce from subservices ss, services s where s.id = ss.service_id and ss.id in (");
        for (int a : this.subServices) {
            sb.append(a);
            sb.append(", ");
        }
        sb.deleteCharAt(sb.lastIndexOf(","));
        sb.append(")");
        sb.append("and ss.description is not null) \n select baza.serviceName, coalesce(baza.description, ' ')||' '||coalesce(m2.description, ' ')||' '||coalesce(m3.description, ' ')||' '||\n");
        sb.append("coalesce(m4.description, ' ')||' '||coalesce(m5.description, ' ')||' '||coalesce(m6.description, ' ')||' '||coalesce(m7.description, ' ')||' '||coalesce(m8.description, ' ')||' '||coalesce(m9.description, ' ')||' '||coalesce(m10.description, ' ') description \n");
        sb.append("from (select * from lista where miejsce = 1) baza \n");
        for (int i = 2; i <= 10; i++) {
            sb.append("left join (select * from lista where miejsce = ");
            sb.append(i);
            sb.append(") m");
            sb.append(i);
            sb.append(" on m");
            sb.append(i);
            sb.append(".serviceName = baza.serviceName \n");
        }

        for (int a : this.subServices) {
            try {
                ResultSet rs = Postgres.Execute(sb.toString());
                while (rs.next()) if(!rs.getString("description").trim().isEmpty()) descriptions.put(rs.getString("serviceName"), rs.getString("description").trim());
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
        return descriptions;
    }


    public int getPromoCodeValue() {
        return promoCodeValue;
    }

    public void setPromoCodeValue(int promoCodeValue) {
        this.promoCodeValue = promoCodeValue;
    }

    public void getOfferById() {
        try {
            ResultSet rs = Postgres.Execute("select pd.subservice_id from package_deals pd, subservices ss where pd.subservice_id = ss.id and pd.id = " + this.packageDealId);
            while (rs.next()) this.subServices.add(rs.getInt("subservice_id"));
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

    }

    public void closeDeal() {
        Postgres.Update("insert into final_offers (promo_code, promo_code_value, package_deal_id, number_of_people) values ('" + this.promoCode + "', " + this.promoCodeValue + ", " + this.packageDealId +", "+this.liczbaOsob+ ")");
        PromoCode.deletePromoCode(this.promoCode);
    }

    public void getFinalOffer(String username) {
        try {
            ResultSet rs = Postgres.Execute("select f.promo_code_value, f.number_of_people from users u, package_deals p, final_offers f where u.id = p.user_id and f.package_deal_id = p.id and u.username ='" + username + "'");
            while (rs.next()) {
                this.liczbaOsob = rs.getInt("number_of_people");
                this.promoCodeValue = rs.getInt("promo_code_value");
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public float getFinalPrice(String username){
        float price = 0;
        StringBuilder sb = new StringBuilder();
        sb.append("(");
        for (int a : this.subServices) {
            sb.append(a);
            sb.append(", ");
        }
        sb.deleteCharAt(sb.lastIndexOf(","));
        sb.append(")");

        try {
            ResultSet rs = Postgres.Execute("select sum(case when ss.pricing = 'pp' then ss.price * " + this.liczbaOsob + " else ss.price end + case when ss.pricing = 'pp' then price * " + this.liczbaOsob + "*ss.tax /100 else price* ss.tax/100 end) -"+this.promoCodeValue+" price from subservices ss, services s where s.id = ss.service_id and ss.id in" + sb.toString());
            while (rs.next()) price = rs.getFloat("price");
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return price;
    }

    public float getFinalPrice(){
        float price = 0;
        StringBuilder sb = new StringBuilder();
        sb.append("(");
        for (int a : this.subServices) {
            sb.append(a);
            sb.append(", ");
        }
        sb.deleteCharAt(sb.lastIndexOf(","));
        sb.append(")");

        try {
            ResultSet rs = Postgres.Execute("select sum(case when ss.pricing = 'pp' then ss.price * " + this.liczbaOsob + " else ss.price end + case when ss.pricing = 'pp' then price * " + this.liczbaOsob + "*ss.tax /100 else price* ss.tax/100 end) -"+this.promoCodeValue+" price from subservices ss, services s where s.id = ss.service_id and ss.id in" + sb.toString());
            while (rs.next()) price = rs.getFloat("price");
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        if (price<0) return 0;
        else return price;
    }


}
