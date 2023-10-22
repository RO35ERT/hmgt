package com.example.hotelmanagementsystem.controller;

import com.example.hotelmanagementsystem.dto.ReserveDto;
import com.example.hotelmanagementsystem.dto.UserDto;
import com.example.hotelmanagementsystem.entity.*;
import com.example.hotelmanagementsystem.repository.*;
import com.example.hotelmanagementsystem.service.PaymentService;
import com.example.hotelmanagementsystem.service.ReserveService;
import com.example.hotelmanagementsystem.service.RoomService;
import com.example.hotelmanagementsystem.service.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Base64;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/user")
public class UserController {
    @Autowired
    public RoomService roomService;

    @Autowired
    public ReserveService reserveService;

    @Autowired
    public UserRepository userRepository;

    @Autowired
    public NotificationRepository notificationRepository;

    @Autowired
    public ReserveRepository reserveRepository;

    @Autowired
    public PaymentTypeRepository paymentTypeRepository;

    @Autowired
    public PaymentService paymentService;

    @Autowired
    public UserService userService;


    @GetMapping("/room/{id}")
    public String room(@PathVariable("id") String id, Model model, Reserve reserve){
        model.addAttribute("reserve", reserve);
        Optional<Room> room = roomService.getRoom(id);
        Room room1 = room.get();
        List<Reserve> reserveList = reserveRepository.findReserveByRoomAndCheckOutDateAfterAndPaid(room1,new Date(),true);
        Date date = roomService.findLastestReserve(reserveList);
        String file = "data:image/jpeg;base64," + Base64.getEncoder().encodeToString(room1.getImage());
        model.addAttribute("imageUri", file);
        model.addAttribute("singleRoom",room1);
        model.addAttribute("reservesList",date);
        return "roomdetails";
    }

    @PostMapping("/reserve/{roomNumber}")
    public String createReserve(@ModelAttribute("reserve") ReserveDto reserve,
                                Model model, @PathVariable("roomNumber") String roomNumber,
                                HttpSession httpSession){
        String message = null;
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        UserDetails userDetails = (UserDetails)auth.getPrincipal();
        User user = userRepository.findByEmail(userDetails.getUsername());
        reserve.setRoomNumber(roomNumber);
        reserve.setUser(user);
        Reserve isSaved = reserveService.createdReserve(reserve);
        if(isSaved == null){
            message = "Choose other dates";
            model.addAttribute("reserveMessage", message);
            return "roomdetails";
        }
        else {
            httpSession.setAttribute("reservation",isSaved);
            return "redirect:/user/payment";
        }
    }

    @GetMapping("/notifications")
    public String getNotifications(Model model){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        UserDetails userDetails = (UserDetails)auth.getPrincipal();
        User user = userRepository.findByEmail(userDetails.getUsername());
        List<Notification> notifications = notificationRepository.findAllByUserOrderByDateDesc(user);
        model.addAttribute("notifications", notifications);
        return "notification";
    }

    @GetMapping("/setRead/{id}")
    public String updateNotification(@PathVariable("id") Long id){
        Notification notification = notificationRepository.findById(id).get();
        notification.setIsRead(true);
        notificationRepository.save(notification);
        return "redirect:/user/notifications";
    }

    @GetMapping("/payment")
    public String payment(HttpSession session, Model model){
        Reserve reserve = (Reserve)session.getAttribute("reservation");
        Room room = reserve.getRoom();
        model.addAttribute("reservePayment", reserve);
        model.addAttribute("roomNumber", room.getRoomNumber());
        return "payment";
    }

    @GetMapping("/payment/{reserveId}")
    public String reservePayment(@PathVariable("reserveId") Long reserveId, Model model){
        Reserve reserve = reserveRepository.findById(reserveId).get();
        Room room = reserve.getRoom();
        model.addAttribute("reservePayment", reserve);
        model.addAttribute("roomNumber", room.getRoomNumber());
        return "payment";
    }

    @PostMapping("/savePayment")
    public String createPayment(@RequestParam("paymentType") String paymentType,HttpSession session, Model model){
        Reserve reserve1 = (Reserve)session.getAttribute("reservation");
        PaymentType newPaymentType = paymentTypeRepository.findByType(paymentType);
        paymentService.createPayement(newPaymentType,reserve1);
        model.addAttribute("message", "Paid successfully!");
        return "redirect:/user/payment";
    }

    @GetMapping("/delete/notification/{id}")
    public String deleteNotification(@PathVariable("id")Long id){
        Notification notification = notificationRepository.findById(id).get();
        notification.setUser(null);
        notificationRepository.deleteById(notification.getId());
        return "redirect:/user/notifications";
    }

    @GetMapping("/reservations")
    public String getReserve(Model model){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails = (UserDetails)auth.getPrincipal();
        User user = userRepository.findByEmail(userDetails.getUsername());
        model.addAttribute("reservation",reserveRepository.findAllByUserOrderByCreatedAt(user));
        return "reservation";
    }

    @GetMapping("/profile")
    public String profile(Model model){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails = (UserDetails)auth.getPrincipal();
        User user = userRepository.findByEmail(userDetails.getUsername());
        model.addAttribute("user",user);
        UserDto userDto = new UserDto();
        model.addAttribute("userDto",userDto);
        return "profile";
    }

    @PostMapping("/updateProfile")
    public String updateProfile(@ModelAttribute("userDto") UserDto userDto){
        userService.updateUser(userDto);
        return "redirect:/user/profile";
    }

    @PostMapping("/sendForgotPasswordEmail/{email}")
    public String sendChangePasswordEmail(@PathVariable String email){
        return "redirect:/user/profile";
    }

    @PostMapping("/changePass")
    public String changePass(){
        return "admin";
    }

}
