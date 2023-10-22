package com.example.hotelmanagementsystem.service;

import com.example.hotelmanagementsystem.dto.RoomDto;
import com.example.hotelmanagementsystem.dto.RoomInvoiceDto;
import com.example.hotelmanagementsystem.dto.ToRoomDto;
import com.example.hotelmanagementsystem.entity.Branch;
import com.example.hotelmanagementsystem.entity.Reserve;
import com.example.hotelmanagementsystem.entity.Room;
import com.example.hotelmanagementsystem.repository.RoomRepository;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public interface RoomService {
    public Room createRoom(RoomDto roomDto);
    public List<Room> getRooms();

    public List<RoomInvoiceDto> getRoomInvoice(Branch branch,String search);

    public Optional<Room> getRoom(String id);

    Page<Room> findPaginated(int pageNo, int pageSize);

    public Date findLastestReserve(List<Reserve> reserves);

    public RoomDto updateRoom(RoomDto roomDto, String roomNumber);
}
