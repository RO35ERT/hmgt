package com.example.hotelmanagementsystem.repository;

import com.example.hotelmanagementsystem.entity.Booking;
import com.example.hotelmanagementsystem.entity.Reserve;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

@Service
public interface BookingRepository extends JpaRepository<Booking, Long> {
    Booking findByReserve(Reserve reserve);

    Long countAllByCheckOutAndAdmit(Boolean checkOut, Boolean admit);
}
