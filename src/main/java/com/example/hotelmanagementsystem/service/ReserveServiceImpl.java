package com.example.hotelmanagementsystem.service;

import com.example.hotelmanagementsystem.dto.Mapper;
import com.example.hotelmanagementsystem.dto.ReserveDto;
import com.example.hotelmanagementsystem.entity.Notification;
import com.example.hotelmanagementsystem.entity.Reserve;
import com.example.hotelmanagementsystem.entity.Room;
import com.example.hotelmanagementsystem.entity.User;
import com.example.hotelmanagementsystem.repository.NotificationRepository;
import com.example.hotelmanagementsystem.repository.ReserveRepository;
import com.example.hotelmanagementsystem.repository.RoomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Service
public class ReserveServiceImpl implements ReserveService {
    private final ReserveRepository repository;

    private final RoomRepository roomRepository;

    private final NotificationRepository notificationRepository;

    @Autowired
    public ReserveServiceImpl(ReserveRepository repository, RoomRepository roomRepository, NotificationRepository notificationRepository) {
        this.repository = repository;
        this.roomRepository = roomRepository;
        this.notificationRepository = notificationRepository;
    }

    @Override
    public List<Reserve> findAllReserveByUser(User user) {
        return repository.findAllByUserOrderByCreatedAt(user);
    }

    @Override
    public Reserve createdReserve(ReserveDto reserve) {
        Room room = roomRepository.findByRoomNumber(reserve.getRoomNumber());

        Reserve toNewReserve = null;
        try {
            toNewReserve = Mapper.toReserve(reserve);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
        Reserve newReserve = new Reserve();
        newReserve.setCreatedAt(new Date());
        newReserve.setRoom(room);
        newReserve.setPaid(false);
        newReserve.setTotalAmount(toNewReserve.getTotalAmount());
        newReserve.setCheckOutDate(toNewReserve.getCheckOutDate());
        newReserve.setCheckInDate(toNewReserve.getCheckInDate());
        newReserve.setUser(reserve.getUser());
        sendNotification(reserve);
        return repository.save(newReserve);
    }

    private void sendNotification(ReserveDto reserve) {
        Notification notification = new Notification();
        notification.setIsRead(false);
        notification.setDate(new Date());
        notification.setUser(reserve.getUser());
        notification.setNotification("Hello, your reservation of " + reserve.getRoomNumber() +" has been created successfully");
        notificationRepository.save(notification);
    }

    @Override
    public Reserve findReserveByRoomBefore(Room room) {
        return null;
    }

    @Override
    public Double countPendingReserveByMonth(String date) {
        Double sum = 0.0;
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        try{
            Date start =  dateFormat.parse(date+"-01");
            Date end =  dateFormat.parse(date+"-31");
            sum = repository.countByBetweenPending(start, end);
        }catch (Exception e){
            System.out.println(e);
        }
        return sum;
    }

    @Override
    public Double countAllReserveByMonth(String date) {
        Double sum = 0.0;
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        try{
            Date start =  dateFormat.parse(date+"-01");
            Date end =  dateFormat.parse(date+"-31");
            sum = repository.countAllReserveByBetween(start, end);
        }catch (Exception e){
            System.out.println(e);
        }
        return sum;
    }


}
