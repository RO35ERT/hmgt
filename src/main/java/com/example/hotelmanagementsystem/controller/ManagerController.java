package com.example.hotelmanagementsystem.controller;

import com.example.hotelmanagementsystem.dto.RoomDto;
import com.example.hotelmanagementsystem.entity.Branch;
import com.example.hotelmanagementsystem.entity.Notification;
import com.example.hotelmanagementsystem.entity.Room;
import com.example.hotelmanagementsystem.entity.User;
import com.example.hotelmanagementsystem.repository.*;
import com.example.hotelmanagementsystem.service.InvoiceService;
import com.example.hotelmanagementsystem.service.NotificationService;
import com.example.hotelmanagementsystem.service.RoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.text.DecimalFormat;
import java.util.Collections;
import java.util.List;

@Controller
@RequestMapping("/manager")
public class ManagerController {

    @Autowired
    public RoomService roomService;

    @Autowired
    public NotificationService notificationService;

    @Autowired
    public NotificationRepository notificationRepository;

    @Autowired
    public RoomRepository roomRepository;

    @Autowired
    public ReserveRepository reserveRepository;

    @Autowired
    public BookingRepository bookingRepository;

    @Autowired
    public InvoiceRepository invoiceRepository;

    @Autowired
    public InvoiceService invoiceService;

    @Autowired
    public UserRepository userRepository;
    @GetMapping("/")
    public String getAnalytics(Model model){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails = (UserDetails)auth.getPrincipal();
        String username = userDetails.getUsername();

        User user = userRepository.findByEmail(username);
        Branch branch = user.getBranch();
        List<Room> roomList = roomRepository.findAllByBranch(branch);
        List<User> users = userRepository.findAllByBranch(branch);
        Long totalRooms = roomRepository.countAllByBranch(branch);
        model.addAttribute("branch",branch.getBranch());
        model.addAttribute("users",users);
        model.addAttribute("totalRooms",totalRooms);
        model.addAttribute("totalReserves",reserveRepository.countAllReservesByBranch(branch.getBranch()));
        model.addAttribute("allRooms",roomList);
        RoomDto roomDto = new RoomDto();
        model.addAttribute("roomDto", roomDto);
        model.addAttribute("reserveCount", reserveRepository.countAllPendingReservesByBranch(branch.getBranch()));
        model.addAttribute("sum",reserveRepository.getTotalAmountByBranchSumWherePaidIsTrue(branch.getBranch()));
        Long occupied = roomRepository.countAllByBranchAndStatus(branch,true);
        DecimalFormat df = new DecimalFormat("#.##");
        Double occupiedOverTotalRooms = (((double)occupied/(double)totalRooms)*100);
        model.addAttribute("occupied",occupied);
        model.addAttribute("roomIncrease",df.format(occupiedOverTotalRooms)+"%");
        findPaginated(1, model);
        return "manager";
    }

    @GetMapping("/page/{pageNo}")
    public String findPaginated(@PathVariable(value = "pageNo") int pageNo, Model model){
        int pageSize = 11;
        Page<Notification> page = notificationService.findPaginated(pageNo,pageSize);
        List<Notification>  notifications = page.getContent();
        model.addAttribute("cPage",pageNo);
        model.addAttribute("totalPages",page.getTotalPages());
        model.addAttribute("allNotification", notifications);
        return "admin";
    }
}
