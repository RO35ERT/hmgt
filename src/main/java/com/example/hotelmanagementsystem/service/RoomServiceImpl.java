package com.example.hotelmanagementsystem.service;

import com.example.hotelmanagementsystem.dto.Mapper;
import com.example.hotelmanagementsystem.dto.RoomDto;
import com.example.hotelmanagementsystem.dto.RoomInvoiceDto;
import com.example.hotelmanagementsystem.entity.*;
import com.example.hotelmanagementsystem.repository.*;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class RoomServiceImpl implements RoomService {
    private final RoomRepository roomRepository;
    private final RoomTypeRepository roomTypeRepository;

    private final InvoiceRepository invoiceRepository;

    private final BookingRepository bookingRepository;

    private final BranchRepository branchRepository;

    @Autowired
    public RoomServiceImpl(RoomRepository roomRepository, RoomTypeRepository roomTypeRepository, InvoiceRepository invoiceRepository, BookingRepository bookingRepository, BranchRepository branchRepository) {
        this.roomRepository = roomRepository;
        this.roomTypeRepository = roomTypeRepository;
        this.invoiceRepository = invoiceRepository;
        this.bookingRepository = bookingRepository;
        this.branchRepository = branchRepository;
    }

    @Override
    public Room createRoom(RoomDto roomDto) {
        RoomType roomType = roomTypeRepository.findRoomTypeByRoomType(roomDto.getRoomType());
        Branch branch = branchRepository.findByBranch(roomDto.getBranch());
        try{
            Room room = Mapper.createRoomDto(roomDto);
            room.setRoomType(roomType);
            room.setBranch(branch);
            roomRepository.save(room);
        }catch (Exception e){
            System.out.println("Image error");
        }
        return new Room();
    }

    @Override
    public List<Room> getRooms() {
        return roomRepository.findAll();
    }

    @Transactional
    @Override
    public List<RoomInvoiceDto> getRoomInvoice(Branch branch,String search) {
        if(search != null){
            List<Invoice> invoices = invoiceRepository.findAllByInvoiceNoContaining(search);
            List<RoomInvoiceDto> invoiceListWithBooking = Mapper.getRoomInvoice(invoices);

            return addBooking(invoiceListWithBooking);
        }else{
            List<RoomInvoiceDto> roomInvoiceDtos = Mapper.getRoomInvoice(invoiceRepository.findAllByBranch(branch));
            return addBooking(roomInvoiceDtos);
        }
    }

    @Override
    public Optional<Room> getRoom(String id) {
        return roomRepository.findById(id);
    }

    @Override
    public Page<Room> findPaginated(int pageNo, int pageSize) {
        Pageable pageable = PageRequest.of(pageNo-1,pageSize);
        return this.roomRepository.findAll(pageable);
    }

    public List<RoomInvoiceDto> addBooking(List<RoomInvoiceDto> invoiceListWithBooking){
        List<RoomInvoiceDto> roomInvoiceDtos = new ArrayList<>();
        //Adding booking to an invoice to keep track of checked in and checked out reserves;
        for(RoomInvoiceDto invoice: invoiceListWithBooking){
            Invoice invoice1 = invoiceRepository.findByInvoiceNo(invoice.getInvoiceNo());
            Reserve reserve = invoice1.getReserve();
            Booking booking = bookingRepository.findByReserve(reserve);
            invoice.setCheckedIn(booking.getAdmit());
            invoice.setCheckedOut(booking.getCheckOut());
            roomInvoiceDtos.add(invoice);
        }
        return roomInvoiceDtos;
    }
    @Override
    public Date findLastestReserve(List<Reserve> reserves){
       if(!reserves.isEmpty()){
           Date reserve = reserves.get(0).getCheckOutDate();
           for (Reserve reserve1: reserves){
               if(reserve.before(reserve1.getCheckOutDate())){
                   reserve = reserve1.getCheckOutDate();
               }
           }
           return reserve;
       }
       return null;
    }

    @Override
    public RoomDto updateRoom(RoomDto roomDto, String roomNumber) {
        Room room = roomRepository.findByRoomNumber(roomNumber);

        if(roomDto.getRoomType() != null){
            RoomType roomType = roomTypeRepository.findRoomTypeByRoomType(roomDto.getRoomType());
            room.setRoomType(roomType);
        }
        if (roomDto.getPrice() != null) {
            room.setPrice(roomDto.getPrice());
        }
        if(roomDto.getAmmenities() !=null){
            room.setAmmenities(roomDto.getAmmenities());
        }
        roomRepository.save(room);
        return roomDto;
    }
}
