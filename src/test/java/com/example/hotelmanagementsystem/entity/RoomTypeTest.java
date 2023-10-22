package com.example.hotelmanagementsystem.entity;

import com.example.hotelmanagementsystem.dto.Mapper;
import com.example.hotelmanagementsystem.dto.RoomInvoiceDto;
import com.example.hotelmanagementsystem.dto.UserDto;
import com.example.hotelmanagementsystem.repository.*;
import com.example.hotelmanagementsystem.service.InvoiceService;
import com.example.hotelmanagementsystem.service.ReserveService;
import com.example.hotelmanagementsystem.service.RoomService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class RoomTypeTest {

    @Autowired
    public ReserveRepository tokenRepository;

    @Autowired
    public NotificationRepository notificationRepository;

    @Autowired
    public UserRepository userRepository;

    @Autowired
    public ReserveRepository reserveRepository;

    @Autowired
    public RoomRepository roomRepository;

    @Autowired
    public BranchRepository branchRepository;

    @Autowired
    public RoomService roomService;

    @Autowired
    public InvoiceRepository invoiceRepository;

    @Autowired
    public BookingRepository bookingRepository;

    @Autowired
    public ReserveService reserveService;

    @Autowired
    public InvoiceService invoiceService;

    @Test
    @Transactional
    public void showType() throws ParseException {
        List<Reserve> reserves = reserveRepository.findAllByPaid(true);

//        for(Reserve reserve: reserves){
//            User user = reserve.getUser();
//            Date date = reserve.getCheckOutDate();
//            Instant instant = date.toInstant();
//            LocalDate localDate = instant.atZone(ZoneId.systemDefault()).toLocalDate();
//            LocalDate newLocalDate = localDate.minusDays(1);
//            Date date1 = new Date();
//            Instant newInstant = date1.toInstant();
//            LocalDate now = newInstant.atZone(ZoneId.systemDefault()).toLocalDate();
//            if(newLocalDate.isEqual(now)){
//                System.out.println("Hello, User");
//            }
//        }
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
//        System.out.println(invoiceRepository.getTotalBetweenStartAndEnd(dateFormat.parse("2023-10-01"),dateFormat.parse("2023-10-31")));

        System.out.println(reserveService.countPendingReserveByMonth("2023-11"));
    }
}