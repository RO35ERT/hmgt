package com.example.hotelmanagementsystem.repository;

import com.example.hotelmanagementsystem.entity.Payment;
import com.example.hotelmanagementsystem.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

@Repository
public interface PaymentRepository extends JpaRepository<Payment,Long> {
    Payment findAllByUser(User user);
}
