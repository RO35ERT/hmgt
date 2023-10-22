package com.example.hotelmanagementsystem.repository;

import com.example.hotelmanagementsystem.entity.Branch;
import com.example.hotelmanagementsystem.entity.Reserve;
import com.example.hotelmanagementsystem.entity.Room;
import com.example.hotelmanagementsystem.entity.RoomType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface RoomRepository extends JpaRepository<Room,String> {
    Room findByRoomNumber(String roomNumber);

    List<Room> findAllByBranch(Branch branch);

    List<Room> findAllByBranchAndPriceGreaterThan(Branch branch, Double price);

    List<Room> findAllByBranchAndPriceLessThan(Branch branch, Double price);

    @Query("select r.branch,r.roomNumber,r.status,r.ammenities,r.image,r.price,r.roomType from Room r join Reserve l on r.roomNumber=l.room.roomNumber where r.branch=:branch and r.price>:price and l.checkOutDate<:date")
    List<Room> findAllByBranchAndPriceLessThanOrGreaterThanAndReserve(String branch,Double price,String date);

    @Query("select r.branch,r.roomNumber,r.status,r.ammenities,r.image,r.price,r.roomType from Room r join Reserve l on r.roomNumber=l.room.roomNumber where r.branch=:branch and r.price<:price and l.checkOutDate<:date")
    List<Room> findAllByBranchAndPriceLessThanOrLessThanAndReserve(String branch,Double price,String date);

    Long countAllByStatus(Boolean status);

    Long countAllByBranch(Branch branch);

    Long countAllByBranchAndStatus(Branch branch, Boolean status);
}
