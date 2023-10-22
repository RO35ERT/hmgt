package com.example.hotelmanagementsystem.service;

import com.example.hotelmanagementsystem.entity.ConfirmationToken;
import org.springframework.stereotype.Service;

@Service
public interface TokenService {
    public ConfirmationToken getToken(String token);
}
