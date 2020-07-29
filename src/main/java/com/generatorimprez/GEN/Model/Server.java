package com.generatorimprez.GEN.Model;

import org.simplejavamail.api.mailer.config.ServerConfig;

import javax.net.ssl.SSLSocketFactory;
import java.io.*;
import java.util.HashMap;

public class Server implements ServerConfig {
    String host = "smtp.gmail.com";
    int port = 465;
    HashMap<String, String> data;


    public Server() {
        this.data = readFile();
    }

    @Override
    public String getHost() {
        return host;
    }


    @Override
    public Integer getPort() {
        return port;
    }


    @Override
    public String getUsername() {
        return data.get("username");
    }


    @Override
    public String getPassword() {
        return data.get("password");
    }


    @Override
    public String getCustomSSLFactoryClass() {
        return null;
    }


    @Override
    public SSLSocketFactory getCustomSSLFactoryInstance() {
        return null;
    }

    public static HashMap<String, String> readFile() {
        HashMap<String, String> data = new HashMap<String, String>();
        try {
            FileInputStream fis = new FileInputStream("src\\main\\resources\\static\\gmail.jpg");
            ObjectInputStream ois = new ObjectInputStream(fis);
            HashMap<String, char[]> file = (HashMap<String, char[]>) ois.readObject();

            ois.close();
            fis.close();
            for (String str : file.keySet()) data.put(str, Pass.decodeText(file.get(str)));

        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return data;
    }

}
