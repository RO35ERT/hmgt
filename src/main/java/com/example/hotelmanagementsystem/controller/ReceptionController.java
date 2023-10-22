package com.example.hotelmanagementsystem.controller;

import com.example.hotelmanagementsystem.entity.*;
import com.example.hotelmanagementsystem.repository.BookingRepository;
import com.example.hotelmanagementsystem.repository.InvoiceRepository;
import com.example.hotelmanagementsystem.repository.RoomRepository;
import com.example.hotelmanagementsystem.repository.UserRepository;
import com.example.hotelmanagementsystem.service.RoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.Banner;
import org.springframework.data.repository.query.Param;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/reception")
@Controller
public class ReceptionController {

    @Autowired
    public UserRepository userRepository;

    @Autowired
    public RoomService roomService;

    @Autowired
    public RoomRepository roomRepository;

    @Autowired
    public InvoiceRepository invoiceRepository;

    @Autowired
    public BookingRepository bookingRepository;

    @GetMapping("/")
    public String showRec(Model model, @Param("search") String search){

        //get the current user
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails = (UserDetails)auth.getPrincipal();
        User user = userRepository.findByEmail(userDetails.getUsername());
        model.addAttribute("rooms",roomService.getRoomInvoice(user.getBranch(),search));

        return "reception";
    }

    @GetMapping("/checkin/{invoiceId}")
    public String checkIn(@PathVariable("invoiceId") String invoiceId){
        Invoice invoice = invoiceRepository.findByInvoiceNo(invoiceId);
        Reserve reserve = invoice.getReserve();
        Room room = reserve.getRoom();
        room.setStatus(true);
        roomRepository.save(room);

        Booking booking = bookingRepository.findByReserve(reserve);
        booking.setAdmit(true);
        booking.setCheckOut(false);
        bookingRepository.save(booking);
       return "redirect:/reception/";
    }

    @GetMapping("/checkout/{invoiceId}")
    public String checkOut(@PathVariable("invoiceId") String invoiceId){
        Invoice invoice = invoiceRepository.findByInvoiceNo(invoiceId);
        Reserve reserve = invoice.getReserve();
        Room room = reserve.getRoom();
        room.setStatus(false);
        roomRepository.save(room);

        Booking booking = bookingRepository.findByReserve(reserve);
        booking.setAdmit(true);
        booking.setCheckOut(true);
        bookingRepository.save(booking);
        return "redirect:/reception/";
    }


}
