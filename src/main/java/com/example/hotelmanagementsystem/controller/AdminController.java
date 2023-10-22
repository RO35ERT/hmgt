package com.example.hotelmanagementsystem.controller;

import com.example.hotelmanagementsystem.dto.Mapper;
import com.example.hotelmanagementsystem.dto.RoomDto;
import com.example.hotelmanagementsystem.dto.ToRoomDto;
import com.example.hotelmanagementsystem.entity.Branch;
import com.example.hotelmanagementsystem.entity.Notification;
import com.example.hotelmanagementsystem.entity.Room;
import com.example.hotelmanagementsystem.repository.*;
import com.example.hotelmanagementsystem.service.BranchService;
import com.example.hotelmanagementsystem.service.InvoiceService;
import com.example.hotelmanagementsystem.service.NotificationService;
import com.example.hotelmanagementsystem.service.RoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.Banner;
import org.springframework.data.domain.Page;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.text.DecimalFormat;
import java.util.*;

@Controller
@RequestMapping("/admin")
public class AdminController {
    public String message;
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
    public BranchService branchService;

    @Autowired
    public BranchRepository branchRepository;

    @GetMapping("/")
    public String showAdminPage(Model model){
        List<Notification> notificationList = notificationRepository.findAll();
        List<Room> roomList = roomRepository.findAll();
        Long totalRooms = roomRepository.count();
        model.addAttribute("totalRooms",totalRooms);
        model.addAttribute("totalReserves",reserveRepository.count());
        Collections.reverse(notificationList);
        model.addAttribute("allNotification", notificationList);
        model.addAttribute("allRooms",roomList);
        RoomDto roomDto = new RoomDto();
        model.addAttribute("roomDto", roomDto);
        model.addAttribute("reserveCount", reserveRepository.countReserveByPaidIs(false));
        model.addAttribute("sum",reserveRepository.getTotalAmountSumWherePaidIsTrue());

        Long occupied = roomRepository.countAllByStatus(true);
        DecimalFormat df = new DecimalFormat("#.##");
        Double occupiedOverTotalRooms = (((double)occupied/(double)totalRooms)*100);
        model.addAttribute("occupied",occupied);
        model.addAttribute("roomIncrease",df.format(occupiedOverTotalRooms)+"%");
        Branch branch = new Branch();
        model.addAttribute("branch",branch);
        return "admin";
    }
    @GetMapping("/addroom")
    public String addRoomPage(Model model){
        RoomDto roomDto = new RoomDto();
        model.addAttribute("roomDto", roomDto);
        model.addAttribute("message", message);
        model.addAttribute("branches",branchRepository.findAll());
        message="";
        return "addroom";
    }
    @GetMapping("/deleteroom/{roomNumber}")
    public String deleteRoom(@PathVariable("roomNumber") String roomNumber){
        Room room = roomRepository.findByRoomNumber(roomNumber);
        room.setRoomType(null);
        roomRepository.delete(room);
        return "redirect:/admin/";
    }

    @PostMapping("/createroom")
    public String createRoom(@ModelAttribute("roomDto") RoomDto roomDto, Model model){
        if(!roomDto.getImage().isEmpty()){
            try{
                byte[] imageData = roomDto.getImage().getBytes();
            }catch (Exception e){
                System.out.println(e);
            }
        }

        roomService.createRoom(roomDto);
        if(roomService.createRoom(roomDto) != null){
            message= "Room created successfully";
        }
        return "redirect:/admin/addroom";
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

    @PostMapping("/updateroom")
    public String updateRoom(@ModelAttribute("roomDto") RoomDto roomDto, Model model){
        roomService.updateRoom(roomDto, roomDto.getRoomNumber());

        return "redirect:/admin/";
    }

    @PostMapping("/createbranch")
    public String createBranch(@Param("branch") String branch){
        branchService.createBranch(branch);
        return "redirect:/admin/";
    }

}
