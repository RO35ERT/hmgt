package com.example.hotelmanagementsystem.service;

import com.example.hotelmanagementsystem.dto.ReserveDto;
import com.example.hotelmanagementsystem.entity.Reserve;
import com.example.hotelmanagementsystem.entity.Room;
import com.example.hotelmanagementsystem.entity.User;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ReserveService {
    public List<Reserve> findAllReserveByUser(User user);
    public Reserve createdReserve(ReserveDto reserve);

    public Reserve findReserveByRoomBefore(Room room);

    public Double countPendingReserveByMonth(String date);
    public Double countAllReserveByMonth(String date);

}
