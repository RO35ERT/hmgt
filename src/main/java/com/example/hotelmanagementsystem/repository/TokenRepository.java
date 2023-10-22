package com.example.hotelmanagementsystem.repository;

import com.example.hotelmanagementsystem.entity.ConfirmationToken;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TokenRepository extends JpaRepository<ConfirmationToken,Long> {
    public ConfirmationToken findByToken(String token);
}
