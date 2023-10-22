package com.example.hotelmanagementsystem.repository;

import com.example.hotelmanagementsystem.entity.PaymentType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

@Repository
public interface PaymentTypeRepository extends JpaRepository<PaymentType,Long> {
    PaymentType findByType(String type);
}
