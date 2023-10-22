package com.example.hotelmanagementsystem.controller;

import com.example.hotelmanagementsystem.dto.Mapper;
import com.example.hotelmanagementsystem.dto.ToRoomDto;
import com.example.hotelmanagementsystem.entity.Room;
import com.example.hotelmanagementsystem.entity.User;
import com.example.hotelmanagementsystem.repository.NotificationRepository;
import com.example.hotelmanagementsystem.repository.ReserveRepository;
import com.example.hotelmanagementsystem.repository.UserRepository;
import com.example.hotelmanagementsystem.service.RoomService;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class AuthController {
    @Autowired
    public RoomService roomService;

    @Autowired
    public NotificationRepository notificationRepository;

    @Autowired
    public ReserveRepository reserveRepository;

    @Autowired
    public UserRepository userRepository;


    @GetMapping("/")
    public String home(Model model, HttpSession http){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = null;
        if(auth.getName().isEmpty()){
            UserDetails userDetails = (UserDetails)auth.getPrincipal();
            user = userRepository.findByEmail(userDetails.getUsername());
            model.addAttribute("notificationCount", notificationRepository.countNotificationByUserAndIsRead(user,false));
            model.addAttribute("reserveCount", reserveRepository.countReserveByUserAndPaidIs(user, false));
            System.out.println("Hello");
        }else{
            auth = null;
        }
        List<Room> rooms = roomService.getRooms();
        List<ToRoomDto> toRoomDto = Mapper.getRoomDto(rooms);
        model.addAttribute("rooms",toRoomDto);
        findPaginated(1,model);
        return "index";
    }

    @GetMapping("/page/{pageNo}")
    public String findPaginated(@PathVariable(value = "pageNo") int pageNo, Model model){
        int pageSize = 8;
        Page<Room> page = roomService.findPaginated(pageNo,pageSize);
        List<Room>  rooms = page.getContent();
        List<ToRoomDto> toRoomDto = Mapper.getRoomDto(rooms);
        model.addAttribute("cPage",pageNo);
        model.addAttribute("totalPages",page.getTotalPages());
        model.addAttribute("cRoom", toRoomDto);
        return "index";
    }

}
