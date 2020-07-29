package com.generatorimprez.GEN.Model;

import java.util.ArrayList;

public class SubServiceList {

    String subServiceName;
   ArrayList<String> subServiceNames;

   {
       subServiceNames = new ArrayList<String>();
       subServiceName = new String();
   }

    public String getSubServiceName() {
        return subServiceName;
    }

    public void setSubServiceName(String subServiceName) {
        this.subServiceName = subServiceName;
        subServiceNames.add(subServiceName);
    }

    public ArrayList<String> getSubServiceNames() {
        return subServiceNames;
    }

}
