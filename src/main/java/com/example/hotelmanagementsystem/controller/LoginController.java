package com.example.hotelmanagementsystem.controller;

import com.example.hotelmanagementsystem.dto.UserDto;
import com.example.hotelmanagementsystem.entity.User;
import com.example.hotelmanagementsystem.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@Controller
public class LoginController {


    private final UserService userService;



    @Autowired
    public LoginController(UserService userService) {
        this.userService = userService;
    }

    @RequestMapping("/login")
    public String loginForm() {
        return "login";
    }

    @GetMapping("/registration")
    public String registrationForm(Model model) {
        UserDto user = new UserDto();
        model.addAttribute("user", user);
        return "registration";
    }

    @PostMapping("/registration")
    public String registration(
            @Valid @ModelAttribute("user") UserDto userDto,
            BindingResult result,
            Model model) {
        User existingUser = userService.findUserByEmail(userDto.getEmail());

        if (existingUser != null) {
            result.rejectValue("email", null,
                    "User already registered !!!");
        }
        if (result.hasErrors()) {
            model.addAttribute("user", userDto);
            return "/registration";
        }

        userService.saveUser(userDto);
        return "redirect:/confirmPage";
    }

    @GetMapping("/confirmPage")
    public String confirmPage(){
        return "confirmPage";
    }
    @GetMapping("/confirm")
    public String confirmToken(Model model, @RequestParam("token") String token){
        if(userService.confirmToken(token)){
            model.addAttribute("msg","success");
            return "confirm";
        }else{
            model.addAttribute("msg","failure");
            model.addAttribute("token", token);
            return "confirm";
        }
    }

    @GetMapping("/resendToken")
    public String resend(@ModelAttribute("token") String token){
        userService.resendToken(token);
        return "redirect:/login";
    }

    SecurityContextLogoutHandler logoutHandler = new SecurityContextLogoutHandler();

    @GetMapping("/logout")
    public String logout(Authentication authentication, HttpServletRequest request, HttpServletResponse response){
        this.logoutHandler.logout(request, response, authentication);
        return "redirect:/";
    }
}
