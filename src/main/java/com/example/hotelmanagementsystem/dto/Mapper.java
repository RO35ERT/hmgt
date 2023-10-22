package com.example.hotelmanagementsystem.dto;

import com.example.hotelmanagementsystem.entity.*;
import org.springframework.data.domain.Page;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.time.temporal.Temporal;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Date;
import java.util.List;

public class Mapper {
    public static Room createRoomDto(RoomDto roomDto) throws IOException {
        Room room = new Room();
        room.setRoomNumber(roomDto.getRoomNumber());
        room.setImage(roomDto.getImage().getBytes());
        room.setPrice(roomDto.getPrice());
        room.setStatus(false);
        RoomType roomType = new RoomType();
        room.setAmmenities(roomDto.getAmmenities());
        roomType.setRoomType(roomDto.getRoomType());
        room.setRoomType(roomType);
        return room;
    }

    public static List<ToRoomDto> getRoomDto(List<Room> room){
        List<ToRoomDto> toRoomDto = new ArrayList<>();

        for(Room room1 : room){
            ToRoomDto room2 = new ToRoomDto();
            room2.setRoomType(room1.getRoomType());
            room2.setPrice(room1.getPrice());
            room2.setRoomNumber(room1.getRoomNumber());
            room2.setStatus(room1.getStatus());
            String file = Base64.getEncoder().encodeToString(room1.getImage());
            room2.setImage(file);
            room2.setBranch(room1.getBranch().getBranch());
            toRoomDto.add(room2);
        }
        return toRoomDto;
    }

    public static Reserve toReserve(ReserveDto reserveDto) throws ParseException {
        Reserve reserve = new Reserve();

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

        Date checkIn = dateFormat.parse(reserveDto.getCheckInDate());
        Date checkOut = dateFormat.parse(reserveDto.getCheckOutDate());

        reserve.setCheckInDate(checkIn);
        reserve.setCheckOutDate(checkOut);
        reserve.setTotalAmount(reserveDto.getTotalAmount());
        reserve.setUser(reserveDto.getUser());
        return reserve;
    }

    public static List<RoomInvoiceDto> getRoomInvoice(List<Invoice> invoices) {
        List<RoomInvoiceDto> roomInvoiceDtos = new ArrayList<>();
        for(Invoice invoice : invoices){
            RoomInvoiceDto roomInvoiceDto = new RoomInvoiceDto();
            Branch branch =invoice.getBranch();
            Reserve reserve = invoice.getReserve();
            Room room = reserve.getRoom();
            roomInvoiceDto.setInvoiceNo(invoice.getInvoiceNo());
            roomInvoiceDto.setStatus(room.getStatus());
            roomInvoiceDto.setRoomNumber(room.getRoomNumber());
            roomInvoiceDto.setBranch(branch.getBranch());
            roomInvoiceDtos.add(roomInvoiceDto);
        }
        return roomInvoiceDtos;
    }

}
