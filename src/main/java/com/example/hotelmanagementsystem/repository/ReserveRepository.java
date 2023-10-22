package com.example.hotelmanagementsystem.repository;

import com.example.hotelmanagementsystem.entity.Reserve;
import com.example.hotelmanagementsystem.entity.Room;
import com.example.hotelmanagementsystem.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Date;
import java.util.List;

@Repository
public interface ReserveRepository extends JpaRepository<Reserve, Long> {
    List<Reserve> findAllByUserOrderByCreatedAt(User user);

    List<Reserve> findAllByRoom(Room room);

    List<Reserve> findReserveByRoomAndCheckOutDateAfterAndPaid(Room room, Date checkOutDate, Boolean paid);

    Long countReserveByUserAndPaidIs(User user, Boolean paid);
    Long countReserveByPaidIs(Boolean paid);

    @Query("SELECT SUM(r.totalAmount) FROM Reserve r WHERE r.paid = true")
    Double getTotalAmountSumWherePaidIsTrue();

    List<Reserve> findAllByPaid(Boolean paid);

    @Query("select count(*) from Reserve r join Room l on r.room.roomNumber=l.roomNumber where l.branch.branch=:branch")
    Long countAllReservesByBranch(String branch);

    @Query("select count(*) from Reserve r join Room l on r.room.roomNumber=l.roomNumber where l.branch.branch=:branch and r.paid=false")
    Long countAllPendingReservesByBranch(String branch);

    @Query("SELECT SUM(r.totalAmount) FROM Reserve r join Room l on r.room.roomNumber=l.roomNumber WHERE r.paid = true and l.branch.branch=:branch")
    Double getTotalAmountByBranchSumWherePaidIsTrue(String branch);

    @Query("SELECT count(*) from Reserve r join Room l on r.room.roomNumber=l.roomNumber where l.branch.branch=:branch and l.status=true")
    Long countAllByBranchWhereStatusIsTrue();

    @Query("SELECT count(*) from Reserve r where r.createdAt between :start and :end")
    Double countAllReserveByBetween(Date start, Date end);

    @Query("SELECT count(*) FROM Reserve r WHERE r.paid = false and r.createdAt between :start and :end")
    Double countByBetweenPending(Date start, Date end);


}
