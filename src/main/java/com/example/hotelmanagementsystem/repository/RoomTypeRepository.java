package com.example.hotelmanagementsystem.repository;

import com.example.hotelmanagementsystem.entity.RoomType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoomTypeRepository extends JpaRepository<RoomType,Long> {
    public RoomType findRoomTypeByRoomType(String roomType);
}
