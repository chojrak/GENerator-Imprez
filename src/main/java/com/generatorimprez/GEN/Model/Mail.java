package com.generatorimprez.GEN.Model;

import org.simplejavamail.mailer.internal.util.SmtpAuthenticator;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

public class Mail {

    private static Server server;

    static {
        server = new Server();
    }

    public static void sendMail(String subject, String message, String address) {
            try {
                Authenticator auth = new SmtpAuthenticator(server);

                Properties prop = System.getProperties();
                prop.put("mail.smtp.user", server.data.get("username"));
                prop.put("mail.smtp.password", server.data.get("password"));
                prop.put("mail.smtp.host", server.host);
                prop.put("mail.smtp.auth", "true");
                prop.put("mail.smtp.ssl.enable", "true");
                Session session = Session.getInstance(prop, auth);


                MimeMessage msg = new MimeMessage(session);
                msg.setFrom(new InternetAddress("no-reply@chojrak.com", "no-reply"));
                msg.setReplyTo(InternetAddress.parse("no_reply@example.com", false));
                msg.setSubject(subject, "UTF-8");
                msg.setText(message, "UTF-8");

                msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(address, false));
                Transport.send(msg);
            } catch (Exception e) {
                e.printStackTrace();
            }

        }


    }



