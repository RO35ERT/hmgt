package com.example.hotelmanagementsystem.service;

import com.example.hotelmanagementsystem.entity.*;
import com.example.hotelmanagementsystem.repository.PaymentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public interface PaymentService {
    public Payment createPayement(PaymentType paymentType, Reserve reserve);

    public String checkNo(Branch branch);
}
