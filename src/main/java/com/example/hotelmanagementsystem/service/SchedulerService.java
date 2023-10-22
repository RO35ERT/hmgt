package com.example.hotelmanagementsystem.service;

import com.example.hotelmanagementsystem.config.EmailSender;
import com.example.hotelmanagementsystem.entity.Booking;
import com.example.hotelmanagementsystem.entity.Reserve;
import com.example.hotelmanagementsystem.entity.Room;
import com.example.hotelmanagementsystem.entity.User;
import com.example.hotelmanagementsystem.repository.BookingRepository;
import com.example.hotelmanagementsystem.repository.ReserveRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;

@Service
public class SchedulerService {
    @Autowired
    private BookingRepository bookingRepository;

    @Autowired
    private ReserveRepository reserveRepository;

    @Autowired
    private EmailSender emailSender;

    @Scheduled(fixedRate = 43200000)
    public void addPenalty(){
        List<Booking> bookingList = bookingRepository.findAll();
        for(Booking booking: bookingList){
            Reserve reserve = booking.getReserve();
            Room room = reserve.getRoom();
            if(room.getStatus() && new Date().after(reserve.getCheckOutDate())){
                Booking booking1 = bookingRepository.findByReserve(reserve);
                booking1.setPenalty(booking1.getPenalty()+room.getPrice()+(0.10*room.getPrice()));
                bookingRepository.save(booking1);
            }
        }
    }

    @Scheduled(fixedRate = 43200000)
    public void checkOutReminder(){
        List<Reserve> reserves = reserveRepository.findAllByPaid(true);
        for(Reserve reserve: reserves){
            User user = reserve.getUser();
            Date date = reserve.getCheckOutDate();
            Instant instant = date.toInstant();
            LocalDate localDate = instant.atZone(ZoneId.systemDefault()).toLocalDate();
            LocalDate newLocalDate = localDate.minusDays(1);
            Date date1 = new Date();
            Instant newInstant = date1.toInstant();
            LocalDate now = newInstant.atZone(ZoneId.systemDefault()).toLocalDate();
            if(newLocalDate.isEqual(now)){
                emailSender.sendEmailReminder(user,date);
            }
        }
    }
}
