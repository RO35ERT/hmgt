package com.example.hotelmanagementsystem.service;

import com.example.hotelmanagementsystem.dto.UserDto;
import com.example.hotelmanagementsystem.entity.ConfirmationToken;
import com.example.hotelmanagementsystem.entity.User;
import org.springframework.stereotype.Service;

@Service
public interface UserService {
    void saveUser(UserDto userDto);

    User findUserByEmail(String email);
    User findUserAndEnabled(String email, Boolean isEnabled);

    Boolean confirmToken(String token);

    void resendToken(String token);

    UserDto updateUser(UserDto userDto);
}
