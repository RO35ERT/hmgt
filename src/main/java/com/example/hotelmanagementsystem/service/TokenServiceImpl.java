package com.example.hotelmanagementsystem.service;

import com.example.hotelmanagementsystem.entity.ConfirmationToken;
import com.example.hotelmanagementsystem.repository.TokenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TokenServiceImpl implements TokenService {
    @Autowired
    private final TokenRepository tokenRepository;

    public TokenServiceImpl(TokenRepository tokenRepository) {
        this.tokenRepository = tokenRepository;
    }

    @Override
    public ConfirmationToken getToken(String token) {
        return tokenRepository.findByToken(token);
    }
}
