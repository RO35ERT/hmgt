package com.example.hotelmanagementsystem.config;

import com.example.hotelmanagementsystem.entity.Reserve;
import com.example.hotelmanagementsystem.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class EmailSender {
    private final JavaMailSender javaMailSender;

    @Autowired
    public EmailSender(JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }

    public void sendEmail(User user, String token){
        SimpleMailMessage simpleMailMessage =
                new SimpleMailMessage();
        simpleMailMessage.setSubject("Confirmation Email");
        simpleMailMessage.setTo(user.getEmail());
        simpleMailMessage.setText(
                "Click " + "http://localhost:8080/confirm?token="+token + " to confirm your account \n"
                +"This message will expire in 15 minutes"
        );
        javaMailSender.send(simpleMailMessage);
    }

    public void sendPaymentEmail(User user, Reserve reserve){
        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setTo(user.getEmail());
        simpleMailMessage.setText(
                "Hello, we are pleased to have you at Haven hotel, your payment of "+
                reserve.getTotalAmount() + " has been successfully processed."+" You "
                + "can check in on " + reserve.getCheckInDate() + " and checkout on "+ reserve.getCheckOutDate()+
                        " in room number "+ reserve.getRoom().getRoomNumber()+ " Matero branch along mbulwe road"+ "\n"+". Enjoy your stay. \n"+ " For" +
                        " queries call +260 962418322/+260973479021" + "Haven"
        );
        simpleMailMessage.setSubject("Payment");

        javaMailSender.send(simpleMailMessage);
    }

    public void sendEmailReminder(User user, Date date){
        SimpleMailMessage simpleMailMessage =
                new SimpleMailMessage();
        simpleMailMessage.setSubject("Checkout Reminder");
        simpleMailMessage.setTo(user.getEmail());
        simpleMailMessage.setText(
               "Please make sure that you checkout on "+ date+". Please note that any delays will attract a penalty " +
                       " fee of 10% per day spent."
        );
        javaMailSender.send(simpleMailMessage);
    }

    public void sendForgotPassEmail(User user){
        SimpleMailMessage simpleMailMessage =
                new SimpleMailMessage();
        simpleMailMessage.setSubject("Reset Password");
        simpleMailMessage.setTo(user.getEmail());
        simpleMailMessage.setText(
                "Click " + "http://localhost:8080/"
                        +" to reset your password"
        );
        javaMailSender.send(simpleMailMessage);
    }

}
