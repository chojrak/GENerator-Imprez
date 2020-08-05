package com.generatorimprez.GEN.Model;

import java.sql.ResultSet;
import java.sql.SQLException;

public class PromoCode {
    String code;
    String type;
    int amount;
    int value;
    static int nextId;

    static {
        try {
            ResultSet rs = Postgres.Execute("select case when (select max(id) from promo_codes) is null then 0 else (select max(id) from package_deals) end +1 id");
            while (rs.next()) nextId = rs.getInt("id");
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public PromoCode() {
    }

    public PromoCode(String code, String type, int value) {
        this.code = code;
        this.type = type;
        this.value = value;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public void addPromoCodes() {
        Postgres.Update("do $$ begin for x in "+nextId+".."+(this.amount+nextId)+" loop insert into promo_codes (id, code, value) values (x, '"+this.code+"', "+this.value+"); raise notice '%' , x; end loop; end $$;");
    }

    public void deletePromoCodes() {
        Postgres.Update("delete from promo_codes p where p.code like '"+this.code+"'");
    }

    public boolean chckPromoCode() {
        try {
            ResultSet rs = Postgres.Execute("select * from promo_codes p where p.code like '"+this.code+"'");
            return (rs.next());
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return false;
    }


}
