package com.example.hotelmanagementsystem.service;

import com.example.hotelmanagementsystem.config.EmailSender;
import com.example.hotelmanagementsystem.dto.UserDto;
import com.example.hotelmanagementsystem.entity.ConfirmationToken;
import com.example.hotelmanagementsystem.entity.Notification;
import com.example.hotelmanagementsystem.entity.Role;
import com.example.hotelmanagementsystem.entity.User;
import com.example.hotelmanagementsystem.repository.NotificationRepository;
import com.example.hotelmanagementsystem.repository.RoleRepository;
import com.example.hotelmanagementsystem.repository.TokenRepository;
import com.example.hotelmanagementsystem.repository.UserRepository;
import com.example.hotelmanagementsystem.util.TbConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class UserServiceImpl implements UserService{
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private EmailSender emailSender;

    @Autowired
    private TokenRepository tokenRepository;

    @Autowired
    private NotificationRepository notificationRepository;

    @Override
    public void saveUser(UserDto userDto) {
        Role role = roleRepository.findByName(TbConstants.Roles.USER);

        if (role == null)
            role = roleRepository.save(new Role(TbConstants.Roles.USER));

        User user = new User(userDto.getName(), userDto.getEmail(),
                passwordEncoder.encode(userDto.getPassword()),
                userDto.getLastName(),
                userDto.getMiddleName(),
                userDto.getResidential(),
                userDto.getPostal(),
                false,
                List.of(role));

        ConfirmationToken confirmationToken =
                new ConfirmationToken();
        confirmationToken.setExpiration(calculateExpirationTime());
        confirmationToken.setToken(generateToken());
        confirmationToken.setUser(user);

        userRepository.save(user);
        tokenRepository.save(confirmationToken);
        emailSender.sendEmail(user, confirmationToken.getToken());

    }

    @Override
    public User findUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public User findUserAndEnabled(String email, Boolean isEnabled) {
        return userRepository.findByEmailAndIsEnabled(email,isEnabled);
    }

    @Override
    public Boolean confirmToken(String token) {
        ConfirmationToken token1 = tokenRepository.findByToken(token);
        if(isTokenExpired(token1)){
            User user = token1.getUser();
            user.setIsEnabled(true);
            userRepository.save(user);
            Notification notification = new Notification();
            notification.setNotification("Hello, "+user.getName() +" welcome to our hotel.");
            notification.setDate(new Date());
            notification.setUser(user);
            notification.setIsRead(false);
            notificationRepository.save(notification);
            return true;
        }
        return false;
    }


    public String generateToken(){
        return UUID.randomUUID().toString();
    }
    public Date calculateExpirationTime(){
        Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("UTC+2"));
        calendar.add(Calendar.MINUTE,15);
        return calendar.getTime();
    }

    public Boolean isTokenExpired(ConfirmationToken confirmationToken){
        Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("UTC+2"));
        Date date = calendar.getTime();
        return date.before(confirmationToken.getExpiration());
    }

    @Override
    public void resendToken(String token){
        ConfirmationToken confirmationToken = tokenRepository.findByToken(token);
        confirmationToken.setExpiration(calculateExpirationTime());
        confirmationToken.setToken(generateToken());
        tokenRepository.save(confirmationToken);
        emailSender.sendEmail(confirmationToken.getUser(), confirmationToken.getToken());
    }

    @Override
    public UserDto updateUser(UserDto userDto) {
        User user = userRepository.findByEmail(userDto.getEmail());

        if(userDto.getName() != null){
            user.setName(userDto.getName());
        }
        if (userDto.getLastName() != null){
            user.setLastName(userDto.getLastName());
        }
        if(userDto.getMiddleName() != null){
            user.setMiddleName(userDto.getMiddleName());
        }
        if(userDto.getPostal() != null){
            user.setPostal(userDto.getPostal());
        }
        if(userDto.getResidential() !=null){
            user.setResidential(user.getResidential());
        }
        userRepository.save(user);
        return userDto;
    }
}
