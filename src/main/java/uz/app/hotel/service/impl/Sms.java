package uz.app.hotel.service.impl;

import uz.app.hotel.entity.User;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

public class Sms {
    static Properties props = new Properties();
    static {
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");
    }
    public static void send(User user) {
        Session session = Session.getInstance(
                props,
                new Authenticator() {
                    @Override
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication("soliqsoliqsawka34@gmail.com","ystwqoowrqwpgznz");
                    }
                }
        );
        try {
            MimeMessage message = new MimeMessage(session);
            message.setFrom(new InternetAddress("soliqsoliqsawka34@gmail.com"));
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(user.getEmail()));
            message.setSubject("Please confirm your accaunt");
            message.setText(user.getCode());
            Transport.send(message);
            System.out.println("successfully sent, please check your email");
        } catch (Exception e){
        }

    }
}
